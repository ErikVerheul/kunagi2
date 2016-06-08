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
package ilarkesto.testng;

import ilarkesto.async.AAsyncWorker;
import ilarkesto.async.AsyncWorker;
import ilarkesto.async.Callback;
import ilarkesto.async.Job;
import org.testng.annotations.Test;

public abstract class AAsyncJobTest<R> extends ATest {

	protected abstract Job<R> createJob();

	protected abstract void assertResult(R result);

	@Test
	public void testJob() {
		Callback<R> callback = new Callback<R>() {

			@Override
			public void onSuccess(R result) {
				assertResult(result);
			}

			@Override
			public void onError(Throwable error) {
				fail("Async job failed", error);
			}

		};

		Job<R> job = createJob();
		job.setCallback(callback);

		AsyncWorker engine = new TestAsyncEngine();
		engine.start(job);
	}

	class TestAsyncEngine extends AAsyncWorker {

		@Override
		public void runJob(Runnable job) {
			job.run();
		}

		@Override
		public void runCallback(Runnable callback) {
			callback.run();
		}

	}

}
