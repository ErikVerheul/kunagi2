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
package scrum.client.collaboration;

import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.LabelProvider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import scrum.client.admin.Auth;
import scrum.client.admin.User;
import scrum.client.common.AScrumGwtEntity;

public class Emoticon extends GEmoticon {

	public Emoticon(AScrumGwtEntity parent, String emotion) {
		setOwner(Scope.get().getComponent(Auth.class).getUser());
		setParent(parent);
		setEmotion(emotion);
	}

	public Emoticon(Map data) {
		super(data);
	}

	public String getEmotionLabel() {
		return getEmotionLabel(getEmotion());
	}

	public String getTooltip() {
		User owner = getOwner();
		if (owner == null) return getEmotionLabel();
		return owner.getName() + ": " + getEmotionLabel();
	}

	@Override
	public String toString() {
		return getOwner() + ":" + getEmotion();
	}

	public static String getEmotionLabel(String emotion) {
		if (emotion == null) return "-";
		return emotion;
	}

	public static transient final List<String> EMOTIONS = Arrays.asList("grin", "cry", "angry", "surprise");

	public static transient final List<String> EMOTIONS_LEGACY = Arrays.asList("grin", "smile", "wink", "surprise",
		"sad", "cry", "angry");

	public transient static LabelProvider<String> EMOTION_LABEL_PROVIDER = new LabelProvider<String>() {

		@Override
		public String getLabel(String emotion) {
			if (emotion == null) return null;
			return getEmotionLabel(emotion);
		}
	};

}
