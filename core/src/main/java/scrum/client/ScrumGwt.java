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

import ilarkesto.core.base.Str;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.gwt.client.AGwtEntity;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;

import java.util.Collection;

import scrum.client.admin.User;
import scrum.client.collaboration.CommentsWidget;
import scrum.client.collaboration.EmoticonSelectorWidget;
import scrum.client.common.AScrumGwtEntity;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.project.Project;
import scrum.client.project.Requirement;
import scrum.client.workspace.Navigator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ScrumGwt extends Gwt {

	public static String getLoginUrl() {
		return GWT.getHostPageBaseURL() + "login.html";
	}

	public static boolean isCurrentUserProductOwner() {
		Scope scope = Scope.get();
		Project project = scope.getComponent(Project.class);
		User user = scope.getComponent(User.class);
		return project.isProductOwner(user);
	}

	public static String getEstimationAsString(Float estimation, String suffix) {
		String s = getEstimationAsString(estimation);
		return s == null ? null : s + " " + suffix;
	}

	public static String getEstimationAsString(Float estimation) {
		if (estimation == null) return null;
		if (estimation < 0.0f) return "?";
		if (estimation == 0f) return "0";
		if (estimation <= 0.5f) return estimation.toString();
		return String.valueOf(estimation.intValue());
	}

	public static Float getNextEstimationValue(Float value) {
		for (Float f : Requirement.getWorkEstimationFloatValues()) {
			if (f >= value) { return f; }
		}
		throw new IllegalArgumentException("No next value for " + value);
	}

	public static Float getPrevEstimationValue(Float value) {
		for (int i = Requirement.getWorkEstimationFloatValues().length - 1; i > 0; i--) {
			Float f = Requirement.getWorkEstimationFloatValues()[i];
			if (f <= value) { return f; }
		}
		throw new IllegalArgumentException("No previous value for " + value);
	}

	public static String getReferenceAndLabel(AGwtEntity entity) {
		StringBuilder sb = new StringBuilder();
		if (entity instanceof ReferenceSupport) {
			sb.append(((ReferenceSupport) entity).getReference()).append(" ");
		}
		if (entity instanceof LabelSupport) {
			sb.append(((LabelSupport) entity).getLabel());
		} else {
			sb.append(entity.toString());
		}
		return sb.toString();
	}

	public static <E extends AScrumGwtEntity> Widget createReferencesWidget(Collection<E> entities) {
		return new HTML(createHtmlReferences(entities));
	}

	public static <E extends AScrumGwtEntity> String createHtmlReferences(Collection<E> entities) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (AScrumGwtEntity entity : entities) {
			if (!(entity instanceof ReferenceSupport)) continue;
			if (first) {
				first = false;
			} else {
				sb.append(", ");
			}
			sb.append(createHtmlReference(entity));
		}
		return sb.toString();
	}

	public static String createHtmlReference(AScrumGwtEntity entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href='").append(Navigator.getEntityHref(entity)).append("' title='")
				.append(Str.toHtml(((LabelSupport) entity).getLabel())).append("' class='reference'>");
		sb.append(((ReferenceSupport) entity).getReference());
		sb.append("</a>");
		return sb.toString();
	}

	public static boolean isEntityReferenceOrWikiPage(String s) {
		if (s == null) return false;
		if (s.startsWith("[") && s.endsWith("]")) return true;
		return isEntityReference(s);
	}

	public static boolean isEntityReference(String s) {
		if (s.length() < 4) return false;
		if (!Character.isDigit(s.charAt(3))) return false;

		boolean prefixOk = false;
		for (String prefix : ScrumGwtApplication.REFERENCE_PREFIXES) {
			if (s.startsWith(prefix)) {
				prefixOk = true;
				break;
			}
		}
		if (!prefixOk) return false;

		int len = s.length();
		for (int i = 3; i < len; i++) {
			if (!Character.isDigit(s.charAt(i))) return false;
		}
		try {
			String number = s.substring(3);
			Integer.parseInt(number);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	public static String getPeriodToAsShortestString(String prefix, DateAndTime dateAndTime, String suffix) {
		if (dateAndTime == null) return null;
		return prefix + getPeriodToAsShortestString(dateAndTime) + suffix;
	}

	public static String getPeriodToAsShortestString(DateAndTime dateAndTime) {
		if (dateAndTime == null) return null;
		return dateAndTime.getPeriodToNow().toShortestString();
	}

	public static Widget createEmoticonsAndComments(AScrumGwtEntity entity) {
		TableBuilder tb = ScrumGwt.createFieldTable();
		tb.addFieldRow("My emoticon", new EmoticonSelectorWidget(entity));
		tb.addRow(Gwt.createSpacer(1, 5));
		tb.addRow(new CommentsWidget(entity), 2);
		return tb.createTable();
	}

	public static Widget createActionsPanel(Widget... widgets) {
		FlowPanel panel = new FlowPanel();
		panel.setStyleName("ActionsPanel");
		for (Widget widget : widgets) {
			panel.add(widget);
		}
		return panel;
	}

	public static TableBuilder createFieldTable() {
		TableBuilder tb = new TableBuilder();
		tb.setCellPadding(2);
		tb.setColumnWidths("100px");
		return tb;
	}

	public static HTML createPdfLink(String text, String pdfId, AScrumGwtEntity entity) {
		return createPdfLink(text, pdfId, "entityId", entity.getId());
	}

	public static HTML createPdfLink(String text, String pdfId, String parameterKey, String parameterValue,
			String parameter2Key, String parameter2Value) {
		return createPdfLink(text, pdfId, parameterKey + "=" + parameterValue + "&" + parameter2Key + "="
				+ parameter2Value);
	}

	public static HTML createPdfLink(String text, String pdfId, String parameterKey, String parameterValue) {
		return createPdfLink(text, pdfId, parameterKey + "=" + parameterValue);
	}

	public static HTML createPdfLink(String text, String pdfId, String parameters) {
		Project project = Scope.get().getComponent(Project.class);
		assert project != null;
		return createServletDownloadLink("pdf.pdf?projectId=" + project.getId() + "&pdfId=" + pdfId + "&" + parameters,
			text);
	}

	public static String toHtml(AScrumGwtEntity entity, String label) {
		return createHtmlReference(entity) + " " + escapeHtml(label);
	}

}
