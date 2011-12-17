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

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import org.colombbus.tangara.commons.mvc.Command;
import org.colombbus.tangara.commons.mvc.CommandFactory;
import org.colombbus.tangara.commons.resinject.ObjectResourceImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings({"nls","static-method"})
public class ObjectResourceImplTest {

	private ObjectResourceImpl objResource;
	private SampleA objectInstance;

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
		CommandFactory.createCommand("doAction1");
		objectInstance = new SampleA();
		objResource = new ObjectResourceImpl();
		objResource.setObjectInstance(objectInstance);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		CommandFactory.clearAllCommands();
	}

	@Test
	public final void testSetObjectInstance() {
		ObjectResourceImpl objectResource = new ObjectResourceImpl();
		objectResource.setObjectInstance(objectInstance);
	}

	@Test(expected = NullPointerException.class)
	public final void testSetObjectInstance_nullArg() {
		ObjectResourceImpl objectResource = new ObjectResourceImpl();
		objectResource.setObjectInstance(null);
	}

	@Test
	public final void testContainsKey() {
		assertTrue(objResource.containsKey("text1"));
		assertFalse(objResource.containsKey("unknown"));
	}

	@Test
	public final void testGetBoolean() {
		Boolean value = objResource.getBoolean("trueKey");
		assertEquals(Boolean.TRUE, value);
		Boolean value2 = objResource.getBoolean("falseKey");
		assertEquals(Boolean.FALSE, value2);
	}

	@Test
	public final void testGetColor() {
		Color value = objResource.getColor("colorKey1");
		assertNotNull(value);
		assertEquals(3, value.getRed());
		assertEquals(5 * 16 + 10, value.getGreen());
		assertEquals(14 * 16 + 5, value.getBlue());
	}

	@Test
	public final void testGetDouble() {
		Double value = objResource.getDouble("doubleKey");
		Double expected = new Double(15.678);
		assertEquals(expected.doubleValue(), value.doubleValue(), 0.001);
	}

	@Test
	public final void testGetFloat() {
		Float value = objResource.getFloat("floatKey");
		assertEquals(new Float(15.67f), value);
	}

	@Test
	public final void testGetFont() {
		Font value = objResource.getFont("fontKey");
		assertNotNull(value);
	}

	@Test
	public final void testGetFormattedString() {
		String value = objResource.getFormattedString("helloFormat", "Kitty");
		assertEquals("Hello Kitty", value);
	}

	@Test
	public final void testGetI18NString() {
		String value = objResource.getI18NString("helloI18N", "Kitty", "John");
		assertEquals("Hello Kitty, my name is John", value);
	}

	@Test
	public final void testGetImage() {
		Image value = objResource.getImage("imageKey");
		assertNotNull(value);
	}

	@Test
	public final void testGetImageIcon() {
		ImageIcon value = objResource.getImageIcon("iconKey");
		assertNotNull(value);
	}

	@Test
	public final void testGetInteger() {
		Integer value = objResource.getInteger("integerKey");
		assertEquals(new Integer(156), value);
	}

	@Test
	public final void testGetKeyStroke() {
		KeyStroke value = objResource.getKeyStroke("keyStrokeKey");
		assertEquals(KeyStroke.getKeyStroke("alt shift A"), value);
	}

	@Test
	public final void testGetLong() {
		Long value = objResource.getLong("longKey");
		assertEquals(new Long(1567), value);
	}

	@Test
	public final void testGetShort() {
		Short value = objResource.getShort("shortKey");
		assertEquals(new Short((short) 13), value);
	}

	@Test
	public final void testGetString() {
		String value = objResource.getString("text1");
		assertEquals("this is sample A", value);
	}

	@Test
	public final void testGetAction() {
		Command doCommand1 = CommandFactory.getCommand("doAction1");
		Action action = objResource.getAction(doCommand1);
		assertNotNull(action);

		Object nameValue = action.getValue(Action.NAME);
		assertEquals("Name1", nameValue);

		Object shortDescValue = action.getValue(Action.SHORT_DESCRIPTION);
		assertEquals("Short description of action1", shortDescValue);

		Object longDescValue = action.getValue(Action.LONG_DESCRIPTION);
		assertEquals("A very long description of action1", longDescValue);

		Object smallIconValue = action.getValue(Action.SMALL_ICON);
		assertNotNull(smallIconValue);
		assertTrue(Icon.class.isAssignableFrom(smallIconValue.getClass()));

		Object largeIconValue = action.getValue(Action.LARGE_ICON_KEY);
		assertNotNull(largeIconValue);
		assertTrue(Icon.class.isAssignableFrom(largeIconValue.getClass()));

		Object mnemonicValue = action.getValue(Action.MNEMONIC_KEY);
		assertEquals(new Integer(KeyEvent.VK_A), mnemonicValue);

		Object keystrokeValue = action.getValue(Action.ACCELERATOR_KEY);
		assertEquals(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK), keystrokeValue);
	}

}
