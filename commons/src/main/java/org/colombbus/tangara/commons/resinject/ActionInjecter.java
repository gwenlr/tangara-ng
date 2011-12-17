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

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;
import org.apache.commons.lang3.Validate;

import static org.colombbus.tangara.commons.resinject.ResourceConstant.*;

/**
 * Inject resources into an {@link Action} instance.
 * <p>
 * Usage:
 * 
 * <pre>
 * ActionInjecter injecter = new ActionInjecter();
 * injecter.setClassResource(...);
 * injecter.setActionKey(...);
 * injecter.setAction(...);
 * injecter.inject()};
 * </pre>
 * 
 * </p>
 */
class ActionInjecter {

	private ClassResource classResource;
	private Action action;
	private String actionKey;

	/**
     *
     */
	public ActionInjecter() {
	}

	void setClassResource(ClassResource classResource) {
		Validate.notNull(classResource, "classResource argument is null"); //$NON-NLS-1$
		this.classResource = classResource;
	}

	void setAction(Action action) {
		Validate.notNull(action, "action argument is null"); //$NON-NLS-1$
		this.action = action;
	}

	void setActionKey(String actionKey) {
		Validate.notEmpty(actionKey, "actionName argument is null or empty"); //$NON-NLS-1$
		this.actionKey = actionKey;
	}

	void inject() {
		injectName();
		injectShortDescription();
		injectLongDescription();
		injectSmallIcon();
		injectLargeIcon();
		injectMnemonicKey();
		injectAcceleratorKey();
	}

	private void injectName() {
		String nameKey = actionKey + NAME_SUFFIX;
		if (classResource.containsKey(nameKey)) {
			String nameValue = classResource.getString(nameKey);
			action.putValue(Action.NAME, nameValue);
		}
	}

	private void injectShortDescription() {
		String shortDescKey = actionKey + SHORT_DESCRIPTION_SUFFIX;
		if (classResource.containsKey(shortDescKey)) {
			String shortDescValue = classResource.getString(shortDescKey);
			action.putValue(Action.SHORT_DESCRIPTION, shortDescValue);
		}
	}

	private void injectLongDescription() {
		String longDescKey = actionKey + LONG_DESCRIPTION_SUFFIX;
		if (classResource.containsKey(longDescKey)) {
			String longDescValue = classResource.getString(longDescKey);
			action.putValue(Action.LONG_DESCRIPTION, longDescValue);
		}
	}

	private void injectSmallIcon() {
		String smallIconKey = actionKey + SMALL_ICON_SUFFIX;
		if (classResource.containsKey(smallIconKey)) {
			Icon smallIconValue = classResource.getImageIcon(smallIconKey);
			action.putValue(Action.SMALL_ICON, smallIconValue);
		}
	}

	private void injectLargeIcon() {
		String largeIconKey = actionKey + LARGE_ICON_SUFFIX;
		if (classResource.containsKey(largeIconKey)) {
			Icon largeIconValue = classResource.getImageIcon(largeIconKey);
			action.putValue(Action.LARGE_ICON_KEY, largeIconValue);
		}
	}

	private void injectMnemonicKey() {
		String mnemonicKey = actionKey + MNEMONIC_KEY_SUFFIX;
		if (classResource.containsKey(mnemonicKey)) {
			KeyStroke mnemonicKeyStroke = classResource.getKeyStroke(mnemonicKey);
			int keyEventValue = mnemonicKeyStroke.getKeyCode();
			action.putValue(Action.MNEMONIC_KEY, new Integer(keyEventValue));
		}
	}

	private void injectAcceleratorKey() {
		String acceleratorKey = actionKey + ACCELERATOR_KEY_SUFFIX;
		if (classResource.containsKey(acceleratorKey)) {
			KeyStroke acceleratorKeyStroke = classResource.getKeyStroke(acceleratorKey);
			action.putValue(Action.ACCELERATOR_KEY, acceleratorKeyStroke);
		}
	}

}
