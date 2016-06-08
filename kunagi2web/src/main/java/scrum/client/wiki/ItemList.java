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
package scrum.client.wiki;

import ilarkesto.gwt.client.Gwt;
import java.util.ArrayList;
import java.util.List;

public class ItemList extends AWikiElement {

	private final List<Item> items = new ArrayList<Item>();
	boolean ordered;
	private int leadingSpacesLength;

	public ItemList(boolean ordered, int leadingSpacesLenght) {
		super();
		this.ordered = ordered;
		this.leadingSpacesLength = leadingSpacesLenght;
	}

	public void add(Paragraph item) {
		items.add(new Item(item, -1));
	}

	public void add(Paragraph item, String leadingSpaces, boolean ordered, int numberValue) {
		if (leadingSpaces.length() > 0 && leadingSpaces.length() > leadingSpacesLength) {
			Item lastItem = items.get(items.size() - 1);
			if (lastItem.list == null) {
				lastItem.list = new ItemList(ordered, leadingSpaces.length());
				lastItem.list.add(item);
			} else {
				lastItem.list.add(item, leadingSpaces.substring(leadingSpacesLength), ordered);
			}
			return;
		}
		items.add(new Item(item, numberValue));
	}

	public void add(Paragraph item, String leadingSpaces, boolean ordered) {
		add(item, leadingSpaces, ordered, -1);
	}

	public void setLeadingSpacesLength(int leadingSpacesLength) {
		this.leadingSpacesLength = leadingSpacesLength;
	}

	@Override
	String toHtml(HtmlContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append(ordered ? "<ol>" : "<ul>");
		for (Item item : items) {
			sb.append((item.getNumberValue() > -1) ? "<li value=\"" + item.getNumberValue() + "\">" : "<li>");
			sb.append(item.p.toHtml(context));
			if (item.containsList()) {
				sb.append(item.list.toHtml(context));
			}
			sb.append("</li>");
		}
		sb.append(ordered ? "</ol>" : "</ul>");
		return sb.toString();
	}

	public List<Item> getItems() {
		return items;
	}

	public boolean isOrdered() {
		return ordered;
	}

	@Override
	public String toString() {
		return "ItemList(" + Gwt.toString(items) + ")";
	}

	public static class Item {

		Paragraph p;
		ItemList list;
		int numberValue = -1;

		public Item(Paragraph p, int numberValue) {
			super();
			this.p = p;
			this.numberValue = numberValue;
		}

		public int getNumberValue() {
			return numberValue;
		}

		public Paragraph getParagraph() {
			return p;
		}

		public ItemList getList() {
			return list;
		}

		public boolean containsList() {
			return list != null;
		}

		@Override
		public String toString() {
			return containsList() ? "Item(" + p + "," + list + ")" : p.toString();
		}
	}

}
