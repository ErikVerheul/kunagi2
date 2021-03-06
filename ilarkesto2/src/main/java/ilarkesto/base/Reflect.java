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
package ilarkesto.base;

import static ilarkesto.base.StrExtend.concat;
import static ilarkesto.base.StrExtend.uppercaseFirstLetter;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import static java.beans.Introspector.getBeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utilities for reflection and meta programming.
 */
public abstract class Reflect {

    /**
     * Call the <code>initialize()</code> method when it exists.
     */
    public static void invokeInitializeIfThere(Object o) {
        Method m = getDeclaredMethod(o.getClass(), "initialize");
        if (m == null) {
            return;
        }
        try {
            m.invoke(o);
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object getProperty(Object o, String name) {
        String methodSuffix = uppercaseFirstLetter(name);
        Method method = getDeclaredMethod(o.getClass(), "get" + methodSuffix);
        if (method == null) {
            method = getDeclaredMethod(o.getClass(), "is" + methodSuffix);
            Class<?> returnType = method.getReturnType();
            if (returnType != boolean.class && returnType != Boolean.class) {
                method = null;
            }
        }
        if (method == null) {
            throw new RuntimeException("No getter method for property: " + o.getClass().getSimpleName() + "." + name);
        }
        try {
            return method.invoke(o);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("Failed to invoke getter method: " + o.getClass().getSimpleName() + "."
                    + method.getName() + "()", ex);
        }
    }

    public static Object getFieldValue(Class<?> c, String fieldName) {
        return getFieldValue(c, null, fieldName);
    }

    public static Object getFieldValue(Class<?> c, Object object, String fieldName) {
        Field field = getDeclaredField(c, fieldName);
        if (field == null) {
            return null;
        }
        try {
            return field.get(object);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setProperty(Object o, String name, Object value) {
        Method setter = getSetterMethod(o.getClass(), name);
        if (setter == null) {
            throw new RuntimeException("Property setter not found: " + o.getClass() + "." + name);
        }
        Class<?>[] types = setter.getParameterTypes();
        if (types.length != 1) {
            throw new RuntimeException("Setter has illegar arguments: " + o.getClass() + "." + setter.getName());
        }
        Object val = value;
        if (value != null) {
            Class<?> type = types[0];
            if (!type.isAssignableFrom(val.getClass())) {
                if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                    val = Boolean.valueOf(val.toString());
                } else if (type.equals(Integer.class) || type.equals(int.class)) {
                    val = Integer.valueOf(val.toString());
                } else if (type.equals(Long.class) || type.equals(long.class)) {
                    val = Long.valueOf(val.toString());
                } else if (type.equals(Float.class) || type.equals(float.class)) {
                    val = Float.valueOf(val.toString());
                } else if (type.equals(Double.class) || type.equals(double.class)) {
                    val = Double.valueOf(val.toString());
                } else {
                    val = newInstance(type, val);
                }
            }
        }
        invoke(o, setter, val);
    }

    public static <T extends Object> T newInstance(Class<T> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T extends Object> T newInstance(Class<T> type, Object... constructorParameters) {
        try {
            Constructor<T> constructor = type.getConstructor(getClasses(constructorParameters));
            return constructor.newInstance(constructorParameters);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Object invoke(Object object, String method, Object... parameters) {
        Method m = getDeclaredMethodUsingAutoboxing(object.getClass(), method, getClasses(parameters));
        if (m == null) {
            throw new RuntimeException("Method does not exist: " + object.getClass() + "." + method + "("
                    + concat(getClassSimpleNames(parameters), ", ") + ")");
        }
        return invoke(object, m, parameters);
    }

    public static Object invoke(Object object, Method method, Object... parameters) {
        method.setAccessible(true);
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean isTypesCompatible(Class<?>[] methodTypes, Class<?>[] parameterTypes, boolean autoboxing) {
        if (methodTypes.length != parameterTypes.length) {
            return false;
        }
        for (int i = 0; i < methodTypes.length; i++) {
            if (!isTypeCompatible(methodTypes[i], parameterTypes[i], autoboxing)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isTypeCompatible(Class<?> methodType, Class<?> parameterType, boolean autoboxing) {
        if (parameterType == null) {
            return true;
        }
        if (methodType.equals(parameterType)) {
            return true;
        }
        if (!autoboxing) {
            return false;
        }
        // check autoboxing
        if (methodType.equals(Float.class) && parameterType.equals(float.class)) {
            return true;
        }
        if (methodType.equals(float.class) && parameterType.equals(Float.class)) {
            return true;
        }
        if (methodType.equals(Integer.class) && parameterType.equals(int.class)) {
            return true;
        }
        if (methodType.equals(int.class) && parameterType.equals(Integer.class)) {
            return true;
        }
        if (methodType.equals(Double.class) && parameterType.equals(double.class)) {
            return true;
        }
        if (methodType.equals(double.class) && parameterType.equals(Double.class)) {
            return true;
        }
        if (methodType.equals(Long.class) && parameterType.equals(long.class)) {
            return true;
        }
        if (methodType.equals(long.class) && parameterType.equals(Long.class)) {
            return true;
        }
        return false;
    }

    public static Method getDeclaredMethodUsingAutoboxing(Class<?> clazz, String name, Class<?>... parameterTypes) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (!name.equals(m.getName())) {
                continue;
            }
            if (isTypesCompatible(m.getParameterTypes(), parameterTypes, true)) {
                return m;
            }
        }
        if (clazz != Object.class) {
            return getDeclaredMethodUsingAutoboxing(clazz.getSuperclass(), name, parameterTypes);
        }
        return null;
    }

    public static Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        Method m = null;
        try {
            m = clazz.getDeclaredMethod(name, parameterTypes);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            if (clazz != Object.class) {
                m = getDeclaredMethod(clazz.getSuperclass(), name, parameterTypes);
            }
        }
        return m;
    }

    public static Method getSetterMethod(Class<?> clazz, String property) {
        Method m = null;
        String methodName = "set" + uppercaseFirstLetter(property);
        try {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == 1) {
                    m = method;
                    break;
                }
            }
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        }
        if (m == null) {
            if (clazz != Object.class) {
                m = getSetterMethod(clazz.getSuperclass(), property);
            }
        }
        return m;
    }

    public static Field getDeclaredField(Class<?> clazz, String name) {
        Field f = null;
        try {
            f = clazz.getDeclaredField(name);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchFieldException ex) {
            if (clazz != Object.class) {
                f = getDeclaredField(clazz.getSuperclass(), name);
            }
        }
        return f;
    }

    public static Class<?>[] getClasses(Object... objects) {
        Class<?>[] result = new Class<?>[objects.length];
        for (int i = 0; i < objects.length; i++) {
            result[i] = objects[i] == null ? null : objects[i].getClass();
        }
        return result;
    }

    public static String[] getClassSimpleNames(Class<?>... classes) {
        String[] names = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            names[i] = classes[i] == null ? null : classes[i].getSimpleName();
        }
        return names;
    }

    public static String[] getClassSimpleNames(Object... objects) {
        return getClassSimpleNames(getClasses(objects));
    }

    // -------------------
    // --- annotations ---
    // -------------------
    public static void processAnnotations(Object object, FieldAnnotationHandler handler) {
        processAnnotations(object, object.getClass(), handler);
    }

    public static void processAnnotations(Object object, Class<?> clazz, FieldAnnotationHandler handler) {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = fields.length - 1; i >= 0; i--) {
            Annotation[] annotations = fields[i].getAnnotations();
            for (Annotation annotation : annotations) {
                handler.handle(annotation, fields[i], object);
            }
        }

        Class<?> supa = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> intf : interfaces) {
            processAnnotations(object, intf, handler);
        }
        if (supa != null && !supa.equals(Object.class)) {
            processAnnotations(object, supa, handler);
        }
    }

    public static void processAnnotations(Object object, MethodAnnotationHandler handler) {
        processAnnotations(object, object.getClass(), handler);
    }

    public static void processAnnotations(Object object, Class<?> clazz, MethodAnnotationHandler handler) {
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = methods.length - 1; i >= 0; i--) {
            Annotation[] annotations = methods[i].getAnnotations();
            for (Annotation annotation : annotations) {
                handler.handle(annotation, methods[i], object);
            }
        }

        Class<?> supa = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> intf : interfaces) {
            processAnnotations(object, intf, handler);
        }
        if (supa != null && !supa.equals(Object.class)) {
            processAnnotations(object, supa, handler);
        }
    }

    public static void processAnnotations(Object object, PropertyMethodAnnotationHandler handler, boolean getter,
            boolean setter) {
        processAnnotations(object, object.getClass(), handler, getter, setter);
    }

    public static void processAnnotations(Object object, Class<?> clazz, PropertyMethodAnnotationHandler handler,
            boolean getter, boolean setter) {
        BeanInfo beanInfo;
        try {
            beanInfo = getBeanInfo(clazz);
        } catch (IntrospectionException ex) {
            throw new RuntimeException(ex);
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            if (getter) {
                Method method = propertyDescriptor.getReadMethod();
                if (method != null) {
                    Annotation[] annotations = method.getAnnotations();
                    for (Annotation annotation : annotations) {
                        handler.handle(annotation, propertyDescriptor, object);
                    }
                }
            }
            if (setter) {
                Method method = propertyDescriptor.getWriteMethod();
                if (method != null) {
                    Annotation[] annotations = method.getAnnotations();
                    for (Annotation annotation : annotations) {
                        handler.handle(annotation, propertyDescriptor, object);
                    }
                }
            }
        }
        Class<?> supa = clazz.getSuperclass();
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> intf : interfaces) {
            processAnnotations(object, intf, handler, getter, setter);
        }
        if (supa != null && !supa.equals(Object.class)) {
            processAnnotations(object, supa, handler, getter, setter);
        }
    }

    public static interface MethodAnnotationHandler {

        public void handle(Annotation annotation, Method method, Object object);

    }

    public static interface PropertyMethodAnnotationHandler {

        public void handle(Annotation annotation, PropertyDescriptor property, Object object);

    }

    public static interface FieldAnnotationHandler {

        public void handle(Annotation annotation, Field field, Object object);

    }
}
