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

/**
 *
 */
class ResourceHelper {

	/**
    *
    */
	static final String RESOURCES_PACKAGE = "resources"; //$NON-NLS-1$

	/**
	 * Create the bundle name from a class name
	 * 
	 * <pre>
	 * Examples:
	 *  The bundle of class x.y.Z class is x.y.resources.Z
	 *  The bundle of class Z is resources.Z
	 * </pre>
	 */
	static String buildBundleName(Class<?> clazz) {
		StringBuilder bundleName = new StringBuilder();

		String resourcePackage = buildResourcePackageName(clazz.getPackage());
		bundleName.append(resourcePackage);

		bundleName.append('.');

		String className = clazz.getSimpleName();
		bundleName.append(className);

		return bundleName.toString();
	}

	static String buildResourcePackageName(Package pkg) {
		String packageName = pkg.getName();
		int lastDotPos = packageName.lastIndexOf('.');
		StringBuilder resourcePackageName = new StringBuilder();
		if (lastDotPos != -1) {
			resourcePackageName.append(packageName);
			resourcePackageName.append('.');
			resourcePackageName.append(RESOURCES_PACKAGE);
		} else {
			resourcePackageName.append(RESOURCES_PACKAGE);
		}
		return resourcePackageName.toString();
	}

}
