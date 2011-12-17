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
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

/**
 *
 */
public interface ClassResource {

	boolean containsKey(String key);

	Boolean getBoolean(String key);

	Short getShort(String key);

	Integer getInteger(String key);

	Long getLong(String key);

	Float getFloat(String key);

	Double getDouble(String key);

	Font getFont(String resource);

	ImageIcon getImageIcon(String key);

	String getString(String key);

	String getFormattedString(String key, Object... args);

	String getI18NString(String key, Object... args);

	Color getColor(String key);

	Image getImage(String key);

	KeyStroke getKeyStroke(String key);

	URL getResourceURL(String key);

	Cursor getCursor(String key);

	void inject(JLabel label);

	void inject(AbstractButton button);

	void inject(JMenu menu);

	void inject(JComponent component);

	// void injectAction(Action action, String actionKey);
	// Action getAction(String command);
	// void injectComponents(Component root);
	// AttributeSet getAttributeSet(String resource);
}
