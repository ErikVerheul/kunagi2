/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.collaboration;

import scrum.client.common.AScrumAction;
import scrum.client.common.AScrumGwtEntity;

/**
 *
 * @author erik
 */
public class SetEmoticonAction extends AScrumAction {

	private AScrumGwtEntity entity;
	private String emotion;

    /**
     *
     * @param entity
     * @param emotion
     */
    public SetEmoticonAction(AScrumGwtEntity entity, String emotion) {
		super();
		this.entity = entity;
		this.emotion = emotion;
	}

	@Override
	public String getLabel() {
		return Emoticon.getEmotionLabel(emotion);
	}

	@Override
	protected void onExecute() {
		entity.setCurrentUserEmoticon(emotion);
	}

}
