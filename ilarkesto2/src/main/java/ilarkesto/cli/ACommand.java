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
package ilarkesto.cli;

import ilarkesto.auth.LoginRequiredException;
import static ilarkesto.core.base.Str.removeSuffix;
import static ilarkesto.di.Context.get;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author erik
 * @param <A>
 */
public abstract class ACommand<A extends Arguments> {

    /**
     *
     * @return
     */
    public abstract A createArguments();

    /**
     *
     * @throws LoginRequiredException
     */
    public abstract void assertPermissions() throws LoginRequiredException;

    /**
     *
     * @param arguments
     * @return
     * @throws BadSyntaxException
     * @throws CommandExecutionFailedException
     */
    public abstract Object execute(A arguments) throws BadSyntaxException, CommandExecutionFailedException;

    /**
     *
     * @return
     */
    public String getName() {
		String name = getClass().getSimpleName();
		name = removeSuffix(name, "Command");
		name = name.toLowerCase();
		return name;
	}

	private String description;

    /**
     *
     * @param description
     */
    public ACommand(String description) {
		this.description = description;
	}

    /**
     *
     * @return
     */
    public String getDescription() {
		return description;
	}

    /**
     *
     * @return
     */
    public Collection getAliases() {
		return new ArrayList(1);
	}

    /**
     *
     * @return
     */
    public String getUsage() {
		return getName() + createArguments().getUsage();
	}

    /**
     *
     * @param <T>
     * @param target
     * @return
     */
    protected final <T> T autowire(T target) {
		return get().autowire(target);
	}

}
