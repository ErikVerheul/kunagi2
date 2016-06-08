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
package ilarkesto.rss;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import ilarkesto.base.TmExtend;
import static ilarkesto.base.TmExtend.FORMAT_RFC822;
import ilarkesto.base.UtlExtend;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.integration.jdom.JDom;
import static ilarkesto.integration.jdom.JDom.addElement;
import static ilarkesto.integration.jdom.JDom.addTextElement;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.sort;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;

public class Rss20Builder {

	private String title;
	private String link;
	private String description;
	private String language;
	private DateAndTime pubDate;
	private String image;

	private List<Item> items = new ArrayList<>();

	public Rss20Builder sortItems() {
		sort(items);
		return this;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setPubDate(DateAndTime pubDate) {
		this.pubDate = pubDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Item addItem() {
		Item item = new Item();
		items.add(item);
		return item;
	}

	public Document createXmlDocument() {
		Document doc = new Document();

		Element eRss = new Element("rss");
		doc.setRootElement(eRss);
		eRss.setAttribute("version", "2.0");

		Element eChannel = addElement(eRss, "channel");
		if (title != null) {
                        addTextElement(eChannel, "title", title);
                }
		if (link != null) {
                        addTextElement(eChannel, "link", link);
                }
		if (description != null) {
                        addTextElement(eChannel, "description", description);
                }
		if (language != null) {
                        addTextElement(eChannel, "language", language);
                }
		if (pubDate != null) {
                        addTextElement(eChannel, "pubDate", FORMAT_RFC822.format(pubDate));
                }
		if (image != null) {
			Element eImage = addElement(eChannel, "image");
			addTextElement(eImage, "url", image);
			addTextElement(eImage, "title", "Logo");
		}

		for (Item item : items) {
			Element eItem = addElement(eChannel, "item");
			item.appendTo(eItem);
		}

		return doc;
	}

	public void removeDuplicates(Item item) {
		for (Item i : new ArrayList<>(items)) {
			if (i == item) {
                                continue;
                        }
			if (UtlExtend.equals(i.guid, item.guid) || (i.enclosure != null && UtlExtend.equals(i.enclosure, item.enclosure))
					|| UtlExtend.equals(i.title, item.title)) {
				items.remove(item);
			}
		}
	}

	public void write(OutputStream out, String encoding) {
		JDom.write(createXmlDocument(), out, encoding);
	}

	@Override
	public String toString() {
		return createXmlDocument().toString();
	}

	public static class Item implements Comparable<Item> {

		private String title;
		private String description;
		private String link;
		private String guid;
		private DateAndTime pubDate;
		private String enclosure;

		private void appendTo(Element eItem) {
			if (title != null) {
                                addTextElement(eItem, "title", title);
                        }
			if (description != null) {
                                addTextElement(eItem, "description", description);
                        }
			if (link != null) {
                                addTextElement(eItem, "link", link);
                        }
			if (guid != null) {
                                addTextElement(eItem, "guid", guid);
                        }
			if (pubDate != null) {
                                addTextElement(eItem, "pubDate", FORMAT_RFC822.format(pubDate));
                        }
			if (enclosure != null) {
				Element eEnclosure = addElement(eItem, "enclosure");
				eEnclosure.setAttribute("url", enclosure);
			}
		}

		public void setLink(String link) {
			this.link = link;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setGuid(String guid) {
			this.guid = guid;
		}

		public void setPubDate(DateAndTime pubDate) {
			this.pubDate = pubDate;
		}

		public void setEnclosure(String enclosure) {
			this.enclosure = enclosure;
		}

                @SuppressWarnings(value = "EQ_COMPARETO_USE_OBJECT_EQUALS", justification = "The value of compareTo returns zero if and only if equals returns true")
		@Override
		public int compareTo(Item o) {
			return o.pubDate.compareTo(pubDate);
		}

	}

}