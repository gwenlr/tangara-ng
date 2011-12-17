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

/**
 * Default implementation of {@link Command}
 * 
 * @author gwen
 */
class CommandImpl implements Command {

	private String commandId;

	public CommandImpl(String commandId) {
		this.commandId = commandId;
	}

	@Override
	public String getId() {
		return this.commandId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commandId == null) ? 0 : commandId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CommandImpl))
			return false;
		CommandImpl other = (CommandImpl) obj;
		if (commandId == null) {
			if (other.commandId != null)
				return false;
		} else if (!commandId.equals(other.commandId))
			return false;
		return true;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return String.format("CommandImpl [commandId=%s]", commandId);
	}

}
