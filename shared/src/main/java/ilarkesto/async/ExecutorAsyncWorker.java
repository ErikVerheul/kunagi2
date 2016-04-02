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

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class ExecutorAsyncWorker extends AAsyncWorker {

	private Executor jobExecutor;
	private Executor callbackExecutor;

	public ExecutorAsyncWorker(Executor jobExecutor, Executor callbackExecutor) {
		super();
		this.jobExecutor = jobExecutor;
		this.callbackExecutor = callbackExecutor;
	}

	public ExecutorAsyncWorker(Executor jobExecutor) {
		this(jobExecutor, newSingleThreadExecutor());
	}

	public ExecutorAsyncWorker() {
		this(newCachedThreadPool());
	}

	@Override
	public void runJob(Runnable job) {
		jobExecutor.execute(job);
	}

	@Override
	public void runCallback(Runnable callback) {
		callbackExecutor.execute(callback);
	}
}
