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

import ilarkesto.gwt.client.TableBuilder;

import java.util.List;

import scrum.client.common.AScrumGwtEntity;
import scrum.client.common.AScrumWidget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class EmoticonsWidget extends AScrumWidget {

	private AScrumGwtEntity entity;

	private SimplePanel wrapper;

	public EmoticonsWidget(AScrumGwtEntity entity) {
		super();
		this.entity = entity;
	}

	@Override
	protected Widget onInitialization() {
		wrapper = new SimplePanel();
		wrapper.setStyleName("EmoticonsWidget");
		return wrapper;
	}

	@Override
	protected void onUpdate() {
		wrapper.clear();

		List<Emoticon> emoticons = entity.getEmoticons();
		TableBuilder tb = new TableBuilder();
		tb.setWidth(null);
		for (Emoticon emoticon : emoticons) {
			// if (emoticon.getOwner() == currentUser) continue;
			tb.add(createEmoticonWidget(emoticon));
		}
		// tb.add(createEmoticonEditor());
		wrapper.setWidget(tb.createTable());
	}

	private Widget createEmoticonWidget(Emoticon emoticon) {
		Image img = new Image(getEmotionImage(emoticon.getEmotion()), 0, 0, 16, 16);
		img.setTitle(emoticon.getTooltip());
		Style imgStyle = img.getElement().getStyle();
		imgStyle.setMarginLeft(1, Unit.PX);
		imgStyle.setMarginTop(1, Unit.PX);
		return img;
	}

	private String getEmotionImage(String emotion) {
		if (emotion == null) emotion = "none";
		return "emoticons/" + emotion + ".png";
	}
}
