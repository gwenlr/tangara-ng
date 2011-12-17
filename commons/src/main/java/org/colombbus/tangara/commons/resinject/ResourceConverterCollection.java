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

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import org.apache.commons.lang3.Validate;

/**
 *
 */
public final class ResourceConverterCollection {

	private static final Map<Class<?>, ResourceConverterComposite> map = new ConcurrentHashMap<Class<?>, ResourceConverterComposite>();
	static {
		register(Boolean.class, new BooleanConverter());
		register(Short.class, new ShortConverter());
		register(Integer.class, new IntegerConverter());
		register(Long.class, new LongConverter());
		register(Float.class, new FloatConverter());
		register(Double.class, new DoubleConverter());
		register(Font.class, new FontConverter());
		register(Color.class, new ColorConverter());
		register(ImageIcon.class, new ImageIconConverter());
		register(Image.class, new ImageConverter());
		register(KeyStroke.class, new KeyStrokeConverter());
	}

	private ResourceConverterCollection() {

	}

	/**
	 * Register a new converter
	 * 
	 * @param type
	 * @param converter
	 */
	public static void register(Class<?> type, ResourceConverter converter) {
		Validate.notNull(type, "type argument is null"); //$NON-NLS-1$
		Validate.notNull(converter, "converter argument is null"); //$NON-NLS-1$

		synchronized (map) {
			ResourceConverterComposite composite = map.get(type);
			if (composite == null) {
				composite = new ResourceConverterComposite();
				map.put(type, composite);
			}
			composite.add(converter);
		}
	}

	/**
	 * Get the converter of a type
	 * 
	 * @param type
	 *            a non <code>null</code> type
	 * @return the converter of the type or <code>null</code> if no converter is
	 *         associated
	 * @throws IllegalArgumentException
	 *             if the type argument is <code>null</code>
	 */
	public static ResourceConverter findConverter(Class<?> type) {
		Validate.notNull(type, "type argument is null"); //$NON-NLS-1$

		ResourceConverterComposite composite = map.get(type);
		return composite;
	}

	static void unregister(Class<?> type, ResourceConverter converter) {
		if (type == null || converter == null)
			return;
		synchronized (map) {
			ResourceConverterComposite composite = map.get(type);
			if (composite != null) {
				composite.remove(converter);
				if (composite.isEmpty()) {
					map.remove(type);
				}
			}
		}
	}

}
