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
package scrum.server;

import ilarkesto.base.Sys;
import ilarkesto.base.UtlExtend;
import ilarkesto.core.logging.Log;
import ilarkesto.io.IO;
import ilarkesto.properties.APropertiesStore;
import ilarkesto.properties.FilePropertiesStore;

import java.io.File;
import java.util.Properties;

public class KunagiRootConfig {

	private static Log log = Log.get(KunagiRootConfig.class);

	private APropertiesStore props;

	private String webappName;
	private File configFile;

	// used by TestUtil
	public KunagiRootConfig(File configFile, File defaultsFile) {
		this(configFile, defaultsFile, "kunagi");
	}

	// used by web application
	public KunagiRootConfig(String webappName) {
		this(determineConfigFile(webappName), determineDefaultConfigFile(webappName), webappName);
	}

	private KunagiRootConfig(File configFile, File defaultsFile, String webappName) {
		this.configFile = configFile;
		this.webappName = webappName;
		log.info("\n\n     CONFIGURATION FILE:", configFile.getAbsolutePath(), "\n\n");
		props = new FilePropertiesStore(configFile, true).setLabel("Kunagi Configuration");

		if (defaultsFile != null && defaultsFile.exists()) {
			log.info("\n\n     Including configuration defaults from:", defaultsFile.getAbsolutePath(), "\n\n");
			Properties defaults = IO.loadProperties(defaultsFile, IO.UTF_8);
			props.setDefaults(defaults);
			log.warn("Configuration file "
					+ defaultsFile.getAbsolutePath()
					+ " is deprecated and will be ignored by future Kunagi versions! Please move configuration properties into your instance configuration file "
					+ configFile.getAbsolutePath() + ".");
		}
	}

	private String dataPath;

	public String getDataPath() {
		if (dataPath == null) {
			dataPath = props.get("data.path");
			if (dataPath != null) {
				if (new File(dataPath).getAbsolutePath().equals(configFile.getParent())) {
					props.set("data.path", (String) null);
				} else {
					log.warn("The property data.path in configuration file "
							+ configFile.getAbsolutePath()
							+ " is deprecated and will be ignored in future Kunagi versions! Instead the configuration file config.properties needs to be in your Kunagi data directory.");
				}
			} else {
				dataPath = configFile.getParent();
			}
		}
		return dataPath;
	}

	private static File determineConfigFile(String webappName) {
		String configFilePath = System.getProperty("kunagi.config");
		if (configFilePath != null) {
			log.warn("Java System Property kunagi.config is deprecated and will be ignored in future Kunagi versions! Please instead set the Java System Property kunagi.data and point it to your Kunagi data directory. Also place there your configuration file as config.properties.");
			return new File(configFilePath);
		}

		return new File(determineUsedDataPath(webappName) + "/config.properties");
	}

	private static File determineDefaultConfigFile(String webappName) {
		String dir = Sys.isWindows() ? Sys.getUsersHomePath() : "/etc";
		return new File(dir + "/" + webappName + ".properties");
	}

	private static String determineUsedDataPath(String webappName) {
		String dataPath = System.getProperty("kunagi.data");
		if (dataPath != null) return dataPath;

		if (Sys.isDevelopmentMode()) return new File("runtimedata").getAbsolutePath();

		File legacyDataDir = determineLegacyDataDir(webappName);
		if (legacyDataDir != null && legacyDataDir.exists()) {
			log.warn("Data directory " + legacyDataDir.getAbsolutePath()
					+ " is deprecated and will be ignored by future Kunagi version! Please move it to "
					+ determineDefaultDataPath(webappName));
			return legacyDataDir.getAbsolutePath();
		}

		return determineDefaultDataPath(webappName);
	}

	private static String determineDefaultDataPath(String webappName) {
		if (Sys.isWindows()) return Sys.getUsersHomePath() + "/" + webappName;

		String webappsDir = Sys.getWorkDir().getAbsolutePath() + "/webapps";
		if (new File(webappsDir).exists()) return new File(webappsDir + "/" + webappName + "-data").getAbsolutePath();
		return new File(Sys.getWorkDir().getAbsolutePath() + "/" + webappName + "-data").getAbsolutePath();
	}

        // do not try Sys.getUsersHomePath()! This will cause Glassfish on Windows to fail.
	private static File determineLegacyDataDir(String webappName) {
		File file = UtlExtend.getFirstExistingFile(Sys.getUsersHomePath() + "/webapp-data/" + webappName + "/entities",
			Sys.getUsersHomePath() + "/webapps/" + webappName + "/entities", Sys.getWorkDir() + "/webapp-data/"
					+ webappName + "/entities", Sys.getWorkDir() + "/webapps/" + webappName + "/entities");
		return file == null ? null : file.getParentFile();
	}

	public String getUrl() {
		return props.get("url", Sys.isDevelopmentMode() ? "http://localhost:8888/" : null);
	}
        
        public int getPort() {
            return props.getInt("port", 8080);
        }

	public void setUrl(String url) {
		props.set("url", url);
	}

	public String getFileRepositoryPath() {
		return props.get("fileRepository.path", getDataPath() + "/files");
	}

	public boolean isStartupDelete() {
		return props.getBoolean("startup.delete", false);
	}

	public boolean isDeleteOldProjects() {
		return props.getBoolean("deleteOldProjects", false);
	}

	public boolean isDeleteDisabledUsers() {
		return props.getBoolean("deleteDisabledUsers", false);
	}

	public boolean isDisableUsersWithUnverifiedEmails() {
		return props.getBoolean("disableUsersWithUnverifiedEmails", false);
	}

	public boolean isDisableInactiveUsers() {
		return props.getBoolean("disableInactiveUsers", false);
	}

	public boolean isShowRelease() {
		return props.getBoolean("showRelease", Sys.isDevelopmentMode());
	}

	public boolean isCreateTestData() {
		return props.getBoolean("createTestData", Sys.isDevelopmentMode());
	}

	public String getInitialPassword() {
		return props.get("initialPassword", "geheim");
	}

	public boolean isLoggingDebug() {
		return props.getBoolean("logging.debug", false);
	}

	public String getHttpProxyHost() {
		return props.get("http.proxy.host");
	}

	public int getHttpProxyPort() {
		return props.getInt("http.proxy.port", 8080);
	}

	public String getSmtpServer() {
		return props.get("smtp.server");
	}

	public String getSmtpUser() {
		return props.get("smtp.user");
	}

	public String getSmtpPassword() {
		return props.get("smtp.password");
	}

}
