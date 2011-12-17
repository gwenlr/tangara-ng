/**
 * Tangara is an educational platform to get started with programming. Copyright
 * (C) 2009 Colombbus (http://www.colombbus.org)
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

/**
 * Thrown by resource loading methods to indicate that the resource format is
 * not the expected format.
 */
@SuppressWarnings("serial")
public class ResourceFormatException extends RuntimeException {

	private String resourceKey;

	/**
	 * @param resourceKey
	 *            the key of the resource with an unexpected format
	 * 
	 */
	public ResourceFormatException(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	/**
	 * @param resourceKey
	 *            the key of the resource with an unexpected format
	 * @param message
	 */
	public ResourceFormatException(String resourceKey, String message) {
		super(message);
		this.resourceKey = resourceKey;
	}

	/**
	 * @param resourceKey
	 *            the key of the resource with an unexpected format
	 * @param cause
	 */
	public ResourceFormatException(String resourceKey, Throwable cause) {
		super(cause);
		this.resourceKey = resourceKey;
	}

	/**
	 * @param resourceKey
	 *            the key of the resource with an unexpected format
	 * @param message
	 * @param cause
	 */
	public ResourceFormatException(String resourceKey, String message, Throwable cause) {
		super(message, cause);
		this.resourceKey = resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	/**
	 * Get the key of the resource with an unexpected format
	 * 
	 * @return the resource key at the origin of the problem
	 */
	public String getResourceKey() {
		return resourceKey;
	}

}
