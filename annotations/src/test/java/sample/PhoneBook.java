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
package sample;

import org.colombbus.annotation.LocalizableClass;
import org.colombbus.annotation.LocalizableMethod;

/**
 *
 */
@LocalizableClass(value = "PhoneBook.PhoneBook", localize = true, inherit = true)
@SuppressWarnings("nls")
public class PhoneBook extends ReadOnlyPhoneBook {

	/**
	 *
	 */
	@LocalizableMethod("PhoneBook.PhoneBook")
	public PhoneBook() {
	}

	/**
	 * @param personName
	 * @param phoneNumber
	 */
	@LocalizableMethod("PhoneBook.add")
	public void add(String personName, String phoneNumber) {
		System.out.println("Add personn " + personName + " with number " + phoneNumber);
	}

	/**
	 * @param personName
	 */
	@LocalizableMethod("PhoneBook.remove")
	public void remove(String personName) {
		System.out.println("Remove personn " + personName);
	}

}
