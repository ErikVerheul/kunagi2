/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.di.app;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import static ilarkesto.base.StrExtend.lowercaseFirstLetter;
import ilarkesto.base.Sys;
import static ilarkesto.base.Sys.getUsersHomePath;
import static ilarkesto.base.UtlExtend.sleep;
import ilarkesto.concurrent.ATask;
import ilarkesto.concurrent.TaskManager;
import static ilarkesto.core.base.Str.removeSuffix;
import ilarkesto.core.logging.Log;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.Time;
import ilarkesto.core.time.TimePeriod;
import static ilarkesto.core.time.Tm.DAY;
import static ilarkesto.core.time.Tm.getCurrentTimeMillis;
import ilarkesto.di.Context;
import static ilarkesto.di.Context.createRootContext;
import ilarkesto.integration.xstream.XStreamSerializer;
import ilarkesto.io.ExclusiveFileLock;
import ilarkesto.io.ExclusiveFileLock.FileLockedException;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.IO.delete;
import static ilarkesto.io.IO.getResource;
import static ilarkesto.io.IO.loadProperties;
import static ilarkesto.io.IO.zip;
import ilarkesto.persistence.DaoListener;
import ilarkesto.persistence.DaoService;
import ilarkesto.persistence.EntityStore;
import ilarkesto.persistence.FileEntityStore;
import ilarkesto.persistence.Serializer;
import ilarkesto.persistence.TransactionService;
import ilarkesto.properties.FilePropertiesStore;
import java.io.File;
import java.io.FileFilter;
import java.util.Properties;
import java.util.Set;

/**
 * Base class of a custom application
 * 
 * @author Witoslaw Koczewski
 */
public abstract class AApplication {

	private static final Log log = Log.get(AApplication.class);

	private ExclusiveFileLock exclusiveFileLock;
	private boolean startupFailed;
	private boolean shuttingDown;
	private boolean shutdown;

	protected abstract void onStart();

	protected abstract void onShutdown();

	protected abstract void scheduleTasks(TaskManager tm);

	protected Context context;
	private String[] arguments = new String[0];

	public void ensureIntegrity() {
		log.info("Ensuring application integrity");
		getDaoService().ensureIntegrity();
		getTransactionService().commit();
	}

	protected boolean isSingleton() {
		return true;
	}

        @SuppressWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
	public final void start() {
		if (instance != null) { throw new RuntimeException("An Application already started: " + instance); }
		synchronized (getApplicationLock()) {

			instance = this;

			log.info("\n\n     DATA PATH:", getApplicationDataDir(), "\n\n");

			context = createRootContext("app:" + getApplicationName());
			context.addBeanProvider(this);

			if (isSingleton()) {
				File lockFile = new File(getApplicationDataDir() + "/.lock");
				for (int i = 0; i < 10; i++) {
					try {
						exclusiveFileLock = new ExclusiveFileLock(lockFile);
						break;
					} catch (FileLockedException ex) {
						log.info("Application already running. Lock file locked: " + lockFile.getAbsolutePath());
					}
				        sleep(1000);
				}
				if (exclusiveFileLock == null) {
					log.fatal("Application startup failed. Another instance is running. Lock file: "
							+ lockFile.getAbsolutePath());
					shutdown();
					return;
				}
			}

			try {
				getApplicationConfig();
			} catch (Throwable ex) {
				startupFailed = true;
				throw new RuntimeException("Application startup failed. Loading configuration failed.", ex);
			}
			try {
				backupApplicationDataDir();
				deleteOldApplicationDataDirBackups();
			} catch (Throwable ex) {
				log.error("Backing up application data directory failed.", ex);
			}
			try {
				ensureIntegrity();
			} catch (Throwable ex) {
				startupFailed = true;
				shutdown(false);
				throw new RuntimeException("Application startup failed. Data integrity check or repair failed.", ex);
			}
			try {
				onStart();
			} catch (Throwable ex) {
				startupFailed = true;
				shutdown(false);
				throw new RuntimeException("Application startup failed.", ex);
			}

			try {
				scheduleTasks(getTaskManager());
			} catch (Throwable ex) {
				startupFailed = true;
				shutdown(true);
				throw new RuntimeException("Application startup failed.", ex);
			}
		}
	}

	public final void shutdown() {
		shutdown(true);
	}

	private void shutdown(final boolean runOnShutdown) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (getApplicationLock()) {
					if (instance == null) {
                                                throw new RuntimeException("Application not started yet.");
                                        }
					log.info("Shutdown initiated:", getApplicationName());

					if (runOnShutdown) {
                                                onShutdown();
                                        }

					getTaskManager().shutdown(10000);
					Set<ATask> tasks = getTaskManager().getRunningTasks();
					if (!tasks.isEmpty()) {
						log.warn("Aborting tasks on shutdown failed:", tasks);
					}
					getEntityStore().lock();
					shutdown = true;

					if (context != null) {
                                                context.destroy();
                                        }

					if (exclusiveFileLock != null) {
                                                exclusiveFileLock.release();
                                        }
//					DefaultLogRecordHandler.stopLogging();
				}
			}

		});
		thread.setName(getApplicationName() + " shutdown");
		shuttingDown = true;
		thread.start();
	}

	public void backupApplicationDataDir() {
		final File dataDir = new File(getApplicationDataDir());
		File backupFile = new File(dataDir.getPath() + "/backups/" + getApplicationName() + "-data_"
				+ DateAndTime.now().formatLog() + ".zip");
		log.info("Backing up application data dir:", dataDir.getAbsolutePath(), "into", backupFile);
		long starttime = getCurrentTimeMillis();
		Object lock = entityStore == null ? this : entityStore;
		synchronized (lock) {
			zip(backupFile, new File[] { dataDir }, new FileFilter() {

				@Override
				public boolean accept(File file) {
					File dir = file.getParentFile();
					if (dir.equals(dataDir)) {
						// base dir
						String name = file.getName();
						if (name.equals(".lock")) {
                                                        return false;
                                                }
						if (name.equals("backups")) {
                                                        return false;
                                                }
						if (name.equals("entities-rescue")) {
                                                        return false;
                                                }
						if (name.equals("tmp")) {
                                                        return false;
                                                }
						if (name.startsWith("gwt-")) {
                                                        return false;
                                                }
						if (file.isDirectory()) {
                                                        log.info("    Zipping", file.getPath());
                                                }
					}
					return true;
				}
			});
		}
		long runtime = getCurrentTimeMillis() - starttime;
		log.info("  Backup completed in", new TimePeriod(runtime).toShortestString());
	}

	private void deleteOldApplicationDataDirBackups() {
		File backupDir = new File(getApplicationDataDir() + "/backups");
		File[] files = backupDir.listFiles();
		if (files == null || files.length == 0) {
                        return;
                }

		log.info("Deleting old backup files from", backupDir);
		final long deadline = getCurrentTimeMillis() - DAY * 7;

		for (File file : files) {
			if (!file.getName().startsWith(getApplicationName())) {
                                continue;
                        }
			if (file.lastModified() >= deadline && !file.getName().endsWith(".zip~")) {
                                continue;
                        }
			log.debug("    Deleting", file);
			delete(file);
		}
	}

	public final <T> T autowire(T bean) {
		return context.autowire(bean);
	}

        @SuppressWarnings("EI_EXPOSE_REP2")
	public void setArguments(String[] arguments) {
		this.arguments = arguments;
	}

        @SuppressWarnings("EI_EXPOSE_REP")
	public String[] getArguments() {
		return arguments;
	}

	private static volatile AApplication instance;

	public static AApplication get() {
		if (instance == null) {
                        throw new RuntimeException("No application started yet");
                }
		return instance;
	}

	public static boolean isStarted() {
		return instance != null;
	}

	private static String APPLICATION_LOCK = "APPLICATION_LOCK";

	public Object getApplicationLock() {
		return APPLICATION_LOCK;
	}

	public final String getApplicationPackageName() {
		return getClass().getPackage().getName();
	}

	public String getApplicationLabel() {
		return getApplicationName();
	}

	public AApplication getApplication() {
		return this;
	}

	private String applicationName;

	public String getApplicationName() {
		if (applicationName == null) {
			applicationName = getClass().getSimpleName();
			applicationName = lowercaseFirstLetter(applicationName);
			applicationName = removeSuffix(applicationName, "Application");
		}
		return applicationName;
	}

	private String applicationDataDir;

	public String getApplicationDataDir() {
		if (applicationDataDir == null) {
			if (isDevelopmentMode()) {
				applicationDataDir = new File("runtimedata").getAbsolutePath();
			} else {
				applicationDataDir = getUsersHomePath() + "/." + getApplicationName();
			}
		}
		return applicationDataDir;
	}

	private String applicationTempDir;

	public String getApplicationTempDir() {
		if (applicationTempDir == null) {
			applicationTempDir = getApplicationDataDir() + "/tmp";
		}
		return applicationTempDir;
	}

	private FilePropertiesStore applicationConfig;

	public FilePropertiesStore getApplicationConfig() {
		if (applicationConfig == null) {
			applicationConfig = new FilePropertiesStore(getApplicationDataDir() + "/config.properties", false);
		}
		return applicationConfig;
	}

	private String releaseLabel;

	public String getReleaseLabel() {
		if (releaseLabel == null) {
			releaseLabel = getBuildProperties().getProperty("release.label");
			if (releaseLabel == null || releaseLabel.equals("@release-label@")) {
                                releaseLabel = "dev[" + getBuild() + "]";
                        }
		}
		return releaseLabel;
	}

	public String getBuild() {
		String date = getBuildProperties().getProperty("date");
		if (date == null || "@build-date@".equals(date)) {
                        date = Time.now().toString();
                }
		return date;
	}

	protected Properties buildProperties;

	public final Properties getBuildProperties() {
		if (buildProperties == null) {
			try {
				buildProperties = loadProperties(getResource(getClass(), "build.properties"), UTF_8);
			} catch (Throwable t) {
				log.error(t);
				buildProperties = new Properties();
			}
		}
		return buildProperties;
	}

	public boolean isDevelopmentMode() {
		return Sys.isDevelopmentMode();
	}

	public final boolean isProductionMode() {
		return !isDevelopmentMode();
	}

	public boolean isStartupFailed() {
		return startupFailed;
	}

	public boolean isShuttingDown() {
		return shuttingDown;
	}

	public boolean isShutdown() {
		return shutdown;
	}

	@Override
	public final String toString() {
		return getApplicationName();
	}

	// --- beans / services ---

	private TaskManager taskManager;

	public TaskManager getTaskManager() {
		if (taskManager == null) {
                        taskManager = Context.get().autowire(new TaskManager());
                }
		return taskManager;
	}

	private FileEntityStore entityStore;

	public final EntityStore getEntityStore() {
		if (entityStore == null) {
			entityStore = new FileEntityStore();
			entityStore.setDir(getApplicationDataDir() + "/entities");
			File backupDir = new File(getApplicationDataDir() + "/entities-rescue");

			File backupDirOld = new File(getApplicationDataDir() + "/backup/entities");
                        if (backupDirOld.exists()) {
                                if (!(backupDirOld.renameTo(backupDir)
                                        && backupDirOld.delete()
                                        && backupDirOld.getParentFile().delete())) {
                                        log.error("Renaming and deleting the old backup " + backupDirOld + " directory and parent failed.");
                                }
                        }

			entityStore.setBackupDir(backupDir.getPath());
			entityStore.setVersion(getDataVersion());
			Context.get().autowire(entityStore);

			entityStore.deleteOldBackups();
		}
		return entityStore;
	}

	protected int getDataVersion() {
		return -1;
	}

	private XStreamSerializer beanSerializer;

	public final Serializer getBeanSerializer() {
		if (beanSerializer == null) {
			beanSerializer = new XStreamSerializer();
			Context.get().autowire(beanSerializer);
		}
		return beanSerializer;
	}

	private DaoService daoService;

	public final DaoService getDaoService() {
		if (daoService == null) {
			daoService = new DaoService();
			Context.get().autowire(daoService);
			daoService.initialize(context);
			for (DaoListener listener : Context.get().getBeansByType(DaoListener.class)) {
                                daoService.addListener(listener);
                        }
		}
		return daoService;
	}

	private TransactionService transactionService;

	public final TransactionService getTransactionService() {
		if (transactionService == null) {
			transactionService = new TransactionService();
			Context.get().autowire(transactionService);
		}
		return transactionService;
	}

}
