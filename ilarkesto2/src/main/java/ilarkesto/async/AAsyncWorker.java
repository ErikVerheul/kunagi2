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
package ilarkesto.async;

public abstract class AAsyncWorker implements AsyncWorker {

	public abstract void runJob(Runnable job);

	public abstract void runCallback(Runnable callback);

	@Override
	public final void start(Job job) {
		runJob(new JobRunner(job));
	}

	private class JobRunner implements Runnable {

		private Job job;

		public JobRunner(Job job) {
			super();
			this.job = job;
		}

		@Override
		public void run() {
			Object result;
			try {
				result = job.runJob();
			} catch (Throwable ex) {
				runCallback(new FailureRunnable(job, ex));
				return;
			}
			runCallback(new SuccessRunnable(job, result));
		}
	}

	private static class SuccessRunnable implements Runnable {

		private Job job;
		private Object result;

		public SuccessRunnable(Job job, Object result) {
			super();
			this.job = job;
			this.result = result;
		}

		@Override
		public void run() {
			job.onSuccess(result);
		}

	}

	private static class FailureRunnable implements Runnable {

		private Job job;
		private Throwable error;

		public FailureRunnable(Job job, Throwable result) {
			super();
			this.job = job;
			this.error = result;
		}

		@Override
		public void run() {
			job.onError(error);
		}

	}

}
