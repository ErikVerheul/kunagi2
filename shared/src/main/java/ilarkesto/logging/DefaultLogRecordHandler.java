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
package ilarkesto.logging;

import static ilarkesto.base.Sys.getUsersHomePath;
import static ilarkesto.base.Sys.getWorkDir;
import static ilarkesto.core.base.Str.format;
import ilarkesto.core.logging.Log;
import static ilarkesto.core.logging.Log.setLogRecordHandler;
import ilarkesto.core.logging.LogRecord;
import ilarkesto.core.logging.LogRecordHandler;
import static ilarkesto.io.IO.isFileWritable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import static java.lang.System.err;
import static java.lang.Thread.currentThread;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DefaultLogRecordHandler implements LogRecordHandler {
        // The logging thread has been removed. It does not work with Glassfish 3.1

        private static final Log LOG = Log.get(DefaultLogRecordHandler.class);
        public final DateFormat LOG_TIME_FORMAT = new SimpleDateFormat("EEE, dd. MMMM yyyy, HH:mm");
        public static final DefaultLogRecordHandler INSTANCE = new DefaultLogRecordHandler();
        private File logFile;
        private final LinkedList<LogRecord> latestRecords = new LinkedList<>();
        private final LinkedList<LogRecord> errorRecords = new LinkedList<>();

        public static void activate() {
        }

        private DefaultLogRecordHandler() {
                err.println("Initializing logging system");
                setLogRecordHandler(this);
        }

        @Override
        public void log(LogRecord record) {
                if (record == null) {
                        return;
                }
                synchronized (latestRecords) {
                        latestRecords.add(record);
                        if (latestRecords.size() > 256) {
                                latestRecords.remove(0);
                        }
                }

                if (record.level.isWarnOrWorse()) {
                        synchronized (errorRecords) {
                                if (!errorRecords.contains(record)) {
                                        errorRecords.add(record);
                                        if (errorRecords.size() > 256) {
                                                errorRecords.remove(0);
                                        }
                                }
                        }
                }

                record.context = currentThread().getName();
                appendToFile(record.toString());
//                if (record.level.isWarnOrWorse()) {
//                        appendToFile(record.toString());
//                }
        }

        public static boolean setLogFile(File file) {
                if (!isFileWritable(file)) {
                        return false;
                }
                INSTANCE.logFile = file;
                LOG.info("Log-file:", file);
                return true;
        }

        public static boolean setLogFileToHomeOrWorkdir(String name) {
                if (setLogFileToWorkdir(name)) {
                        return true;
                }
                return setLogFileToHome(name);
        }

        public static boolean setLogFileToHome(String name) {
                boolean ok = setLogFile(new File(getUsersHomePath() + "/" + name + ".log"));
                if (ok) {
                        return true;
                }
                return setLogFile(new File(getUsersHomePath() + "/webapps/" + name + ".log"));
        }

        public static boolean setLogFileToWorkdir(String name) {
                boolean ok = setLogFile(new File(getWorkDir() + "/" + name + ".log"));
                if (ok) {
                        return true;
                }
                return setLogFile(new File(getWorkDir() + "/webapps/" + name + ".log"));
        }

        @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "DM_DEFAULT_ENCODING", justification = "Used only when default encoding is needed")
        private void appendToFile(String record) {
                if (logFile == null) {
                        File runtimedataDir = new File("runtimedata");
                        if (runtimedataDir.exists() && runtimedataDir.isDirectory()) {
                                setLogFile(new File("runtimedata/error.log"));
                        } else {
                                setLogFile(new File("error.log"));
                        }
                }
                if (logFile == null) {
                        return;
                }

                try {
                        try (BufferedWriter out = new BufferedWriter(new FileWriter(logFile, logFile.length() < 1048576))) {
                                out.write("--------------------------------------------------------------------------------\n");
                                out.write(LOG_TIME_FORMAT.format(new Date()));
                                out.write(" -> ");
                                out.write(record);
                                out.write('\n');
                        }
                } catch (Exception e) {
                        err.println("Failed to write to logFile: " + logFile.getAbsolutePath() + ": " + format(e));
                }
        }

        public static List<LogRecord> getLatestRecords() {
                synchronized (INSTANCE.latestRecords) {
                        return new ArrayList<>(INSTANCE.latestRecords);
                }
        }

        public static List<LogRecord> getErrors() {
                synchronized (INSTANCE.errorRecords) {
                        return new ArrayList<>(INSTANCE.errorRecords);
                }
        }
}
