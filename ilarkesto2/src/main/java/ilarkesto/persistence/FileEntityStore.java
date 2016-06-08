/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
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
package ilarkesto.persistence;
import static ilarkesto.core.base.Str.isBlank;
import ilarkesto.core.logging.Log;
import ilarkesto.core.time.Date;
import static ilarkesto.core.time.Date.beforeDays;
import static ilarkesto.core.time.Date.today;
import ilarkesto.fp.Predicate;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.IO.close;
import static ilarkesto.io.IO.copyFile;
import static ilarkesto.io.IO.delete;
import static ilarkesto.io.IO.loadProperties;
import static ilarkesto.io.IO.move;
import static ilarkesto.io.IO.saveProperties;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Long.parseLong;
import static java.lang.String.valueOf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class FileEntityStore implements EntityStore {

	private static final Log log = Log.get(FileEntityStore.class);

	public final static String CLUSTER_FILE_NAME = "cluster.xml";

	private boolean versionSaved;
	private boolean versionChecked;
	private boolean locked;

	// --- dependencies ---

	private long version;

	@Override
	public synchronized void setVersion(long version) {
		this.version = version;
	}

	private Serializer beanSerializer;

	public synchronized void setBeanSerializer(Serializer beanSerializer) {
		this.beanSerializer = beanSerializer;
	}

	private EntityfilePreparator entityfilePreparator;

	public void setEntityfilePreparator(EntityfilePreparator entityfilePreparator) {
		this.entityfilePreparator = entityfilePreparator;
	}

	private String dir;

	public synchronized void setDir(String dir) {
		this.dir = dir;
	}

	private String backupDir;

	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}

	// --- ---

	@Override
	public synchronized void lock() {
		if (locked) {
                        return;
                }
		locked = true;
		log.info("File entity store locked.");
	}

	@Override
	public synchronized void persist(Collection<AEntity> entitiesToSave, Collection<AEntity> entitiesToDelete) {
		if (locked) {
                        throw new RuntimeException("Can not persist entity changes. EntityStore already locked.");
                }

		if (!versionSaved) {
                        saveVersion();
                }

		// create operations
		List<Operation> operations = new ArrayList<>(entitiesToSave.size()
				+ entitiesToDelete.size());
		for (AEntity entity : entitiesToSave) {
			operations.add(new SaveOperation(entity));
		}
		for (AEntity entity : entitiesToDelete) {
			operations.add(new DeleteOperation(entity));
		}

		// prepare operations
		for (Operation operation : operations) {
			operation.prepare();
		}

		// complete operations (critical)
		for (Operation operation : operations) {
			operation.complete();
		}

		StringBuilder sb = new StringBuilder();
		for (Operation operation : operations) {
			sb.append("\n    ").append(operation.toString());
		}
		log.debug("Entity changes persisted.", sb.toString());
	}

	private Map<String, AEntity> getDao(Class<? extends AEntity> type) {
		Map<String, AEntity> dao = data.get(type);
		if (dao == null) { throw new RuntimeException("Unknown entity type: " + type); }
		return dao;
	}

	@Override
	public AEntity getById(String id) {
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> daoEntry : data.entrySet()) {
			AEntity entity = daoEntry.getValue().get(id);
			if (entity != null) {
                                return entity;
                        }
		}
		return null;
	}

	@Override
	public synchronized AEntity getEntity(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> daoEntry : data.entrySet()) {
			if (typeFilter != null && !typeFilter.test(daoEntry.getKey())) {
                                continue;
                        }
			for (AEntity entity : daoEntry.getValue().values()) {
				if (entityFilter.test(entity)) {
                                        return entity;
                                }
			}
		}
		return null;
	}

	@Override
	public List<AEntity> getByIds(Collection<String> ids) {
		List<AEntity> result = new ArrayList<>(ids.size());
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> entry : data.entrySet()) {
			Map<String, AEntity> entites = entry.getValue();
			for (String id : ids) {
				AEntity entity = entites.get(id);
				if (entity != null) {
                                        result.add(entity);
                                }
			}
		}
		return result;
	}

	@Override
	public synchronized Set<AEntity> getEntities(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		Set<AEntity> result = new HashSet<>();
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> entry : data.entrySet()) {
			if (typeFilter != null && !typeFilter.test(entry.getKey())) {
                                continue;
                        }
			if (entityFilter == null) {
				result.addAll(entry.getValue().values());
			} else {
				for (AEntity entity : entry.getValue().values()) {
					if (entityFilter.test(entity)) {
                                                result.add(entity);
                                        }
				}
			}
		}
		return result;
	}

	@Override
	public synchronized int getEntitiesCount(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		int result = 0;
		for (Map.Entry<Class<AEntity>, Map<String, AEntity>> entry : data.entrySet()) {
			if (typeFilter != null && !typeFilter.test(entry.getKey())) {
                                continue;
                        }
			if (entityFilter == null) {
				result += entry.getValue().size();
			} else {
				for (AEntity entity : entry.getValue().values()) {
					if (entityFilter.test(entity)) {
                                                result++;
                                        }
				}
			}
		}
		return result;
	}

	private Map<Class, String> aliases = new HashMap<>();

	private Map<Class<AEntity>, Map<String, AEntity>> data = new HashMap<>();

	@Override
	public synchronized void setAlias(String alias, Class cls) {
		aliases.put(cls, alias);
		beanSerializer.setAlias(alias, cls);
	}

	@Override
	public synchronized void load(Class<? extends AEntity> cls, String alias) {
		if (!versionChecked) {
                        checkVersion();
                }

		aliases.put(cls, alias);

		Map<String, AEntity> entities = new HashMap<>();
		data.put((Class<AEntity>) cls, entities);

		beanSerializer.setAlias(alias, cls);

		File entitiesDir = new File(dir + "/" + alias);

		File clusterFile = new File(dir + "/" + CLUSTER_FILE_NAME);
		if (clusterFile.exists()) {
			loadCluster(clusterFile, entities, cls, alias);
		}

		File[] files = entitiesDir.listFiles();
		int count = files == null ? 0 : files.length;
		log.info("Loading", count, "entitiy files:", alias);
		if (count > 0) {
                        for (File file : files) {
                                String filename = file.getName();
                                
                                if (filename.equals(CLUSTER_FILE_NAME)) {
                                        continue;
                                }
                                if (!filename.endsWith(".xml")) {
                                        log.warn("Unsupported file. Skipping:", filename);
                                        continue;
                                }
                                
                                try {
                                        loadObject(file, entities, cls, alias);
                                } catch (Throwable ex) {
                                        throw new RuntimeException("Loading object from " + file + " failed", ex);
                                }
                        }
		}
		// LOG.info(" Loaded entities:", alias, count);
	}

	private void loadCluster(File file, Map<String, AEntity> container, Class type, String alias) {
		if (entityfilePreparator != null) {
                        entityfilePreparator.prepareClusterfile(file, type, alias);
                }

		BufferedInputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		Collection<AEntity> entities = (Collection<AEntity>) beanSerializer.deserialize(in);

		for (AEntity entity : entities) {
			container.put(entity.getId(), entity);
		}

		try {
			in.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void loadObject(File file, Map<String, AEntity> container, Class type, String alias) {
		if (entityfilePreparator != null) {
                        entityfilePreparator.prepareEntityfile(file, type, alias);
                }

		BufferedInputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		AEntity entity = (AEntity) beanSerializer.deserialize(in);
		container.put(entity.getId(), entity);
		try {
			in.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void backup(File src, String type) {
		if (src.isDirectory()) {
                        throw new RuntimeException("sorry, backing up directories is not implemented yet.");
                }

		String destinationPath = backupDir + "/" + today() + "/" + type + "/";
		File dst = new File(destinationPath + src.getName());
		for (int i = 2; dst.exists(); i++) {
			dst = new File(destinationPath + i + "_" + src.getName());
		}

		// LOG.debug("Backing up", src.getPath(), "to", dst.getPath());
		copyFile(src.getPath(), dst.getPath());
	}

	private synchronized void checkVersion() {
		versionChecked = true;
		if (version <= 0) {
                        return;
                }
		File propertiesFile = getPropertiesFile();
		if (!propertiesFile.exists()) {
                        return;
                }
		Properties properties = loadProperties(propertiesFile, UTF_8);
		String s = properties.getProperty("version");
		if (isBlank(s)) {
                        return;
                }
		long dataVersion = parseLong(s);
		if (dataVersion > version) {
                        throw new IllegalStateException("Data stored in " + dir
                                + " was created by a newer version of the application. "
                                + "You have probably downgraded. Since data formats changed, this is not possible. "
                                + "Application version is " + version + ", data version is " + dataVersion + ".");
                }
	}

	private synchronized void saveVersion() {
		versionSaved = true;
		if (version <= 0) {
                        return;
                }
		File propertiesFile = getPropertiesFile();
		Properties properties = propertiesFile.exists() ? loadProperties(propertiesFile, UTF_8)
				: new Properties();
		properties.setProperty("version", valueOf(version));
		saveProperties(properties, getClass().getName(), propertiesFile);
	}

	@Override
	public void deleteOldBackups() {
		if (isBlank(backupDir)) {
                        return;
                }
		File[] dirs = new File(backupDir).listFiles();
		if (dirs == null || dirs.length == 0) {
                        return;
                }
		Date deadline = beforeDays(7);
		log.info("Deleting temporary entity backups from before", deadline);
		for (File dir_local : dirs) {
			if (!dir_local.isDirectory()) {
                                continue;
                        }
			String name = dir_local.getName();
			Date date;
			try {
				date = new Date(name);
			} catch (Throwable ex) {
				continue;
			}
			if (date.isBefore(deadline)) {
				log.debug("    Deleting temporary enity backups:", name);
				delete(dir_local);
			}
		}
	}

	private File getPropertiesFile() {
		return new File(dir + "/store.properties");
	}

	abstract class Operation {

		protected abstract void prepare();

		protected abstract void complete();

		protected AEntity entity;

		public Operation(AEntity entity) {
			this.entity = entity;
		}

	}

	class SaveOperation extends Operation {

		private File tmpFile;
		private File file;

		public SaveOperation(AEntity entity) {
			super(entity);
		}

		@Override
		protected void prepare() {
			tmpFile = new File(dir + "/tmp/" + entity.getId() + ".xml");
			file = new File(dir + "/" + entity.getDao().getEntityName() + "/" + entity.getId() + ".xml");

			wirteTemporaryFile();
			backupExistingFile();
		}

		@Override
		protected void complete() {
			move(tmpFile, file, true);
			getDao(entity.getClass()).put(entity.getId(), entity);
		}

		public void backupExistingFile() {
			if (file.exists() && !(entity instanceof BackupHostile)) {
				backup(file, entity.getDao().getEntityName());
			}
		}

		private synchronized void wirteTemporaryFile() {
			if (!tmpFile.getParentFile().exists()) {
                               if (!tmpFile.getParentFile().mkdirs()) {
                                       throw new RuntimeException("Creating path to tmpFile failed: " + tmpFile.getPath());
                               }
                        }
			BufferedOutputStream out;
			try {
				out = new BufferedOutputStream(new FileOutputStream(tmpFile));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			beanSerializer.serialize(entity, out);
			close(out);

			if (!tmpFile.exists()) {
                                throw new RuntimeException("Writing entity file failed: " + tmpFile.getPath());
                        }

			if (tmpFile.length() < 1) {
                                throw new RuntimeException("Writing entity file caused empty file: " + tmpFile.getPath());
                        }
		}

		@Override
		public String toString() {
			return "SAVED " + file.getPath() + " -> " + entity;
		}

	}
        
        @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "IS2_INCONSISTENT_SYNC", justification = "Used only in a synchronized context")
	private class DeleteOperation extends Operation {

		private File file;

		public DeleteOperation(AEntity entity) {
			super(entity);
		}
           
                @Override
		protected void prepare() {
			file = new File(dir + "/" + entity.getDao().getEntityName() + "/" + entity.getId() + ".xml");

			// backup
			if (file.exists() && !(entity instanceof BackupHostile)) {
				backup(file, entity.getDao().getEntityName());
			}

		}

		@Override
		protected void complete() {
			delete(file);
			getDao(entity.getClass()).remove(entity.getId());
		}

		@Override
		public String toString() {
			return "DELETED " + file.getPath();
		}

	}

}
