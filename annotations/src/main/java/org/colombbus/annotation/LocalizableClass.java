/**
 * Tangara is an educational platform to get started with programming. Copyright
 * (C) 2008 Colombbus (http://www.colombbus.org)
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.colombbus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for translating automatically classes or methods.
 * 
 * @version $Id: LocalizableClass.java,v 1.3 2009/01/10 09:35:43 gwenael.le_roux
 *          Exp $
 * @author Benoit
 * @author gwen
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface LocalizableClass {

	/**
	 * Get the identifier of the property containing the translation of the
	 * element (type or method)
	 * 
	 * @return a non empty and non null text
	 */
	String value();

	/**
	 * Indicates if the inherited methods shall be also translated.
	 * <p>
	 * All the parents of the type are parsed.
	 * </p>
	 * 
	 * @return <code>true</code> if the parent shall be translated,
	 *         <code>false</code> otherwise
	 */
	boolean inherit() default false;

	/**
	 * Indicates if the current type shall be translated
	 * <p>
	 * By default, a type with a {@link LocalizableClass}
	 * org.colombbus.annotation is translated.
	 * </p>
	 * 
	 * @return <code>true</code> if the type shall be translated,
	 *         <code>false</code> otherwise
	 */
	boolean localize() default true;

	/**
	 * Get the base name of the resource bundle containing the translation
	 * 
	 * @return a bundle base name ('localize' by default)
	 */
	String bundle() default "localize";
}
