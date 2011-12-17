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

import static org.junit.Assert.*;

import java.util.Collection;

import org.colombbus.tangara.commons.mvc.Command;
import org.colombbus.tangara.commons.mvc.CommandAlreadyExistsException;
import org.colombbus.tangara.commons.mvc.CommandFactory;
import org.colombbus.tangara.commons.mvc.CommandNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings({"nls", "static-method" })
public class CommandFactoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		CommandFactory.clearAllCommands();
	}

	@Test
	public final void testCreateCommand() {
		Command command = CommandFactory.createCommand("id");
		assertNotNull(command);
	}

	@Test(expected = NullPointerException.class)
	public final void testCreateCommand_nullId() {
		CommandFactory.createCommand(null);
	}

	@Test(expected = CommandAlreadyExistsException.class)
	public final void testCreateCommand_twice() {
		CommandFactory.createCommand("id");
		CommandFactory.createCommand("id");
	}

	@Test
	public final void testGetCommand() {
		Command expected = CommandFactory.createCommand("id");
		Command command = CommandFactory.getCommand("id");
		assertEquals(expected, command);
	}

	@Test(expected = CommandNotFoundException.class)
	public final void testGetCommand_notExists() {
		CommandFactory.getCommand("id");
	}

	@Test
	public final void testContainsCommand() {
		CommandFactory.createCommand("id");
		assertTrue(CommandFactory.containsCommand("id"));
		assertFalse(CommandFactory.containsCommand("unknown"));
	}

	@Test
	public final void testGetAllCommands() {
		Command cmd1 = CommandFactory.createCommand("id1");
		Command cmd2 = CommandFactory.createCommand("id2");

		Collection<Command> allCommands = CommandFactory.getAllCommands();
		assertNotNull(allCommands);
		assertTrue(allCommands.size() == 2);
		assertTrue(allCommands.contains(cmd1));
		assertTrue(allCommands.contains(cmd2));
	}

	@Test
	public final void testGetAllCommands_empty() {
		Collection<Command> allCommands = CommandFactory.getAllCommands();
		assertNotNull(allCommands);
		assertTrue(allCommands.size() == 0);
	}

}
