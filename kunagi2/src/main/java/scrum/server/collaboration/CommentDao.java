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
package scrum.server.collaboration;

import ilarkesto.base.AFactoryCache;
import ilarkesto.base.Cache;
import ilarkesto.core.base.Str;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.fp.Predicate;
import ilarkesto.persistence.AEntity;

import java.util.Set;

public class CommentDao extends GCommentDao {

	@Override
	public void clearCaches() {
		super.clearCaches();
		commentsByParentIdCache.clear();
	}

	public Set<Comment> getPublishedCommentsByParent(final AEntity parent) {
		return getEntities(new Predicate<Comment>() {

			@Override
			public boolean test(Comment c) {
				return c.isPublished() && c.isParent(parent);
			}
		});
	}

	private Cache<String, Set<Comment>> commentsByParentIdCache = new AFactoryCache<String, Set<Comment>>() {

		@Override
		public Set<Comment> create(final String parentId) {
			return getEntities(new Predicate<Comment>() {

				@Override
				public boolean test(Comment e) {
					return e.getParent().getId().equals(parentId);
				}
			});
		}

	};

	public Set<Comment> getCommentsByParentId(final String parentId) {
		return commentsByParentIdCache.get(parentId);
	}

	public Comment postComment(AEntity entity, String text, String name, String email, boolean publish) {
		assert entity != null;
		if (Str.isBlank(name)) name = "anonymous";
		Comment comment = newEntityInstance();
		comment.setParent(entity);
		comment.setText(text);
		comment.setAuthorName(name);
		comment.setAuthorEmail(email);
		comment.setDateAndTime(DateAndTime.now());
		comment.setPublished(publish);

		saveEntity(comment);
		return comment;
	}

}