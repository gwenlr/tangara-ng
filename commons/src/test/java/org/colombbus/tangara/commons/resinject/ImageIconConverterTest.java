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

import java.net.URL;

import javax.swing.ImageIcon;

import org.colombbus.tangara.commons.resinject.ImageIconConverter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
@SuppressWarnings("nls")
public class ImageIconConverterTest {

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

	@Test
	public void testConvert() {
		URL url1 = getClass().getResource("resources/stuff.png");
		assertNotNull(url1);
		URL url2 = getClass().getResource("/org/colombbus/tangara/commons/resinject/resources/stuff.png");
		assertNotNull(url2);
		URL url3 = getClass().getClassLoader().getResource("org/colombbus/tangara/commons/resinject/resources/stuff.png");
		assertNotNull(url3);

		ImageIconConverter converter = new ImageIconConverter();
		ImageIcon imageIcon = converter.convert("stuff.png", getClass());
		assertNotNull(imageIcon);
	}

}
