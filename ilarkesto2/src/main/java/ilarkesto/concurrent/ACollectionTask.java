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
import java.util.Collection;
import static java.util.Collections.emptyList;

/**
 *
 *
 * @param <E>
 */
public abstract class ACollectionTask<E> extends ATask {

	private int count;
	private int index;
	private E element;

    /**
     *
     * @return
     * @throws InterruptedException
     */
    protected abstract Collection<E> prepare() throws InterruptedException;

    /**
     *
     * @param element
     * @throws InterruptedException
     */
    protected abstract void perform(E element) throws InterruptedException;

    /**
     *
     * @throws InterruptedException
     */
    @Override
	protected final void perform() throws InterruptedException {
		Collection<E> elements = prepare();
		if (elements == null) {
                        elements = emptyList();
                }
		count = elements.size();
		index = 0;
		for (E local_element : elements) {
			this.element = local_element;
			try {
				perform(local_element);
			} catch (Exception ex) {
				Exception rootCause = getRootCause(ex);
				if (!(rootCause instanceof InterruptedException)) {
                                        onElementError(local_element, ex);
                                }
			}
			if (isAbortRequested()) {
                                break;
                        }
			index++;
		}
		cleanup();
	}

    /**
     *
     * @param element
     * @param ex
     */
    protected void onElementError(E element, Exception ex) {
		throw new RuntimeException("Processing element failed: " + element, ex);
	}

    /**
     *
     * @return
     */
    public final int getIndex() {
		return index;
	}

    /**
     *
     * @throws InterruptedException
     */
    protected void cleanup() throws InterruptedException {}

    /**
     *
     * @param element
     * @return
     */
    protected String getProgressMessage(E element) {
		return element.toString();
	}

    /**
     *
     * @return
     */
    @Override
	public final String getProgressMessage() {
		return getProgressMessage(element);
	}

    /**
     *
     * @return
     */
    @Override
	public final float getProgress() {
		if (count == 0) {
                        return 1;
                }
		if (index == 0) {
                        return 0;
                }
		return (float) index / (float) count;
	}

}
