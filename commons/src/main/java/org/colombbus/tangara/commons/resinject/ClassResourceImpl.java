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
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ClassResourceImpl implements ClassResource {

	private static final Logger LOG = LoggerFactory.getLogger(ClassResourceImpl.class);
	private ClassResource parent;
	private ResourceBundle bundle;
	private Class<?> clazz;

	public void setClass(Class<?> clazz) {
		this.clazz = clazz;
		loadBundle();
	}

	private void loadBundle() {
		String bundleName = ResourceHelper.buildBundleName(this.clazz);
		try {
			bundle = ResourceBundle.getBundle(bundleName);
		} catch (MissingResourceException ex) {
			// No bundle found, that's not a real problem
			if (LOG.isDebugEnabled()) {
				String format = "Missing resource bundle: %s"; //$NON-NLS-1$
				String message = String.format(format, bundleName);
				LOG.debug(message);
			}
		}
	}

	public void setParent(ClassResource parent) {
		this.parent = parent;
	}

	@Override
	public boolean containsKey(String key) {
		return bundle.containsKey(key);
	}

	@Override
	public String getString(String key) {
		Validate.notNull(key, "key argument is null"); //$NON-NLS-1$
		if (bundle == null)
			return missingKeyValue(key);
		if (bundle.containsKey(key))
			return bundle.getString(key);
		if (parent != null)
			return parent.getString(key);
		return missingKeyValue(key);
	}

	private static String missingKeyValue(String key) {
		return '!' + key + '!';
	}

	@Override
	public String getFormattedString(String key, Object... args) {
		String format = getString(key);
		return String.format(format, args);
	}

	@Override
	public String getI18NString(String key, Object... args) {
		String pattern = getString(key);
		return MessageFormat.format(pattern, args);
	}

	@Override
	public Boolean getBoolean(String key) {
		return getTypedValue(key, Boolean.class);
	}

	private <T> T getTypedValue(String key, Class<?> type) {
		String stringValue = getString(key);
		ResourceConverter converter = ResourceConverterCollection.findConverter(type);

		if (converter == null) {
			String format = "Converter not found for %s"; //$NON-NLS-1$
			String msg = String.format(format, type.getName());
			throw new ResourceConverterNotFoundException(msg);
		}
		@SuppressWarnings("unchecked")
		T typedValue = (T) converter.convert(stringValue, clazz);
		return typedValue;
	}

	@Override
	public Short getShort(String key) {
		Short typedValue = getTypedValue(key, Short.class);
		return typedValue;
	}

	@Override
	public Integer getInteger(String key) {
		Integer typedValue = getTypedValue(key, Integer.class);
		return typedValue;
	}

	@Override
	public Long getLong(String key) {
		Long typedValue = getTypedValue(key, Long.class);
		return typedValue;
	}

	@Override
	public Float getFloat(String key) {
		Float typedValue = getTypedValue(key, Float.class);
		return typedValue;
	}

	@Override
	public Double getDouble(String key) {
		Double typedValue = getTypedValue(key, Double.class);
		return typedValue;
	}

	@Override
	public Font getFont(String key) {
		Font typedValue = getTypedValue(key, Font.class);
		return typedValue;
	}

	@Override
	public ImageIcon getImageIcon(String key) {
		ImageIcon typedValue = getTypedValue(key, ImageIcon.class);
		return typedValue;
	}

	@Override
	public Color getColor(String key) {
		Color typedValue = getTypedValue(key, Color.class);
		return typedValue;
	}

	@Override
	public Image getImage(String key) {
		Image typedValue = getTypedValue(key, Image.class);
		return typedValue;
	}

	@Override
	public KeyStroke getKeyStroke(String key) {
		KeyStroke typedValue = getTypedValue(key, KeyStroke.class);
		return typedValue;
	}

	@Override
	public URL getResourceURL(String key) {
		String value = getString(key);
		URL typedValue = clazz.getResource(value);
		return typedValue;
	}

	@Override
	public Cursor getCursor(String key) {
		try {
			return loadCursor(key);
		} catch (Exception ex) {
			String message = String.format("Fail to load cursor from resource %s", key); //$NON-NLS-1$
			throw new InvalidResourceException(message, ex);
		}
	}

	private Cursor loadCursor(String key) {
		String imageKey = key + ".image"; //$NON-NLS-1$
		String nameKey = key + ".name"; //$NON-NLS-1$
		String xKey = key + ".hotspot.x"; //$NON-NLS-1$
		String yKey = key + ".hotspot.y"; //$NON-NLS-1$

		Image image = getImage(imageKey);
		String name = getString(nameKey);
		Integer hotSpotX = getInteger(xKey);
		Integer hotSpotY = getInteger(yKey);
		Point hotSpot = new Point(hotSpotX.intValue(), hotSpotY.intValue());

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		return toolkit.createCustomCursor(image, hotSpot, name);
	}

	@Override
	public void inject(JLabel label) {
		String key = label.getName();
		Validate.notEmpty(key, "label argument has no name"); //$NON-NLS-1$

		JLabelInjecter injecter = new JLabelInjecter();

		injecter.setClassResource(this);
		injecter.setKey(key);
		injecter.setLabel(label);

		injecter.inject();
	}

	@Override
	public void inject(AbstractButton button) {
		String key = button.getName();
		Validate.notEmpty(key, "button argument has no name"); //$NON-NLS-1$

		AbstractButtonInjecter injecter = new AbstractButtonInjecter();

		injecter.setClassResource(this);
		injecter.setKey(key);
		injecter.setButton(button);

		injecter.inject();
	}

	@Override
	public void inject(JMenu menu) {
		String key = menu.getName();
		Validate.notEmpty(key, "menu argument has no name"); //$NON-NLS-1$

		JMenuInjecter injecter = new JMenuInjecter();

		injecter.setClassResource(this);
		injecter.setKey(key);
		injecter.setMenu(menu);

		injecter.inject();
	}

	@Override
	public void inject(JComponent component) {
		Validate.notNull(component, "component argument is null"); //$NON-NLS-1$

		String key = component.getName();
		Validate.notEmpty(key, "component argument has no name"); //$NON-NLS-1$

		JComponentInjecter injecter = new JComponentInjecter();
		injecter.setClassResource(this);
		injecter.setKey(key);
		injecter.setComponent(component);
		injecter.inject();
	}

}
