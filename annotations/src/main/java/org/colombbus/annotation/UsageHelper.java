/**
 * Tangara is an educational platform to get started with programming.
 * Copyright (C) 2008 Colombbus (http://www.colombbus.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.colombbus.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Set of methods to use {@link Usage} annotation
 *
 * @version $Id: UsageHelper.java,v 1.3 2009/01/10 09:35:43 gwenael.le_roux Exp $
 * @author gwen
 */
public final class UsageHelper {

    /**
     * Singleton, no instantiation
     */
    private UsageHelper() {
        super();
    }

    /**
     * Find and format the list of the method usages available in an object
     *
     * @param obj
     *            the object to parse
     * @return a list of formatted usage sample
     * @see #findUsages(Class)
     */
    public static List<String> findUsages(Object obj) {
        if( obj == null )
            throw new IllegalArgumentException("obj argument is null"); //$NON-NLS-1$

        List<String> usageList = findUsages(obj.getClass());
        return usageList;
    }

    /**
     * Find and format the list of the methods with an {@link Usage} annotation.
     * <p>
     * The result returns by the method is a list of strings. Each string is a
     * method usage sample. For instance, if the following method is declared:
     * <blockquote> <code>@Usage("\"Bob\")</code> String getAddress(String name) { ... }
     * </blockquote> the returned string will be <code>getAddress("Bob")</code>
     * </p>
     *
     * @param clazz
     *            the class to parse
     * @return a list of method usage sample
     */
    public static List<String> findUsages(Class<?> clazz) {
        if( clazz == null )
            throw new IllegalArgumentException("clazz argument is null"); //$NON-NLS-1$

        List<String> usageList = new ArrayList<String>();
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            Usage usage = method.getAnnotation(Usage.class);
            if (usage != null) {
                String useSample = String.format("%s(%s)", method.getName(), //$NON-NLS-1$
                        usage.value());
                usageList.add(useSample);
            }
        }
        return usageList;
    }

    /**
     * Find and format the list of the method name annotated with {@link Usage} of an object
     * <p>
     * It only returns the method names, not the
     * </p>
     *
     * @param obj
     *            the object to parse
     * @return a list of formatted usage sample
     * @see #findUsages(Class)
     */
    public static List<String> findUsageMethods(Object obj) {
        if( obj == null )
            throw new IllegalArgumentException("obj argument is null"); //$NON-NLS-1$
        List<String> usageMethodList = findUsageMethods(obj.getClass());
        return usageMethodList;
    }

    /**
     * Find and format the list of the methods with an {@link Usage} annotation.
     * <p>
     * The result returns by the method is a list of strings. Each string is a
     * method name followed by '()'. For instance, if the following method is declared:
     * <blockquote> <code>@Usage("\"Bob\")</code> String getAddress(String name) { ... }
     * </blockquote> the returned string will be <code>getAddress()</code>
     * </p>
     *
     * @param clazz
     *            the class to parse
     * @return a list of method usage sample
     */
    public static List<String> findUsageMethods(Class<?> clazz) {
        if( clazz == null )
            throw new IllegalArgumentException("clazz argument is null"); //$NON-NLS-1$

        List<String> usageMethodList = new ArrayList<String>();
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            Usage usage = method.getAnnotation(Usage.class);
            if (usage != null) {
                String useSample = String.format("%s()", method.getName()); //$NON-NLS-1$
                usageMethodList.add(useSample);
            }
        }
        return usageMethodList;
    }


    /**
     * Find and provides the list of the methods annotated with {@link Usage} and
     * their usage samples.
     * <p>
     * The result returns by the method is a map. The key is the method name and
     * the value is a usage sample. For instance, if the following method is
     * declared: <blockquote> <code>@Usage("\"Bob\")</code> String getAddress(String name) { ...
     * } </blockquote> the returned entry will be {<code>getAddress()</code>,
     * <code>getAddress("Bob")</code>
     * </p>
     *
     * @param clazz
     *            the class to parse
     * @return a map of {method name, method usage sample}
     */
    public static Map<String, String> findUsagesMap(Class<?> clazz) {
        Map<String, String> usageList = new HashMap<String, String>();
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            Usage usage = method.getAnnotation(Usage.class);
            if (usage != null) {
                String methodName = method.getName() + "()";//$NON-NLS-1$
                String useSample = String.format("%s(%s)", method.getName(), //$NON-NLS-1$
                        usage.value());
                usageList.put(methodName, useSample);
            }
        }
        return usageList;
    }

}
