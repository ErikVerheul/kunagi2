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
package scrum.server.pr;

import ilarkesto.base.UtlExtend;
import ilarkesto.core.time.DateAndTime;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.server.admin.User;
import scrum.server.common.Numbered;

public class BlogEntry extends GBlogEntry implements Numbered, ReferenceSupport, LabelSupport, Comparable<BlogEntry> {

    public String getUrl() {
        String url = getProject().getHomepageUrl();
        if (url == null) {
            return null;
        }
        if (!url.endsWith("/")) {
            url += "/";
        }
        url += getReference() + ".html";
        return url;
    }

    @Override
    public String getLabel() {
        return getTitle();
    }

    @Override
    public boolean isVisibleFor(User user) {
        return getProject().isVisibleFor(user);
    }

    public String getReferenceAndLabel() {
        return getReference() + " " + getTitle();
    }

    @Override
    public String getReference() {
        return scrum.client.pr.BlogEntry.REFERENCE_PREFIX + getNumber();
    }

    @Override
    public void updateNumber() {
        if (getNumber() == 0) {
            setNumber(getProject().generateBlogEntryNumber());
        }
    }

    @Override
    public void ensureIntegrity() {
        super.ensureIntegrity();
        updateNumber();
        if (!isDateAndTimeSet()) {
            setDateAndTime(DateAndTime.now());
        }
    }

    @Override
    public int compareTo(BlogEntry other) {
        return UtlExtend.compare(getDateAndTime(), other.getDateAndTime()) * -1;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BlogEntry) || getDateAndTime() == null) {
            return false;
        }
        BlogEntry bm = (BlogEntry) o;
        return getDateAndTime().equals(bm.getDateAndTime());
    }


    @Override
    public String toString() {
        return getReferenceAndLabel();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
}