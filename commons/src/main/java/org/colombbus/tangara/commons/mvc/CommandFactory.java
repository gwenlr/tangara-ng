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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

public class CommandFactory {

	private static final Map<String, Command> idToCommand = new HashMap<String, Command>();

	private CommandFactory() {
	}

	/**
	 * Create a new display command
	 * 
	 * @param commandId
	 *            a non <code>null</code> and unique command identifier
	 * @return a non <code>null</code> command
	 * @throws IllegalArgumentException
	 * @throws CommandAlreadyExistsException
	 */
	public static Command createCommand(String commandId) throws IllegalArgumentException, CommandAlreadyExistsException {
		Validate.notNull(commandId, "commandId argument is null or empty"); //$NON-NLS-1$
		if (idToCommand.containsKey(commandId))
			throw new CommandAlreadyExistsException();

		Command command = new CommandImpl(commandId);
		idToCommand.put(commandId, command);
		return command;
	}

	/**
	 * Get a command from its identifier
	 * 
	 * @param commandId
	 *            a command unique identifier
	 * @return a non <code>null</code> command
	 * @throws CommandNotFoundException
	 *             if no command is associated to the identifier
	 */
	public static Command getCommand(String commandId) throws CommandNotFoundException {
		if (idToCommand.containsKey(commandId) == false)
			throw new CommandNotFoundException("Cannot found command " + commandId); //$NON-NLS-1$
		return idToCommand.get(commandId);
	}

	/**
	 * Check if a command identifier is registered
	 * 
	 * @param commandId
	 *            a unique command identifier
	 * @return <code>true</code> if a command is associated to an identifier,
	 *         <code>false</code> if no such command exists
	 */
	public static boolean containsCommand(String commandId) {
		return idToCommand.containsKey(commandId);
	}

	/**
	 * Get all registered commands
	 * 
	 * @return a non <code>null</code> collections of commands
	 */
	public static Collection<Command> getAllCommands() {
		return idToCommand.values();
	}

	/**
	 * Remove all registered commands
	 */
	public static void clearAllCommands() {
		idToCommand.clear();
	}

}
