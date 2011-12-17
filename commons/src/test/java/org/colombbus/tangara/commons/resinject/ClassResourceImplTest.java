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
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import org.colombbus.tangara.commons.resinject.ClassResource;
import org.colombbus.tangara.commons.resinject.InvalidResourceException;
import org.colombbus.tangara.commons.resinject.ResourceFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("nls")
public class ClassResourceImplTest {

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = NullPointerException.class)
	public void testGetString_nullKey() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		res.getString(null);
	}

	@Test
	public void testGetString_missingKey() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		String value = res.getString("missing");
		assertEquals("!missing!", value);
	}

	@Test
	public void testGetString() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		String value = res.getString("text1");
		assertEquals("this is sample A", value);
	}

	@Test
	public void testGetString_keyInParent() {
		ClassResource res = ResourceFactory.getClassResource(SampleChild.class);
		String value = res.getString("textInParent");
		assertEquals("text in parent", value);
	}

	@Test
	public void testGetFormattedString() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		String value = res.getFormattedString("helloFormat", "Kitty");
		assertEquals("Hello Kitty", value);
	}

	@Test(expected = NullPointerException.class)
	public void testGetFormattedString_nullKey() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		res.getFormattedString(null, "Kitty");
	}

	@Test
	public void testGetFormattedString_missingKey() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		String value = res.getFormattedString("missing", "Kitty");
		assertEquals("!missing!", value);
	}

	@Test
	public void testGetI18NString() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		String value = res.getI18NString("helloI18N", "Kitty", "John");
		assertEquals("Hello Kitty, my name is John", value);
	}

	@Test(expected = NullPointerException.class)
	public void testGetI18NString_nullKey() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		res.getI18NString(null, "Kitty", "John");
	}

	@Test
	public void testGetI18NString_missingKey() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		String value = res.getI18NString("missing", "Kitty", "John");
		assertEquals("!missing!", value);
	}

	@Test
	public void testGetBoolean() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Boolean value = res.getBoolean("trueKey");
		assertEquals(Boolean.TRUE, value);
		Boolean value2 = res.getBoolean("falseKey");
		assertEquals(Boolean.FALSE, value2);
	}

	@Test
	public void testGetShort() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Short value = res.getShort("shortKey");
		assertEquals(new Short((short) 13), value);
	}

	@Test
	public void testGetInteger() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Integer value = res.getInteger("integerKey");
		assertEquals(new Integer(156), value);
	}

	@Test
	public void testGetLong() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Long value = res.getLong("longKey");
		assertEquals(new Long(1567), value);
	}

	@Test
	public void testGetFloat() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Float value = res.getFloat("floatKey");
		assertEquals(new Float(15.67f), value);
	}

	@Test
	public void testGetDouble() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Double value = res.getDouble("doubleKey");
		Double expected = new Double(15.678);
		assertEquals(expected.doubleValue(), value.doubleValue(), 0.001);
	}

	@Test
	public void testGetFont() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Font value = res.getFont("fontKey");
		assertNotNull(value);
	}

	@Test
	public void testGetColor() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Color value = res.getColor("colorKey1");
		assertNotNull(value);
		assertEquals(3, value.getRed());
		assertEquals(5 * 16 + 10, value.getGreen());
		assertEquals(14 * 16 + 5, value.getBlue());
	}

	@Test
	public void testGetImage() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Image value = res.getImage("imageKey");
		assertNotNull(value);
	}

	@Test
	public void testGetImageIcon() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		ImageIcon value = res.getImageIcon("iconKey");
		assertNotNull(value);
	}

	@Test
	public void testGetKeyStroke() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		KeyStroke value = res.getKeyStroke("keyStrokeKey");
		assertEquals(KeyStroke.getKeyStroke("alt shift A"), value);
	}

	@Test
	public void testKeyStrokeSingle() {
		KeyStroke ks = KeyStroke.getKeyStroke("B");
		assertNotNull(ks);
		assertTrue(KeyEvent.VK_B == ks.getKeyCode());
	}

	@Test
	public void testKeyStrokeMultiple() {
		KeyStroke ks = KeyStroke.getKeyStroke("shift R");
		assertNotNull(ks);
		assertTrue(KeyEvent.VK_R == ks.getKeyCode());
		assertTrue((InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK) == ks.getModifiers());
	}

	@Test
	public void testGetResourceURL() throws MalformedURLException {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		URL value = res.getResourceURL("resourceURLKey");
		assertNotNull(value);

		URL baseURL = getClass().getResource(".");
		String baseURLPath = baseURL.toExternalForm();
		String expectedURLPath = baseURLPath + "resources/action1-small.png";
		URL expected = new URL(expectedURLPath);
		assertEquals(expected, value);
	}

	@Test
	public void testGetResourceURL_invalid() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		URL value = res.getResourceURL("resourceURLKey2");
		assertNull(value);
	}

	@Test
	public void testGetCursor() throws Exception {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		Cursor cursor = res.getCursor("cursorKey");
		assertNotNull(cursor);
		assertEquals("red-cross", cursor.getName());
	}

	@Test
	public void testGetCursor_badKey() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		try {
			res.getCursor("invalidCursorKey");
			fail();
		} catch (InvalidResourceException ex) {
		}
	}

	@Test
	public void testInjectJLabel() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		JLabel label = new JLabel();
		label.setName("label1");
		res.inject(label);

		assertEquals("Text of label1", label.getText());
		assertEquals("Tool tip of label1", label.getToolTipText());
		assertNotNull(label.getIcon());
	}

	@Test(expected = NullPointerException.class)
	public void testInjectJLabel_noName() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		JLabel label = new JLabel();
		res.inject(label);
	}

	@Test
	public void testInjectJMenu() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		JMenu menu = new JMenu();
		menu.setName("menu1");
		res.inject(menu);

		assertEquals("Menu1", menu.getText());
		assertEquals("Tool tip of menu1", menu.getToolTipText());
		assertNotNull(menu.getIcon());

		assertEquals(Font.decode("Arial-bold-20"), menu.getFont());
		assertEquals(Color.GREEN, menu.getForeground());
		assertEquals(Color.BLUE, menu.getBackground());

		assertEquals(KeyEvent.VK_M, menu.getMnemonic());
	}

	@Test(expected = NullPointerException.class)
	public void testInjectJMenu_noName() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		JMenu menu = new JMenu();
		res.inject(menu);
	}

	@SuppressWarnings("serial")
	@Test
	public void testInjectJComponent() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		JComponent component = new JComponent() {
		};
		component.setName("component1");
		res.inject(component);

		assertEquals("Tool tip of menu1", component.getToolTipText());
		assertEquals(Font.decode("Arial-bold-20"), component.getFont());
		assertEquals(Color.GREEN, component.getForeground());
		assertEquals(Color.BLUE, component.getBackground());
	}

	@SuppressWarnings("serial")
	@Test(expected = NullPointerException.class)
	public void testInjectJComponent_noName() {
		ClassResource res = ResourceFactory.getClassResource(SampleA.class);
		JComponent component = new JComponent() {
		};
		res.inject(component);
	}

}
