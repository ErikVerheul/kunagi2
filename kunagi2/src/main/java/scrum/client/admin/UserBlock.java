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
package scrum.client.admin;

import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.AOutputViewEditWidget;
import ilarkesto.gwt.client.AnchorPanel;
import ilarkesto.gwt.client.TableBuilder;
import scrum.client.ScrumGwt;
import scrum.client.common.ABlockWidget;
import scrum.client.common.BlockHeaderWidget;
import scrum.client.common.BlockWidgetFactory;
import scrum.client.img.Img;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class UserBlock extends ABlockWidget<User> {

	private AnchorPanel iconWrapper;

	@Override
	protected void onInitializationHeader(BlockHeaderWidget header) {
		User user = getObject();
		iconWrapper = header.addIconWrapper();
		header.addText(user.getNameFullNameModel());
		header.addText(user.getLastLoginAgoModel(), true);
		header.addMenuAction(new ResetUserPasswordAction(user));
		header.addMenuAction(new ConfirmUserEmailAction(user));
		header.addMenuAction(new DisableUserAction(user));
		header.addMenuAction(new EnableUserAction(user));
		header.addMenuAction(new DeleteUserAction(user));
	}

	@Override
	protected void onUpdateHeader(BlockHeaderWidget header) {
		User user = getObject();
		header.setDragHandle("usr");
		Image icon = null;
		if (user.isDisabled()) {
			icon = Img.bundle.usrDisabled().createImage();
			icon.setTitle("User is disabled.");
		} else if (!user.isEmailVerified()) {
			icon = Img.bundle.usrEmailUnverified().createImage();
			icon.setTitle("Users email is not verified.");
		}
		iconWrapper.setWidget(icon);
	}

	@Override
	protected Widget onExtendedInitialization() {
		final User user = getObject();
		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.addFieldRow("Name", user.getNameModel());
		tb.addFieldRow("E-Mail", user.getEmailModel());
		tb.addFieldRow("Full name", user.getFullNameModel());
		if (!Scope.get().getComponent(SystemConfig.class).isOpenIdDisabled()) {
			tb.addFieldRow("OpenID", user.getOpenIdModel());
		}
		tb.addFieldRow("Projects", new AOutputViewEditWidget() {

			@Override
			protected void onViewerUpdate() {
				setViewer(ScrumGwt.createToHtmlItemsWidget(user.getProjects()));
			}
		});
		return tb.createTable();
	}

	public static final BlockWidgetFactory<User> FACTORY = new BlockWidgetFactory<User>() {

		@Override
		public UserBlock createBlock() {
			return new UserBlock();
		}
	};
}
