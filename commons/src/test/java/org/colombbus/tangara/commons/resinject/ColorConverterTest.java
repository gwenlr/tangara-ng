/**
 * Tangara is an educational platform to get started with programming.
 * Copyright (C) 2010 Colombbus (http://www.colombbus.org)
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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("nls")
public class ColorConverterTest {

	private ColorConverter converter = new ColorConverter();

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
	}

	/**
	 * Test method for
	 * {@link org.colombbus.tangara.commons.resinject.ColorConverter#convert(java.lang.String, java.lang.Class)}
	 * .
	 */
	@Test
	public void testConvert_hexa() {
		Color color = converter.convert("#FF0000", Color.class);
		assertNotNull(color);
		assertEquals(Color.red, color);
	}

	@Test
	public void testConvert_deca() {
		Color color = converter.convert("" + Color.red.getRGB(), Color.class);
		assertNotNull(color);
		assertEquals(Color.red, color);
	}

}
