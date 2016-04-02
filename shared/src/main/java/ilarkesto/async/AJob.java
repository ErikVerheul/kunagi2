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

import ilarkesto.core.base.Str;
import static ilarkesto.core.base.Str.concat;

public abstract class AJob<R> implements Job<R> {

	private Callback<R> callback;
	private boolean executed;

	@Override
	public final AJob<R> setCallback(Callback<R> callback) {
		if (this.callback != null) {
                        throw new IllegalStateException("Callback is already set");
                }
		if (executed) {
                        throw new IllegalStateException("Job already executed: " + toString());
                }
		this.callback = callback;
		return this;
	}

	@Override
	public R runJob() {
		if (executed) {
                        throw new IllegalStateException("Job already executed: " + toString());
                }
		executed = true;
		return null;
	}

	public final void start() {
		if (executed) {
                        throw new IllegalStateException("Job already executed: " + toString());
                }
		Async.start(this);
	}

	public final void start(Callback<R> callback) {
		setCallback(callback);
		start();
	}

	@Override
	public void onSuccess(R result) {
		callback.onSuccess(result);
	};

	@Override
	public void onError(Throwable error) {
		callback.onError(error);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	protected final String toString(Object... params) {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		if (params != null && params.length > 0) {
			sb.append(concat("(", ")", ",", params));
		}
		return sb.toString();
	}
}
