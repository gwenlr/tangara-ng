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

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import org.colombbus.tangara.commons.resinject.ActionInjecter;
import org.colombbus.tangara.commons.resinject.ClassResource;
import org.colombbus.tangara.commons.resinject.ResourceFactory;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings({ "nls", "serial" })
public class ActionInjecterTest {

	private ActionInjecter injecter;
	private Action action;
	private ClassResource sampleRes;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		injecter = new ActionInjecter();
		action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		};

		sampleRes = ResourceFactory.getClassResource(SampleA.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetClassResource() {
		ClassResource cr = EasyMock.createMock(ClassResource.class);
		EasyMock.replay(cr);
		injecter.setClassResource(cr);
		EasyMock.verify(cr);
	}

	@Test(expected = NullPointerException.class)
	public void testSetClassResource_nullArg() {
		injecter.setClassResource(null);
	}

	@Test
	public void testSetAction() {
		Action localAction = EasyMock.createMock(Action.class);
		EasyMock.replay(localAction);
		injecter.setAction(localAction);
		EasyMock.verify(localAction);
	}

	@Test(expected = NullPointerException.class)
	public void testSetAction_nullArg() {
		injecter.setAction(null);
	}

	@Test
	public void testSetActionKey() {
		injecter.setActionKey("action.key");
	}

	@Test(expected = NullPointerException.class)
	public void testSetActionKey_nullArg() {
		injecter.setActionKey(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetActionKey_emptyArg() {
		injecter.setActionKey("");
	}

	@Test
	public void testInject_Name() {
		initializeInjecter();
		injecter.inject();

		Object nameValue = action.getValue(Action.NAME);
		assertEquals("Name1", nameValue);
	}

	private void initializeInjecter() {
		injecter.setClassResource(sampleRes);
		injecter.setAction(action);
		injecter.setActionKey("doAction1");
	}

	@Test
	public void testInject_shortDescription() {
		initializeInjecter();
		injecter.inject();

		Object shortDescValue = action.getValue(Action.SHORT_DESCRIPTION);
		assertEquals("Short description of action1", shortDescValue);
	}

	@Test
	public void testInject_longDescription() {
		initializeInjecter();
		injecter.inject();

		Object longDescValue = action.getValue(Action.LONG_DESCRIPTION);
		assertEquals("A very long description of action1", longDescValue);
	}

	@Test
	public void testInject_smallIcon() {
		initializeInjecter();
		injecter.inject();

		Object smallIconValue = action.getValue(Action.SMALL_ICON);
		assertNotNull(smallIconValue);
		assertTrue(Icon.class.isAssignableFrom(smallIconValue.getClass()));

	}

	@Test
	public void testInject_largeIcon() {
		initializeInjecter();
		injecter.inject();

		Object largeIconValue = action.getValue(Action.LARGE_ICON_KEY);
		assertNotNull(largeIconValue);
		assertTrue(Icon.class.isAssignableFrom(largeIconValue.getClass()));
	}

	@Test
	public void testInject_mnemonic() {
		initializeInjecter();
		injecter.inject();

		Object mnemonicValue = action.getValue(Action.MNEMONIC_KEY);
		assertEquals(new Integer(KeyEvent.VK_A), mnemonicValue);
	}

	@Test
	public void testInject_accelerator() {
		initializeInjecter();
		injecter.inject();

		Object keystrokeValue = action.getValue(Action.ACCELERATOR_KEY);
		assertEquals(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), keystrokeValue);
	}

}
