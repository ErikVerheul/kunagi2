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

import com.google.gwt.user.client.ui.Widget;
import scrum.client.common.ABlockWidget;
import scrum.client.common.AScrumAction;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.dnd.TrashSupport;

/**
 *
 * @author erik
 */
public class ForumItemBlock extends ABlockWidget<ForumSupport> implements TrashSupport {

    /**
     *
     * @param header
     */
    @Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		ForumSupport entity = getObject();
		header.addText(entity.getLabelModel());
		header.addText(entity.getLastCommentAgoModel(), true);
		if (entity instanceof Subject) {
			header.addMenuAction(new DeleteSubjectAction((Subject) entity));
		}
	}

    /**
     *
     * @param header
     */
    @Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		ForumSupport entity = getObject();
		header.setDragHandle(entity.getReference());
	}

    /**
     *
     * @return
     */
    @Override
	protected Widget onExtendedInitialization() {
		return new ForumItemWidget(getObject());
	}

    /**
     *
     * @return
     */
    @Override
	public AScrumAction getTrashAction() {
		ForumSupport entity = getObject();
		if (entity instanceof Subject) {
                    return new DeleteSubjectAction((Subject) entity);
        }
		return null;
	}

    /**
     *
     */
    public static final BlockWidgetFactory<ForumSupport> FACTORY = new BlockWidgetFactory<ForumSupport>() {

		@Override
		public ForumItemBlock createBlock() {
			return new ForumItemBlock();
		}

	};

}
