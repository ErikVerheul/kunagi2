/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

/**
 *
 * @author erik
 */
public class ApplicationInfo implements Serializable, IsSerializable {

    private String name;
    private String release;
    private String build;
    private boolean defaultAdminPassword;
    private String currentRelease;
    private String dataPath;

    /**
     *
     * @param name
     * @param release
     * @param build
     * @param defaultAdminPassword
     * @param currentRelease
     * @param dataPath
     */
    public ApplicationInfo(String name, String release, String build, boolean defaultAdminPassword,
            String currentRelease, String dataPath) {
        this.name = name;
        this.release = release;
        this.build = build;
        this.defaultAdminPassword = defaultAdminPassword;
        this.currentRelease = currentRelease;
        this.dataPath = dataPath;
    }

    /**
     *
     */
    protected ApplicationInfo() {
    }

    /**
     *
     * @return
     */
    public boolean isNewReleaseAvailable() {
        if (currentRelease == null) {
            return false;
        }
        if (release.startsWith("dev")) {
            return false;
        }
        return !currentRelease.equals(release);
    }

    /**
     *
     * @return
     */
    public boolean isDefaultAdminPassword() {
        return defaultAdminPassword;
    }

    /**
     *
     * @return
     */
    public String getRelease() {
        return release;
    }

    /**
     *
     * @return
     */
    public String getCurrentRelease() {
        return currentRelease;
    }

    /**
     *
     * @return
     */
    public String getBuild() {
        return build;
    }

    /**
     *
     * @return
     */
    public boolean isProductionStage() {
        return !release.startsWith("dev[");
    }

    /**
     *
     * @return
     */
    public String getVersionDescription() {
        return name + " " + release + " | Build " + build;
    }

    /**
     *
     * @return
     */
    public String getDataPath() {
        return dataPath;
    }

    @Override
    public String toString() {
        return getVersionDescription();
    }

}
