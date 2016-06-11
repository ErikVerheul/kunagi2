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
package scrum.client.communication;

import ilarkesto.core.logging.Log;
import ilarkesto.core.time.Tm;

import java.util.LinkedList;

import scrum.client.DataTransferObject;
import scrum.client.core.ApplicationStartedEvent;
import scrum.client.core.ApplicationStartedHandler;
import scrum.client.project.Requirement;
import scrum.client.workspace.BlockCollapsedEvent;
import scrum.client.workspace.BlockCollapsedHandler;
import scrum.client.workspace.BlockExpandedEvent;
import scrum.client.workspace.BlockExpandedHandler;

import com.google.gwt.user.client.Timer;

public class Pinger extends GPinger implements ServerDataReceivedHandler, BlockExpandedHandler, BlockCollapsedHandler,
		ApplicationStartedHandler {

	private static Log log = Log.get(Pinger.class);

	public static final int MIN_DELAY = 1000;
	public static final int MAX_DELAY = 5000;

	private Timer timer;
	private int maxDelay = MAX_DELAY;
	private long lastDataReceiveTime = Tm.getCurrentTimeMillis();
	private LinkedList<Long> pingTimes = new LinkedList<Long>();

	@Override
	public void onApplicationStarted(ApplicationStartedEvent event) {
		timer = new Timer() {

			@Override
			public void run() {
				if (!serviceCaller.containsServiceCall(PingServiceCall.class)) {
					final long start = Tm.getCurrentTimeMillis();
					new PingServiceCall().execute(new Runnable() {

						@Override
						public void run() {
							long time = Tm.getCurrentTimeMillis() - start;
							pingTimes.add(time);
							if (pingTimes.size() > 10) pingTimes.removeFirst();
						}
					});
				}
				reschedule();
			}
		};
		reschedule();
	}

	public void shutdown() {
		log.info("Shutting down");
		if (timer == null) return;
		timer.cancel();
		timer = null;
	}

	@Override
	public void onServerDataReceived(ServerDataReceivedEvent event) {
		DataTransferObject data = event.getData();
		if (data.containsEntities()) {
			lastDataReceiveTime = Tm.getCurrentTimeMillis();
			reschedule();
		}
	}

	@Override
	public void onBlockCollapsed(BlockCollapsedEvent event) {
		deactivatePowerPolling();
	}

	@Override
	public void onBlockExpanded(BlockExpandedEvent event) {
		Object object = event.getObject();
		if (object instanceof Requirement) {
			Requirement requirement = (Requirement) object;
			if (requirement.isWorkEstimationVotingActive()) activatePowerPolling();
		}
	}

	public void reschedule() {
		if (timer == null) return;
		long idle = Tm.getCurrentTimeMillis() - lastDataReceiveTime;
		idle = (int) (idle * 0.15);
		if (idle < MIN_DELAY) idle = MIN_DELAY;
		if (idle > maxDelay) idle = maxDelay;
		timer.scheduleRepeating((int) idle);
	}

	private void activatePowerPolling() {
		maxDelay = MIN_DELAY;
		log.debug("PowerPolling activated");
	}

	private void deactivatePowerPolling() {
		if (maxDelay == MAX_DELAY) return;
		maxDelay = MAX_DELAY;
		lastDataReceiveTime = Tm.getCurrentTimeMillis();
		log.debug("PowerPolling deactivated");
	}

	public Long getAvaragePingTime() {
		if (pingTimes.isEmpty()) return null;
		long sum = 0;
		for (Long time : pingTimes) {
			sum += time;
		}
		return sum / pingTimes.size();
	}

	public String getAvaragePingTimeMessage() {
		Long time = getAvaragePingTime();
		if (time == null) return null;
		return "Current response time: " + time + " ms.";
	}

}
