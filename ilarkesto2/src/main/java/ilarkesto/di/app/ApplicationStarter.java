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

import static ilarkesto.base.Sys.getFileEncoding;
import static ilarkesto.base.Sys.getJavaHome;
import static ilarkesto.base.Sys.getJavaRuntimeVersion;
import static ilarkesto.base.Sys.getStartupTime;
import static ilarkesto.base.Sys.getUsersHomePath;
import static ilarkesto.base.Sys.getUsersName;
import static ilarkesto.base.Sys.isDevelopmentMode;
import static ilarkesto.base.Sys.storeStartupTime;
import ilarkesto.cli.ACommand;
import static ilarkesto.core.base.Utl.setLanguage;
import ilarkesto.core.logging.Log;
import static ilarkesto.core.logging.Log.setDebugEnabled;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.di.BeanContainer;
import ilarkesto.di.BeanProvider;
import ilarkesto.di.MultiBeanProvider;
import static ilarkesto.io.IO.getTempDir;
import static ilarkesto.io.IO.getWorkDir;
import ilarkesto.logging.DefaultLogRecordHandler;
import ilarkesto.logging.JavaLogging;
import ilarkesto.logging.Log4jLogging;
import java.io.File;
import static java.util.Locale.getDefault;

public class ApplicationStarter {

	private static final Log LOG = Log.get(ApplicationStarter.class);

	public static <A extends AApplication> A startApplication(Class<A> applicationClass, BeanProvider beanProvider,
			String... arguments) {

		storeStartupTime();
		DefaultLogRecordHandler.activate();
		setDebugEnabled(isDevelopmentMode());
		setLanguage(getDefault().getLanguage());
		LOG.info("********************************************************************************");
		LOG.info("Starting application:", applicationClass.getName());
		logEnvironmentInfo();
		LOG.info("    arguments:   ", arguments);

		try {
			A application = applicationClass.newInstance();
			if (beanProvider != null) {
                                beanProvider.autowire(application);
                        }
			DefaultLogRecordHandler.setLogFile(new File(application.getApplicationDataDir() + "/error.log"));
			JavaLogging.redirectToLoggers();
			try {
				Log4jLogging.redirectToLoggers();
			} catch (Throwable ex) {}
			application.setArguments(arguments);
			application.start();
			LOG.info("Application started:", application.getApplicationName() + " " + application.getReleaseLabel());
			LOG.info("********************************************************************************\n");
			return application;
		} catch (InstantiationException | IllegalAccessException ex) {
			LOG.fatal("Starting application failed.", ex);
			throw new RuntimeException(ex);
		}
	}

	public static void logEnvironmentInfo() {
		String mode = isDevelopmentMode() ? "DEVELOPMENT" : "PRODUCTION";
		LOG.info("   ", mode, "MODE");
		LOG.info("    time:        ", new DateAndTime(getStartupTime()));
		LOG.info("    system user: ", getUsersName());
		LOG.info("    user home:   ", getUsersHomePath());
		LOG.info("    work-path:   ", getWorkDir());
		LOG.info("    temp-path:   ", getTempDir());
		LOG.info("    locale:      ", getDefault());
		LOG.info("    encoding:    ", getFileEncoding());
		LOG.info("    java:        ", getJavaHome());
		LOG.info("    java version:", getJavaRuntimeVersion());
	}

	public static <A extends AApplication> A startApplication(Class<A> applicationClass, String... arguments) {
		return startApplication(applicationClass, new MultiBeanProvider(), arguments);
	}

	public static void executeCommand(Class<? extends ACommand> commandClass, String... arguments) {
		BeanContainer beanContainer = new BeanContainer();
		beanContainer.put("commandClass", commandClass);
		startApplication(CommandApplication.class, beanContainer, arguments);
	}

	// --- dependencies ---

}
