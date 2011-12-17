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

import static org.colombbus.tangara.commons.resinject.ResourceConstant.*;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import org.apache.commons.lang3.Validate;

/**
 * Inject properties into a {@link JLabel}
 * 
 * <pre>
 * Use following suffixes :
 *  - {@link ResourceConstant#TEXT_SUFFIX}
 *  - {@link ResourceConstant#ICON_SUFFIX}
 *  - {@link ResourceConstant#TOOL_TIP_SUFFIX}
 *  - {@link ResourceConstant#MNEMONIC_KEY_SUFFIX}
 *  - {@link ResourceConstant#FOREGROUND_SUFFIX}
 *  - {@link ResourceConstant#BACKGROUND_SUFFIX}
 *  - {@link ResourceConstant#FONT_SUFFIX}
 * </pre>
 * 
 * @author gwen
 */
class JMenuInjecter {

	private ClassResource classResource;
	private String key;
	private JMenu menu;

	public JMenuInjecter() {
	}

	public void setClassResource(ClassResource classResource) {
		Validate.notNull(classResource, "classResource argument is null"); //$NON-NLS-1$
		this.classResource = classResource;
	}

	public void setKey(String key) {
		Validate.notEmpty(key, "key argument is null"); //$NON-NLS-1$
		this.key = key;
	}

	public void setMenu(JMenu menu) {
		Validate.notNull(menu, "menu argument is null"); //$NON-NLS-1$
		this.menu = menu;
	}

	public void inject() {
		injectText();
		injectIcon();
		injectToolTip();
		injectMnemonicKey();
		injectForeground();
		injectBackground();
		injectFont();
	}

	private void injectText() {
		String textKey = key + TEXT_SUFFIX;
		if (classResource.containsKey(textKey)) {
			String textValue = classResource.getString(textKey);
			menu.setText(textValue);
		}
	}

	private void injectToolTip() {
		String toolTipKey = key + TOOL_TIP_SUFFIX;
		if (classResource.containsKey(toolTipKey)) {
			String toolTipValue = classResource.getString(toolTipKey);
			menu.setToolTipText(toolTipValue);
		}
	}

	private void injectIcon() {
		String smallIconKey = key + ICON_SUFFIX;
		if (classResource.containsKey(smallIconKey)) {
			Icon iconValue = classResource.getImageIcon(smallIconKey);
			menu.setIcon(iconValue);
		}
	}

	private void injectMnemonicKey() {
		String mnemonicKey = key + MNEMONIC_KEY_SUFFIX;
		if (classResource.containsKey(mnemonicKey)) {
			KeyStroke mnemonicValue = classResource.getKeyStroke(mnemonicKey);
			menu.setMnemonic(mnemonicValue.getKeyCode());
		}
	}

	private void injectForeground() {
		String foregroundKey = key + FOREGROUND_SUFFIX;
		if (classResource.containsKey(foregroundKey)) {
			Color foregroundValue = classResource.getColor(foregroundKey);
			menu.setForeground(foregroundValue);
		}
	}

	private void injectBackground() {
		String backgroundKey = key + BACKGROUND_SUFFIX;
		if (classResource.containsKey(backgroundKey)) {
			Color backgroundValue = classResource.getColor(backgroundKey);
			menu.setBackground(backgroundValue);
		}
	}

	private void injectFont() {
		String fontKey = key + FONT_SUFFIX;
		if (classResource.containsKey(fontKey)) {
			Font fontValue = classResource.getFont(fontKey);
			menu.setFont(fontValue);
		}
	}
}
