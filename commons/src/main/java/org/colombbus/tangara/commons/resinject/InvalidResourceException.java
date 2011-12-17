/**
 * Tangara is an educational platform to get started with programming. Copyright
 * (C) 2010 Colombbus (http://www.colombbus.org)
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
package org.colombbus.tangara.commons.resinject;

@SuppressWarnings("serial")
public class InvalidResourceException extends RuntimeException {

	public InvalidResourceException() {
	}

	public InvalidResourceException(String message) {
		super(message);
	}

	public InvalidResourceException(Throwable cause) {
		super(cause);
	}

	public InvalidResourceException(String message, Throwable cause) {
		super(message, cause);
	}

}
