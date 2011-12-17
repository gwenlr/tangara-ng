/**
 * "Sauter sur Kolwezi" program is an implementation of the wargame published in
 * the Vae Victis review n°80 (available at
 * http://vaevictis.histoireetcollections.com/publication-2095-.html) Copyright
 * (C) 2010 Gwen
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

import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Action;

public interface ActionManager {

	List<Action> getActions(Command... displayCommands);

	Action getAction(Command displayCommand);

	void injectAction(AbstractButton button, Command command);

}