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
package ilarkesto.core.scope;

import static ilarkesto.core.logging.ClientLog.ERROR;
import static ilarkesto.core.logging.ClientLog.INFO;

/**
 *
 *
 */
public class NonConcurrentScopeManager extends ScopeManager {

	private Scope currentScope;

    /**
     *
     * @param rootScope
     */
    protected NonConcurrentScopeManager(Scope rootScope) {
		super(rootScope);
		this.currentScope = getRootScope();
	}

    /**
     *
     * @param rootScopeName
     * @param componentReflector
     * @return
     */
    public static NonConcurrentScopeManager createCascadingScopeInstance(String rootScopeName,
			ComponentReflector componentReflector) {
		return new NonConcurrentScopeManager(new CascadingScope(null, rootScopeName, componentReflector));
	}

	@Override
	public Scope getScope() {
		assert currentScope != null;
		return currentScope;
	}

    /**
     *
     * @param scope
     * @return
     */
    public Scope setScope(Scope scope) {
		if (scope == null) {
                    ERROR("Scope == null!");
        }
		this.currentScope = scope;
		INFO("Scope activated:", scope);
		return scope;
	}

}
