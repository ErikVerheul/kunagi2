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
package scrum.client;

import ilarkesto.core.scope.Scope;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class ScrumJs {

	static native void initialize() /*-{
									$wnd.scrum = new Object();
									$wnd.scrum.showEntityByReference = function(reference) {
									@scrum.client.ScrumJs::showEntityByReference(Ljava/lang/String;)(reference);
									}
									}-*/;

	public static void showEntityByReference(String reference) {
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showEntityByReference(reference);
	}

	public static String createShowEntityByReferenceLink(String reference, String entityLabel) {
		String labelString = "";
		if (entityLabel != null) {
			entityLabel = entityLabel.replace("'", "`");
			entityLabel = entityLabel.replace("\"", "`");
			labelString = " title='" + entityLabel + "'";
		}
		return "<a class='reference' onclick='window.scrum.showEntityByReference(\"" + reference + "\")'" + labelString
				+ ">" + reference + "</a>";
	}

	public static native String regexTextToHtml(String text) /*-{

																// escape html
																text = text.replace( /&/g , "&amp;" );
																text = text.replace( /</g , "&lt;" );
																text = text.replace( />/g , "&gt;" );
																
																// create links for entity references: "req5" or "tsk23"
																text = text.replace( /\b((req|tsk|iss|qlt|rsk|imp)\d+)\b/g , "<a onclick='window.scrum.showEntityByReference(\"$1\")'>" + "$1"  + "</a>" );
																
																// create links for wiki pages: "[Start]" or "[MyPage]"
																text = text.replace( /(\[(\w+)\])/g , "<a onclick='window.scrum.showEntityByReference(\"$1\")'>" + "$2"  + "</a>" );
																
																return text;
																}-*/;

}
