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
package ilarkesto.base;

import static ilarkesto.base.UtlExtend.toStringArray;
import static ilarkesto.core.base.Str.isBlank;
import static ilarkesto.core.time.Tm.getCurrentTimeMillis;
import static ilarkesto.io.IO.getLocalHostNames;
import java.awt.GraphicsEnvironment;
import java.io.File;
import static java.io.File.listRoots;
import static java.lang.Integer.parseInt;
import static java.lang.Runtime.getRuntime;
import static java.lang.String.valueOf;
import static java.lang.System.getProperty;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;
import java.util.Collection;

/**
 * Utility methods for the java core. System properties access.
 */
public final class Sys {

	private static long startupTime;
	private static File workDir;

	private static final boolean developmentMode = new File("src").exists();

	public static boolean isDevelopmentMode() {
		return developmentMode;
	}

	public static boolean isProductionMode() {
		return !isDevelopmentMode();
	}

	public static long getAvailableMemory() {
		Runtime runtime = getRuntime();
		long freeMemory = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long usedMemory = totalMemory - freeMemory;
		long maxMemory = runtime.maxMemory();
		long availableMemory = maxMemory - usedMemory;
		return availableMemory;
	}

	public static void storeStartupTime() {
		startupTime = getCurrentTimeMillis();
	}

	public static long getStartupTime() {
		if (startupTime == 0) {
                        throw new RuntimeException("Startup unknown. Sys.storeStartupTime() needs to be called.");
                }
		return startupTime;
	}

	public static File getWorkDir() {
		if (workDir == null) {
			workDir = new File("dummy").getAbsoluteFile().getParentFile();
		}
		return workDir;
	}

	public static File getUsersHomeDir() {
		return new File(getUsersHomePath());
	}

	public static void set2dTranslucencyAcceleration(boolean value) {
		setProperty("sun.java2d.translaccel", value);
	}

	public static void set2dForceVideoRam(boolean value) {
		setProperty("sun.java2d.ddforcevram", value);
	}

	public static void set2dHardwareAccaleratedScaling(boolean value) {
		setProperty("sun.java2d.ddscale", value);
	}

	/**
	 * Java 2D OpenGL Support. (Better performance on Linux).
	 */
	public static void set2dOpenGl(boolean value) {
		setProperty("sun.java2d.opengl", value);
	}

	/**
	 * No GUI Mode. Allows usage of Java 2D or Imaging without GUI support.
         * @param value
	 */
	public static void setHeadless(boolean value) {
		setProperty("java.awt.headless", value);
	}

	public static void setJmxRemote(boolean value) {
		setProperty("com.sun.management.jmxremote", value);
	}

	public static boolean isHeadless() {
		return GraphicsEnvironment.isHeadless();
	}

	public static void setHttpProxy(String host, Integer port) {
		setHttpProxy(host, port, toStringArray(getLocalHostNames(true, true)));
	}

	public static void setHttpProxy(String host, Integer port, String... nonProxyHosts) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String nonProxyHost : nonProxyHosts) {
			if (first) {
				first = false;
			} else {
				sb.append("|");
			}
			sb.append(nonProxyHost);
		}
		setHttpProxy(host, port, sb.toString());
	}

	public static void setHttpProxy(String host, Integer port, String nonProxyHosts) {
		System.setProperty("http.proxyHost", isBlank(host) ? "" : host);
		System.setProperty("http.proxyPort", port == null ? "" : port.toString());
		System.setProperty("http.nonProxyHosts", isBlank(nonProxyHosts) ? "" : nonProxyHosts);
	}

	public static String getHttpProxyHost() {
		String value = getProperty("http.proxyHost");
		if (isBlank(value)) {
                        return null;
                }
		return value;
	}

	public static Integer getHttpProxyPort() {
		String value = getProperty("http.proxyPort");
		if (isBlank(value)) {
                        return null;
                }
		return parseInt(value);
	}

	public static String getJavaRuntimeVersion() {
		return getProperty("java.runtime.version");
	}

	public static String getJavaHome() {
		return getProperty("java.home");
	}

	public static String getFileEncoding() {
		return getProperty("file.encoding");
	}

	public static void setFileEncoding(String charset) {
		setProperty("file.encoding", charset);
	}

	public static String getUsersName() {
		return getProperty("user.name");
	}

	public static String getUsersHomePath() {
		return getProperty("user.home");
	}

	public static String getFileSeparator() {
		return getProperty("file.separator");
	}

	public static String getPathSeparator() {
		return getProperty("path.separator");
	}

	public static void setProperty(String name, boolean value) {
		setProperty(name, valueOf(value));
	}

	public static void setProperty(String name, String value) {
		System.setProperty(name, value);
	}

	public static ThreadGroup getRootThreadGroup() {
		ThreadGroup g = currentThread().getThreadGroup();
		while (true) {
			ThreadGroup parent = g.getParent();
			if (parent == null) {
                                break;
                        }
			g = parent;
		}
		return g;
	}

	public static Collection<Thread> getActiveThreads() {
		ThreadGroup tg = getRootThreadGroup();
		int count = tg.activeCount();
		Thread[] threads = new Thread[count];
		tg.enumerate(threads);
		return asList(threads);
	}

	public static boolean equals(Object a, Object b) {
                if (a == b) {
                        return true;
                }
		if (a != null) {
                        return a.equals(b);
                }
		return false;
	}

	public static <T> int compare(Comparable<T> a, Comparable<T> b) {
		if (a == null || b == null) {
			if (a == null && b == null) {
                                return 0;
                        }
			if (a == null) {
				return -1;
			} else {
				return 1;
			}
		}
		return a.compareTo((T) b);
	}

	public static boolean isWindows() {
		return !isUnixFileSystem();
	}

	private static Boolean unixFileSystem;

	public static boolean isUnixFileSystem() {
		if (unixFileSystem == null) {
			File[] roots = listRoots();
			unixFileSystem = roots.length == 1 && "/".equals(roots[0].getPath());
		}
		return unixFileSystem;
	}

	private Sys() {}

}
