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

import ilarkesto.fp.Function;

public abstract class ACallback<R> implements Callback<R> {

	private Callback parentCallback;
	private Function<R, Object> resultConverter;
	private boolean called;

	public ACallback() {
		this(null, null);
	}

	public ACallback(Callback parentCallback) {
		this(parentCallback, null);
	}

	public ACallback(Callback parentCallback, Function<R, Object> resultConverter) {
		super();
		this.parentCallback = parentCallback;
		this.resultConverter = resultConverter;
	}

	@Override
	public void onSuccess(R result) {
		if (called) {
                        throw new IllegalStateException("Callback already called: " + this);
                }
		called = true;
		Object callbackResult;
		if (resultConverter != null) {
			callbackResult = resultConverter.eval(result);
		} else {
			callbackResult = result;
		}
		parentCallback.onSuccess(callbackResult);
	}

	@Override
	public void onError(Throwable error) {
		if (called) {
                        throw new IllegalStateException("Callback already called: " + this);
                }
		called = true;
		parentCallback.onError(error);
	}

}
