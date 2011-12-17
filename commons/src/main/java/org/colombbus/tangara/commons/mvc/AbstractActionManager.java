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
package org.colombbus.tangara.commons.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Action;

import org.apache.commons.lang3.Validate;
import org.colombbus.tangara.commons.resinject.ObjectResource;
import org.colombbus.tangara.commons.resinject.ResourceFactory;

/**
 * @author gwen
 * 
 */
public abstract class AbstractActionManager implements ActionManager {

	private ObjectResource resources;

	public AbstractActionManager() {
		resources = ResourceFactory.getObjectResource(this);
	}

	@Override
	public List<Action> getActions(Command... displayCommands) {
		List<Action> actionList = new ArrayList<Action>();
		for (Command displayCommand : displayCommands) {
			Action action = getAction(displayCommand);
			actionList.add(action);
		}
		return actionList;
	}

	@Override
	public Action getAction(Command command) {
		Validate.notNull(command, "command argument is null"); //$NON-NLS-1$
		Action action = resources.getAction(command);
		return action;
	}

	@Override
	public void injectAction(AbstractButton button, Command command) {
		resources.injectAction(button, command);
	}

}
