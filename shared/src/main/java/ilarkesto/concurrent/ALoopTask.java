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
package ilarkesto.concurrent;

import ilarkesto.core.logging.Log;
import static ilarkesto.core.logging.Log.get;

public abstract class ALoopTask extends ATask {

	private Log log = get(getClass());

	protected abstract void iteration() throws InterruptedException;

	protected void beforeLoop() throws InterruptedException {}

	@Override
	protected final void perform() throws InterruptedException {
		beforeLoop();
		while (!isAbortRequested()) {
			try {
				iteration();
			} catch (Throwable ex) {
				try {
					onError(ex);
				} catch (Throwable ex1) {
					throw new RuntimeException("Task aborted by exception: " + toString(), ex1);
				}
			}
		}
	}

	protected void onError(Throwable ex) throws Throwable {
		log.error("Loop iteration failed:", ex);
	}

}
