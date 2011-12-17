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

import javax.swing.JComponent;
import org.apache.commons.lang3.Validate;

/**
 * Inject properties into a {@link JComponent}
 * 
 * <pre>
 * Use following suffixes :
 *  - {@link ResourceConstant#TOOL_TIP_SUFFIX}
 *  - {@link ResourceConstant#FOREGROUND_SUFFIX}
 *  - {@link ResourceConstant#BACKGROUND_SUFFIX}
 *  - {@link ResourceConstant#FONT}
 *  - {@link ResourceConstant#TOOL_TIP_SUFFIX}
 *  - {@link ResourceConstant#TOOL_TIP_SUFFIX}
 *  - {@link ResourceConstant#TOOL_TIP_SUFFIX}
 *  - {@link ResourceConstant#TOOL_TIP_SUFFIX}
 * </pre>
 * 
 * @author gwen
 */
class JComponentInjecter {

	private ClassResource classResource;
	private String key;
	private JComponent component;

	public JComponentInjecter() {
	}

	public void setClassResource(ClassResource classResource) {
		Validate.notNull(classResource, "classResource argument is null"); //$NON-NLS-1$
		this.classResource = classResource;
	}

	public void setKey(String key) {
		Validate.notEmpty(key, "key argument is null"); //$NON-NLS-1$
		this.key = key;
	}

	public void setComponent(JComponent component) {
		Validate.notNull(component, "component argument is null"); //$NON-NLS-1$
		this.component = component;
	}

	public void inject() {
		injectToolTip();
		injectForeground();
		injectBackground();
		injectFont();
	}

	private void injectToolTip() {
		String toolTipKey = key + TOOL_TIP_SUFFIX;
		if (classResource.containsKey(toolTipKey)) {
			String toolTipValue = classResource.getString(toolTipKey);
			component.setToolTipText(toolTipValue);
		}
	}

	private void injectForeground() {
		String foregroundKey = key + FOREGROUND_SUFFIX;
		if (classResource.containsKey(foregroundKey)) {
			Color foregroundValue = classResource.getColor(foregroundKey);
			component.setForeground(foregroundValue);
		}
	}

	private void injectBackground() {
		String backgroundKey = key + BACKGROUND_SUFFIX;
		if (classResource.containsKey(backgroundKey)) {
			Color backgroundValue = classResource.getColor(backgroundKey);
			component.setBackground(backgroundValue);
		}
	}

	private void injectFont() {
		String fontKey = key + FONT_SUFFIX;
		if (classResource.containsKey(fontKey)) {
			Font fontValue = classResource.getFont(fontKey);
			component.setFont(fontValue);
		}
	}
}
