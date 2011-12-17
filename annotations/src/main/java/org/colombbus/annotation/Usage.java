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
 * Interface for script method providing usage help information
 * 
 * @version $Id: Usage.java,v 1.3 2009/01/10 09:35:43 gwenael.le_roux Exp $
 * @author Benoit
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Usage {

	/**
	 * A method call parameters sample.
	 * <p>
	 * For instance
	 * <code>@Usage("\"bob\", \"bob's address\"") void setAddress(String person, String address)</code>
	 * 
	 * @return parameter usage sample, or an empty string de default
	 */
	String value() default "";

}
