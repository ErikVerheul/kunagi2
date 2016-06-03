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

import ilarkesto.core.logging.Log;
import static ilarkesto.core.time.Tm.getCurrentTimeMillis;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.IO.closeQuiet;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.arraycopy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for easy handling of external processes and executing commands.
 */
public final class Proc {

        public static void main(String[] args) {
                Proc proc = new Proc("sudo");
                proc.addParameter("ls");
                proc.start();
                proc.getOutput();
        }
        private static final Log log = Log.get(Proc.class);
        private static final List<Proc> runningProcs = new LinkedList<>();
        private File workingDir;
        private Map<String, String> environment;
        private final String command;
        private List<String> parameters;
        private long startTime;
        private StreamGobbler outputGobbler;
        private StreamGobbler errorGobbler;
        private PrintStream inputPrintStream;
        private Process process;
        private StringBuffer output = new StringBuffer();
        private Integer returnCode;
        private ShutdownHook shutdownHook;

        public static List<Proc> getRunningProcs() {
                synchronized (runningProcs) {
                        return new ArrayList<>(runningProcs);
                }
        }

        public synchronized void reset() {
                cleanup();
                process = null;
                output.setLength(0);
                returnCode = null;
        }

        private void cleanup() {
                if (isRunning()) {
                        throw new IllegalStateException("Process still running: " + toString());
                }

                runningProcs.remove(this);

                if (shutdownHook != null) {
                        getRuntime().removeShutdownHook(shutdownHook);
                }
                shutdownHook = null;
                if (outputGobbler != null) {
                        outputGobbler.close();
                }
                outputGobbler = null;
                if (errorGobbler != null) {
                        errorGobbler.close();
                }
                errorGobbler = null;
                if (inputPrintStream != null) {
                        closeQuiet(inputPrintStream);
                }
                inputPrintStream = null;
        }

        /**
         * Starts the process and waits until it ends. Returns output when return code is 0. Otherwise throws Exception.
         */
        public static String execute(String command, String... parameters) throws UnexpectedReturnCodeException {
                return execute(null, command, parameters);
        }

        public static String execute(File workDir, String command, String... parameters)
                throws UnexpectedReturnCodeException {
                Proc proc = new Proc(command);
                proc.setWorkingDir(workDir);
                for (String parameter : parameters) {
                        proc.addParameter(parameter);
                }
                return proc.execute();
        }

        @Override
        public String toString() {
                return getCommandLine();
        }

        /**
         * Starts the process and waits until it ends. Returns output when return code is 0. Otherwise throws Exception.
         */
        public String execute() throws UnexpectedReturnCodeException {
                return execute(0);
        }

        public String execute(int... acceptableReturnCodes) throws UnexpectedReturnCodeException {
                start();
                int rc = getReturnCode();
                boolean rcOk = false;
                for (int acceptableReturnCode : acceptableReturnCodes) {
                        if (rc == acceptableReturnCode) {
                                rcOk = true;
                        }
                }
                if (!rcOk) {
                        throw new UnexpectedReturnCodeException(rc, getCommandLine(), getOutput());
                }
                return getOutput();
        }

        public synchronized String getCommandLine() {
                StringBuilder cmdline = new StringBuilder();
                cmdline.append(command);
                if (parameters != null) {
                        for (String parameter : parameters) {
                                cmdline.append(" ").append(parameter);
                        }
                }
                return cmdline.toString();
        }

        /**
         * Start the process.
         */
        public synchronized void start() {
                if (process != null) {
                        throw new RuntimeException("Process already started.");
                }

                int paramLen = parameters == null ? 0 : parameters.size();
                String[] cmdarray = new String[paramLen + 1];
                cmdarray[0] = command;
                if (paramLen > 0) {
                        arraycopy(parameters.toArray(), 0, cmdarray, 1, paramLen);
                }
                if (log.isDebugEnabled()) {
                        StringBuilder sb = new StringBuilder();
                        if (workingDir == null) {
                                sb.append(">");
                        } else {
                                sb.append(workingDir).append(">");
                        }
                        for (String s : cmdarray) {
                                sb.append(" ").append(s);
                        }
                        log.debug(sb.toString());
                }
		startTime = getCurrentTimeMillis();
                try {
                        process = getRuntime().exec(cmdarray, getEnvParameters(), workingDir);
                } catch (IOException ex) {
                        throw new RuntimeException(ex);
                }
                runningProcs.add(this);
                shutdownHook = new ShutdownHook();
                getRuntime().addShutdownHook(shutdownHook);
                outputGobbler = new StreamGobbler(process.getInputStream());
                errorGobbler = new StreamGobbler(process.getErrorStream());
        }

        public synchronized void destroy() {
                if (process == null) {
                        throw new RuntimeException("Process not started yet.");
                }
                if (!isRunning()) {
                        return;
                }
                process.destroy();
                getReturnCode();
                cleanup();
        }

        public synchronized boolean isRunning() {
                if (process == null) {
                        return false;
                }
                return returnCode == null;
        }

        public synchronized Process getProcess() {
                return process;
        }

        public long getStartTime() {
                return startTime;
        }

        public long getRunTime() {
		return getCurrentTimeMillis() - startTime;
        }

        /**
         * Wait until the process is finishes.
         */
        public void waitFor() {
                getReturnCode();
        }

        /**
         * Gets the return code of the process. Waits until process finishes.
         */
        public synchronized int getReturnCode() {
                if (returnCode == null) {
                        if (process == null) {
                                throw new RuntimeException("Process not started yet.");
                        }
                        closeQuiet(process.getOutputStream());
                        try {
                                process.waitFor();
                        } catch (InterruptedException ex) {
                                process = null;
                                cleanup();
                                throw new RuntimeException("Command interrupted: " + command, ex);
                        }
                        returnCode = process.exitValue();
                        cleanup();
                        log.debug("    " + command + ":", "rc:", returnCode);
                }
                return returnCode;
        }

        /**
         * Gets the standard output of the process.
         */
        public String getOutput() {
                if (output == null) {
                        return null;
                }
                synchronized (output) {
                        return output.toString().trim();
                }
        }

        public String popOutput() {
                if (output == null) {
                        throw new RuntimeException("Process not started yet.");
                }
		String s = getOutput();
		output = new StringBuffer();
		return s;
	}

        private String[] getEnvParameters() {
                if (environment == null || environment.isEmpty()) {
                        return null;
                }
                String[] env = new String[environment.size()];
                int i = 0;
                for (Map.Entry<String, String> entry : environment.entrySet()) {
                        env[i++] = entry.getKey() + "=" + entry.getValue();
                }
                return env;
        }

        public void setEnvironment(Map<String, String> environment) {
                this.environment = environment;
        }

        public void addEnvironmentParameter(String name, String value) {
                if (environment == null) {
                        environment = new HashMap<>();
                }
                environment.put(name, value);
        }

        public Proc(String command) {
                this.command = command;
        }

        public synchronized Proc setParameters(List<String> parameters) {
                this.parameters = parameters;
                return this;
        }

        public synchronized Proc addParameter(String parameter) {
                if (parameters == null) {
                        parameters = new ArrayList<>(1);
                }
                parameters.add(parameter);
                return this;
        }

        public synchronized Proc addParameters(String... parameters) {
                for (String parameter : parameters) {
                        addParameter(parameter);
                }
                return this;
        }

        public synchronized Proc setWorkingDir(File workingDir) {
                this.workingDir = workingDir;
                return this;
        }

        // --- sub classes ---
        private class StreamGobbler extends Thread {

                private InputStream is;

                StreamGobbler(InputStream is) {
                        this.is = is;
                        setDaemon(true);
                        super.start();
                }

                @Override
                public void run() {
                        try {
                                InputStreamReader isr = new InputStreamReader(is, UTF_8);
                                BufferedReader br = new BufferedReader(isr);
                                String line = null;
                                while (isRunning() && (line = br.readLine()) != null) {
                                        synchronized (output) {
                                                output.append(line).append("\n");
                                        }
                                }
                        } catch (IOException ex) {
				// return;
                        } finally {
                                close();
                        }
                }

                public void close() {
                        closeQuiet(is);
                }
        }

        public static class UnexpectedReturnCodeException extends RuntimeException {

                private int rc;
                private String cmdline;
                private String output;

                public UnexpectedReturnCodeException(int rc, String cmdline, String output) {
                        super("Command rc=" + rc + ": " + cmdline + "\n" + output);
                        this.rc = rc;
                        this.cmdline = cmdline;
                        this.output = output;
                }

                public int getRc() {
                        return rc;
                }

                public String getOutput() {
                        return output;
                }

                public String getCmdline() {
                        return cmdline;
                }
        }

        private class ShutdownHook extends Thread {

                @Override
                public void run() {
                        Proc.this.destroy();
                }
        }
}
