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
package ilarkesto.persistence;

import ilarkesto.base.UtlExtend;
import ilarkesto.core.logging.Log;
import ilarkesto.fp.Predicate;
import ilarkesto.id.IdentifiableResolver;
import static ilarkesto.persistence.Persist.test;
import static java.lang.Thread.currentThread;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Transaction implements IdentifiableResolver<AEntity> {

	private static final Log log = Log.get(Transaction.class);

	private static int count = 0;
	private final int no;

	private final EntityStore entityStore;

	private final String threadName;
	private final Set<AEntity> entitiesToSave = new HashSet<>();
	private final Set<AEntity> entitiesToDelete = new HashSet<>();
	private final Set<AEntity> entitiesRegistered = new HashSet<>();

	public Transaction(EntityStore entityStore) {
		synchronized (Transaction.class) {
			no = ++count;
		}
		this.entityStore = entityStore;
		threadName = currentThread().getName();
	}

	synchronized void saveEntity(AEntity entity) {
		if (entity == null) {
                        throw new NullPointerException("entity");
                }
		entity.getId();
		if (entitiesToSave.contains(entity) || entitiesToDelete.contains(entity)) {
                        return;
                }
		log.debug("SAVE", toStringWithType(entity), "@", this);
		entitiesToSave.add(entity);
	}

	synchronized void deleteEntity(AEntity entity) {
		if (entitiesToDelete.contains(entity)) {
                        return;
                }
		log.debug("DELETE", toStringWithType(entity), "@", this);
		entitiesToDelete.add(entity);
		entitiesToSave.remove(entity);
	}

	synchronized void registerEntity(AEntity entity) {
		entitiesRegistered.add(entity);
	}

	private boolean committed;

	synchronized void commit() {
		if (committed) {
                        throw new RuntimeException("Transaction already committed: " + this);
                }
		committed = true;

		if (entitiesToDelete.isEmpty() && entitiesToSave.isEmpty()) {
			log.debug("Empty Transaction committed:", this);
			return;
		} else {
			log.info("Committing transaction:", this);
		}

		Set<AEntity> integratedEntities = new HashSet<>(entitiesToSave.size());

		int loopcount = 0;
		while (!integratedEntities.containsAll(entitiesToSave)) {

			if (loopcount > 0) {
				HashSet<AEntity> tmp = new HashSet<>(entitiesToSave);
				tmp.removeAll(integratedEntities);
				log.debug("  Entities changed after ensuring integrity:", tmp);
			}

			if (loopcount > 1000) {
                                throw new RuntimeException("Maximum loops reached while commiting:" + this);
                        }

			entitiesToSave.removeAll(entitiesToDelete);
			for (AEntity entity : new HashSet<>(entitiesToSave)) {
				log.debug("Ensuring integrity for", entity.getClass().getSimpleName(), entity.getId());
				entity.ensureIntegrity();
				integratedEntities.add(entity);
			}
			entitiesToSave.removeAll(entitiesToDelete);

			loopcount++;
		}

		log.debug("Persisting entities:", entitiesToSave, entitiesToDelete);
		entityStore.persist(entitiesToSave, entitiesToDelete);

		log.debug("Transaction committed:", this);
		entitiesToSave.clear();
		entitiesToDelete.clear();
		entitiesRegistered.clear();
	}

	synchronized boolean isPersistent(String id) {
		AEntity result = entityStore.getById(id);
		if (result != null) {
                        return true;
                }

		for (AEntity entity : entitiesToSave) {
			if (id.equals(entity.getId())) {
                                return true;
                        }
		}

		// ignore registeredEntities!
		return false;
	}

	@Override
	public synchronized AEntity getById(String id) {
		AEntity result = entityStore.getById(id);
		if (result == null && !entitiesToSave.isEmpty()) {
			for (AEntity entity : entitiesToSave) {
				if (id.equals(entity.getId())) {
					result = entity;
					break;
				}
			}
		}
		if (result == null && !entitiesRegistered.isEmpty()) {
			for (AEntity entity : entitiesRegistered) {
				if (id.equals(entity.getId())) {
					result = entity;
					break;
				}
			}
		}
		if (result != null && entitiesToDelete.contains(result)) {
                        return null;
                }
		return result;
	}

	@Override
	public synchronized List<AEntity> getByIds(Collection<String> ids) {
		List<AEntity> result = entityStore.getByIds(ids);
		for (AEntity entity : entitiesToSave) {
			if (ids.contains(entity.getId())) {
				result.remove(entity);
				result.add(entity);
			}
		}
		for (AEntity entity : entitiesRegistered) {
			if (ids.contains(entity.getId())) {
				result.remove(entity);
				result.add(entity);
			}
		}
		result.removeAll(entitiesToDelete);
		return result;
	}

	synchronized Set<AEntity> getEntities(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		Set<AEntity> result = entityStore.getEntities(typeFilter, entityFilter);
		for (AEntity entity : entitiesToSave) {
			if (test(entity, typeFilter, entityFilter)) {
                                result.add(entity);
                        }
		}
		for (AEntity entity : entitiesRegistered) {
			if (test(entity, typeFilter, entityFilter)) {
                                result.add(entity);
                        }
		}
		result.removeAll(entitiesToDelete);
		return result;
	}

	int getEntitiesCount(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		return entityStore.getEntitiesCount(typeFilter, entityFilter);
	}

	synchronized AEntity getEntity(Predicate<Class> typeFilter, Predicate<AEntity> entityFilter) {
		AEntity result = entityStore.getEntity(typeFilter, entityFilter);
		if (result == null) {
			for (AEntity entity : entitiesToSave) {
				if (test(entity, typeFilter, entityFilter) && !entitiesToDelete.contains(entity)) {
                                        return entity;
                                }
			}
			for (AEntity entity : entitiesRegistered) {
				if (test(entity, typeFilter, entityFilter) && !entitiesToDelete.contains(entity)) {
                                        return entity;
                                }
			}
		} else {
			if (entitiesToDelete.contains(result)) {
                                return null;
                        }
		}
		return result;
	}

	@Override
	public synchronized String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("#").append(no);
		sb.append(" (").append(threadName).append(")");
		if (!entitiesToSave.isEmpty()) {
			sb.append("\n    SAVE: ").append(toString(entitiesToSave));
		}
		if (!entitiesRegistered.isEmpty()) {
			sb.append("\n    REGISTERED: ").append(toString(entitiesRegistered));
		}
		if (!entitiesToDelete.isEmpty()) {
			sb.append("\n    DELETE: ").append(toString(entitiesToDelete));
		}
		return sb.toString();
	}

	private String toString(Collection<AEntity> entities) {
		StringBuilder sb = new StringBuilder();
		for (AEntity entity : entities) {
			if (entity == null) {
				sb.append("\n        null");
			} else {
				sb.append("\n        ").append(entity.getClass().getSimpleName()).append(": ")
						.append(entity.toString());
			}
		}
		return sb.toString();
	}

	private String toStringWithType(Object o) {
		try {
			return UtlExtend.toStringWithType(o);
		} catch (Exception ex) {
			return o.getClass().getSimpleName();
		}
	}

}
