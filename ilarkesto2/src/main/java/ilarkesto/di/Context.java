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
package ilarkesto.di;

import ilarkesto.logging.Log;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import static java.lang.Thread.currentThread;
import java.util.Set;

/**
 * Represents a context in which a thread is running. Contexts can be nested.
 */
public final class Context {

    private static final Log LOG = Log.get(Context.class);

    private static ThreadLocal<Context> threadLocal = new ThreadLocal<>();

    private static Context rootContext;

    private Context parent;
    private String name;
    private MultiBeanProvider beanProvider;

    private Context(Context parent, String name) {
        this.parent = parent;
        this.name = name;

        beanProvider = new MultiBeanProvider();
        if (parent != null) {
            beanProvider.addBeanProvider(parent.beanProvider);
        }
    }

    /**
     *
     * @param name
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public final String getName() {
        return name;
    }

    /**
     *
     * @param heanProvider
     */
    public final void addBeanProvider(Object heanProvider) {
        this.beanProvider.addBeanProvider(heanProvider);
    }

    /**
     *
     * @return
     */
    public final BeanProvider getBeanProvider() {
        return this.beanProvider;
    }

    /**
     *
     * @return
     */
    public final Context getParentContext() {
        return parent;
    }

    /**
     *
     * @param name
     * @return
     */
    public final Context createSubContext(String name) {
        Context context = new Context(this, name);
        LOG.debug("createSubContext: Context created:", context);
        context.bindCurrentThread();
        return context;
    }

    /**
     *
     */
    @SuppressWarnings(value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", justification = "Static variable is set to null only")
    public final void destroy() {
        releaseCurrentThread();
        if (parent != null) {
            parent.bindCurrentThread();
        } else {
            // root context
            threadLocal = null;
        }
    }

    /**
     *
     */
    public final void bindCurrentThread() {
        if (threadLocal != null) {
            threadLocal.set(this);
        }
        currentThread().setName(toString());
    }

    private void releaseCurrentThread() {
        if (threadLocal != null) {
            threadLocal.set(null);
        }
        currentThread().setName("<no context>");
    }

    @Override
    public final String toString() {
        return parent == null ? name : parent + " > " + name;
    }

    /**
     *
     * @return
     */
    public static Context getRootContext() {
        if (rootContext == null) {
            throw new RuntimeException("Root context does not exist. Call createRootContext()");
        }
        return rootContext;
    }

    /**
     *
     * @param name
     * @return
     */
    public static synchronized Context createRootContext(String name) {
        if (rootContext != null) {
            rootContext.destroy();
            LOG.debug("createRootContext: Existing rootcontext is destoyed. ");
        }
        rootContext = new Context(null, name);
        rootContext.bindCurrentThread();
        return rootContext;
    }

    /**
     *
     * @return
     */
    public static Context get() {
        Context context = threadLocal.get();
        if (context == null) {
            context = getRootContext();
        }
        return context;
    }

	// --- helper ---
    /**
     *
     * @param <T>
     * @param target
     * @return
     */
    public final <T> T autowire(T target) {
        return beanProvider.autowire(target);
    }

    /**
     *
     * @param type
     */
    public final void autowireClass(Class type) {
        beanProvider.autowireClass(type);
    }

    /**
     * Gets all beans by their type. All beans instanceof the given type are
     * returned.
     *
     * @param <T>
     * @param type
     * @return
     */
    public final <T> Set<T> getBeansByType(Class<T> type) {
        return beanProvider.getBeansByType(type);
    }

    /**
     * Provides a set of all existing bean names.
     *
     * @return
     */
    public final Set<String> getBeanNames() {
        return beanProvider.beanNames();
    }

    /**
     * Gets a bean by name.
     *
     * @param <T>
     * @param beanName
     * @return
     */
    public final <T> Object getBean(String beanName) {
        return beanProvider.getBean(beanName);
    }

}
