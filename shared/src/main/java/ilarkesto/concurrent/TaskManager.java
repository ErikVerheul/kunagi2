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
package ilarkesto.concurrent;

import static ilarkesto.base.UtlExtend.getRootCause;
import static ilarkesto.base.UtlExtend.toSet;
import static ilarkesto.base.UtlExtend.toStringWithType;
import ilarkesto.core.logging.Log;
import static ilarkesto.core.time.Tm.getCurrentTimeMillis;
import ilarkesto.di.Context;
import static ilarkesto.di.Context.get;
import static java.lang.Long.MAX_VALUE;
import static java.lang.Thread.sleep;
import static java.util.Collections.synchronizedSet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TaskManager {

	private static final Log LOG = Log.get(TaskManager.class);

	private final Set<ATask> runningTasks = synchronizedSet(new HashSet<ATask>());
	private final Set<ATask> scheduledTasks = synchronizedSet(new HashSet<ATask>());
	private final ScheduledExecutorService scheduledExecutorService = newScheduledThreadPool(5,
		new DeamonThreadFactory());
	private final ExecutorService executorService = newCachedThreadPool(new DeamonThreadFactory());
	private boolean shutdownInProgress;

	public void waitForRunningTasks() {
		waitForRunningTasks(MAX_VALUE);
	}

	public void waitForRunningTasks(long maxWaitTime) {
		long now = getCurrentTimeMillis();
		long tryUntilTime = now + maxWaitTime;
		if (tryUntilTime < now) {
                        tryUntilTime = MAX_VALUE;
                }
		Set<ATask> tasks;
		while ((!(tasks = getRunningTasks()).isEmpty()) && getCurrentTimeMillis() < tryUntilTime) {
			LOG.info("Waiting for running tasks:", tasks);
			try {
				sleep(100);
			} catch (InterruptedException ex) {
				LOG.info("    Waiting for running tasks aborted by InterruptedException");
				return;
			}
		}
		LOG.info("All tasks finished");
	}

	public Set<ATask> getRunningTasks() {
		return toSet(runningTasks.toArray(new ATask[runningTasks.size()]));
	}

	public void abortAllRunningTasks() {
		for (ATask task : getRunningTasks()) {
			LOG.info("Aborting task:", task);
			task.abort();
		}
	}

	public void shutdown(long waitUntilKill) {
		shutdownInProgress = true;
		unscheduleAllTasks();
		scheduledExecutorService.shutdownNow();
		abortAllRunningTasks();
		waitForRunningTasks(waitUntilKill);
		executorService.shutdownNow();
	}

	public Set<ATask> getScheduledTasks() {
		return new HashSet<>(scheduledTasks);
	}

	public void start(ATask task) {
		if (shutdownInProgress) {
			LOG.info("Task execution prevented, cause shutdown in progress:", task);
			return;
		}
		TaskRunner runner = new TaskRunner(task, false, get());
		executorService.execute(runner);
	}

	public void scheduleWithFixedDelay(ATask task, long delay) {
		scheduleWithFixedDelay(task, delay, delay);
	}

	public void scheduleWithFixedDelay(ATask task, long initialDelay, long delay) {
		scheduledTasks.add(task);
		scheduledExecutorService.scheduleWithFixedDelay(new TaskRunner(task, true, get()), initialDelay, delay, MILLISECONDS);
		LOG.info("Scheduled task:", task);
	}

	public boolean unschedule(ATask task) {
		return scheduledTasks.remove(task);
	}

	public void unscheduleAllTasks() {
		if (!scheduledTasks.isEmpty()) {
                        LOG.info("Removing scheduled tasks:", scheduledTasks);
                }
		scheduledTasks.clear();
	}

	class TaskRunner implements Runnable {

		private final ATask task;
		private final boolean repeating;
		private final Context parentContext;

		public TaskRunner(ATask task, boolean repeating, Context parentContext) {
			this.task = task;
			this.repeating = repeating;
			this.parentContext = parentContext;
		}

		@Override
		public void run() {
			Context context = parentContext.createSubContext("task:" + task.toString());
			// Thread.currentThread().setName(task.toString());
			runningTasks.add(task);
			// LOG.debug("Task started:", task);
			try {
				task.run();
			} catch (Throwable ex) {
				if (shutdownInProgress && getRootCause(ex) instanceof InterruptedException) {
					LOG.info("Task interrupted while shutdown:", toStringWithType(task));
				} else {
					LOG.error(ex);
				}
			}
			// LOG.debug("Task finished:", task);
			runningTasks.remove(task);
			if (repeating) {
                                task.reset();
                        }
			context.destroy();
			synchronized (TaskManager.this) {
				TaskManager.this.notifyAll();
			}
		}

	}

}
