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

import java.net.URL;

import javax.swing.ImageIcon;

/**
 *
 */
class ImageIconConverter implements ResourceConverter {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(String resourceValue, Class<?> resourceClass) {
		String urlPath = null;
		if (resourceValue.startsWith("/")) //$NON-NLS-1$
			urlPath = resourceValue;
		else {
			String resourcePackage = ResourceHelper.buildResourcePackageName(resourceClass.getPackage());
			String packagePath = resourcePackage.replace('.', '/');
			StringBuilder resourcePath = new StringBuilder();
			resourcePath.append('/');
			resourcePath.append(packagePath);
			resourcePath.append('/');
			resourcePath.append(resourceValue);
			urlPath = resourcePath.toString();
		}

		URL url = resourceClass.getResource(urlPath);
		ImageIcon typedValue = new ImageIcon(url);
		return (T) typedValue;
	}
}
