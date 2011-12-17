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

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.Validate;

/**
 * 
 * @author gwen
 */
public class ResourceFactory {

	private static ReadWriteLock lock = new ReentrantReadWriteLock();

	private static Map<Class<?>, ClassResourceImpl> classResourceMap = new Hashtable<Class<?>, ClassResourceImpl>();

	private ResourceFactory() {
	}

	/**
	 * Get the {@link ClassResource} of a specific class
	 * 
	 * @param clazz
	 *            a class name, cannot be null
	 * @return a resource helper, never null
	 * @throws IllegalArgumentException
	 *             if the clazz argument is <code>null</code>
	 */
	public static ClassResource getClassResource(Class<?> clazz) {
		Validate.notNull(clazz, "clazz argument is null"); //$NON-NLS-1$

		ClassResourceImpl resource = null;

		lock.readLock().lock();
		resource = classResourceMap.get(clazz);
		lock.readLock().unlock();
		if (resource == null) {
			resource = createResource(clazz);
		}

		return resource;
	}

	private static ClassResourceImpl createResource(Class<?> clazz) {
		ClassResourceImpl resource;
		lock.writeLock().lock();
		resource = new ClassResourceImpl();
		resource.setClass(clazz);
		if (clazz.getSuperclass().equals(Object.class) == false) {
			ClassResource parentResource = getClassResource(clazz.getSuperclass());
			resource.setParent(parentResource);
		}
		classResourceMap.put(clazz, resource);
		lock.writeLock().unlock();
		return resource;
	}

	/**
	 * Create a new instance of an object resource
	 * 
	 * @param objectInstance
	 *            an object instance, cannot be <code>null</code>
	 * @return a non <code>null</code> resource instance
	 */
	public static ObjectResource getObjectResource(Object objectInstance) {
		ObjectResourceImpl resourceImpl = new ObjectResourceImpl();
		resourceImpl.setObjectInstance(objectInstance);
		return resourceImpl;
	}

}
