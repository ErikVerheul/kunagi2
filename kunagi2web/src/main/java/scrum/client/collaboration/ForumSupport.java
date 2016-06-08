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

import ilarkesto.core.time.DateAndTime;
import ilarkesto.gwt.client.editor.AFieldModel;

import java.util.Comparator;

import scrum.client.common.AScrumGwtEntity;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;

import com.google.gwt.user.client.ui.Widget;

public interface ForumSupport extends LabelSupport, ReferenceSupport {

	Widget createForumItemWidget();

	public static final Comparator<ForumSupport> COMPARATOR = new Comparator<ForumSupport>() {

		@Override
		public int compare(ForumSupport aFs, ForumSupport bFs) {
			DateAndTime aTime = ((AScrumGwtEntity) aFs).getLatestCommentDateAndTime();
			DateAndTime bTime = ((AScrumGwtEntity) bFs).getLatestCommentDateAndTime();
			if (aTime == null && bTime == null) return 0;
			if (aTime == null) return -1;
			if (bTime == null) return 1;
			return bTime.compareTo(aTime);
		}
	};

	AFieldModel<String> getLabelModel();

	AFieldModel<String> getLastCommentAgoModel();
}
