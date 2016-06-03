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
package ilarkesto.gwt.server;

import ilarkesto.base.PermissionDeniedException;
import static ilarkesto.base.Sys.isDevelopmentMode;
import static ilarkesto.base.UtlExtend.toStringWithType;
import static ilarkesto.core.base.Utl.compare;
import ilarkesto.core.logging.Log;
import ilarkesto.core.time.DateAndTime;
import static ilarkesto.core.time.DateAndTime.now;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.gwt.client.ADataTransferObject;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.TransactionService;
import ilarkesto.webapp.AWebSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.UUID.randomUUID;

public abstract class AGwtConversation implements Comparable<AGwtConversation> {

	private static final Log LOG = Log.get(AGwtConversation.class);
	private static final TimePeriod DEFAULT_TIMEOUT = TimePeriod.minutes(2);

	private TransactionService transactionService;

	/**
	 * Data that will be transferred to the client at the next request.
	 */
	private ADataTransferObject nextData;
	private final Object nextDataLock = new Object();
	private final Map<AEntity, DateAndTime> remoteEntityModificationTimes = new HashMap<>();

	private final AWebSession session;
	private final int number;
	private DateAndTime lastTouched;

	protected abstract ADataTransferObject createDataTransferObject();

	public AGwtConversation(AWebSession session, int number) {
		super();
		this.session = session;
		this.number = number;

		nextData = createDataTransferObject();
		if (nextData != null) {
			nextData.developmentMode = isDevelopmentMode();
			nextData.entityIdBase = randomUUID().toString();
			nextData.conversationNumber = number;
		}

		touch();
	}

	public int getNumber() {
		return number;
	}

	public final void clearRemoteEntities() {
		remoteEntityModificationTimes.clear();
	}

	public final void clearRemoteEntitiesByType(Class<? extends AEntity> type) {
		List<AEntity> toRemove = new ArrayList<>();
		for (AEntity entity : remoteEntityModificationTimes.keySet()) {
			if (entity.getClass().equals(type)) {
                                toRemove.add(entity);
                        }
		}
		for (AEntity entity : toRemove) {
			remoteEntityModificationTimes.remove(entity);
		}
	}

	protected boolean isEntityVisible(AEntity entity) {
		return true;
	}

	protected void filterEntityProperties(AEntity entity, Map propertiesMap) {}

	public synchronized boolean isAvailableOnClient(AEntity entity) {
		return remoteEntityModificationTimes.containsKey(entity);
	}

	public synchronized void sendToClient(AEntity entity) {
		if (entity == null) {
                        return;
                }

		if (!transactionService.isPersistent(entity.getId())) {
			getNextData().addDeletedEntity(entity.getId());
			return;
		}

		if (!isEntityVisible(entity)) {
                        throw new PermissionDeniedException(entity + " is not visible");
                }

		DateAndTime timeRemote = remoteEntityModificationTimes.get(entity);
		DateAndTime timeLocal = entity.getLastModified();

		if (timeLocal.equals(timeRemote)) {
			LOG.debug("Remote entity already up to date:", toStringWithType(entity), "for", this);
			return;
		}

		Map propertiesMap = entity.createPropertiesMap();
		filterEntityProperties(entity, propertiesMap);

		getNextData().addEntity(propertiesMap);
		remoteEntityModificationTimes.put(entity, timeLocal);
		LOG.debug("Sending", toStringWithType(entity), "to", this);
	}

	public final void sendToClient(Collection<? extends AEntity> entities) {
		if (entities == null) {
                        return;
                }
		for (AEntity entity : entities) {
                        sendToClient(entity);
                }
	}

	public final ADataTransferObject popNextData() {
		if (nextData == null) {
                        return null;
                }
		synchronized (nextDataLock) {
			ADataTransferObject ret = nextData;
			nextData = createDataTransferObject();
			return ret;
		}
	}

	public ADataTransferObject getNextData() {
		return nextData;
	}

	public AWebSession getSession() {
		return session;
	}

	public final void touch() {
		lastTouched = now();
	}

	protected TimePeriod getTimeout() {
		return DEFAULT_TIMEOUT;
	}

	public final boolean isTimeouted() {
		return lastTouched.getPeriodToNow().isGreaterThen(getTimeout());
	}

	public final DateAndTime getLastTouched() {
		return lastTouched;
	}

	public synchronized void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public void invalidate() {}

	@Override
	public String toString() {
		return "#" + number + "@" + getSession();
	}

	@Override
	public int compareTo(AGwtConversation o) {
		return compare(o.getLastTouched(), getLastTouched());
	}
}
