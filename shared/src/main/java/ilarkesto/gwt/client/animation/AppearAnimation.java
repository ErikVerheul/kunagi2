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
package ilarkesto.gwt.client.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Widget;

public class AppearAnimation extends Animation {

	private Widget widget;
	private int totalHeight;
	private int minHeight = 3;

	public AppearAnimation(Integer height, Widget widget) {
		this.totalHeight = height == null ? 25 : height;
		this.widget = widget;
		widget.getElement().getStyle().setProperty("height", minHeight + "px");
	}

	@Override
	protected void onStart() {
		super.onStart();
		widget.getElement().getStyle().setProperty("overflow", "hidden");
	}

	@Override
	protected void onComplete() {
		super.onComplete();
		widget.getElement().getStyle().setProperty("height", "auto");
		widget.getElement().getStyle().setProperty("overflow", "auto");
	}

	@Override
	protected void onUpdate(double progress) {
		int height = (int) (progress * this.totalHeight);
		if (height < minHeight) {
                        height = minHeight;
                }
		widget.getElement().getStyle().setProperty("height", height + "px");
	}

}