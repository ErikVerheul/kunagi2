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
package ilarkesto.di;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Helper for implementing own BeanProviders.
 *
 * @author wko
 */
public abstract class ABeanProvider implements BeanProvider {

        @SuppressWarnings("unchecked")
        @Override
        public final <T> Set<T> getBeansByType(Class<T> type) {
                Set<T> result = new HashSet<>();
                for (String beanName : beanNames()) {
                        if (type.isAssignableFrom(getBeanType(beanName))) {
                                result.add((T) getBean(beanName));
                        }
                }
                return result;
        }

        @Override
        public final Map<String, Object> getAllBeans() {
                Map<String, Object> result = new HashMap<>();
                for (String beanName : beanNames()) {
                        result.put(beanName, getBean(beanName));
                }
                return result;
        }

        // --- autowireing ---
        @Override
        public final <T> T autowire(T bean) {
                return Autowire.autowire(bean, this, objectStringMapper);
        }

        @Override
        public final <T> Class<T> autowireClass(Class<T> type) {
                return Autowire.autowireClass(type, this, objectStringMapper);
        }
        // --- dependencies ---
        protected ObjectStringMapper objectStringMapper;

        public final void setObjectStringMapper(ObjectStringMapper objectStringMapper) {
                this.objectStringMapper = objectStringMapper;
        }
}
