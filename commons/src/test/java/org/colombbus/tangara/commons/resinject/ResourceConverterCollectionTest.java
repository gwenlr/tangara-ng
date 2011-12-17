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

import javax.swing.ImageIcon;

import org.colombbus.tangara.commons.resinject.ResourceConverter;
import org.colombbus.tangara.commons.resinject.ResourceConverterCollection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
@SuppressWarnings({ "nls" })
public class ResourceConverterCollectionTest {

	private ResourceConverter format1Converter = new SampleFormat1Converter();
	private ResourceConverter format2Converter = new SampleFormat2Converter();

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
		ResourceConverterCollection.unregister(Sample.class, format1Converter);
		ResourceConverterCollection.unregister(Sample.class, format2Converter);
	}

	/**
	 * Test method for
	 * {@link org.colombbus.tangara.commons.resinject.ResourceConverterCollection#register(java.lang.Class, org.colombbus.tangara.commons.resinject.ResourceConverter)}
	 * .
	 */
	@Test
	public void testRegister() {
		ResourceConverterCollection.register(Sample.class, format1Converter);
	}

	@Test(expected = NullPointerException.class)
	public void testRegister_nullType() {
		ResourceConverterCollection.register(null, format1Converter);
	}

	@Test(expected = NullPointerException.class)
	public void testRegister_nullConverter() {
		ResourceConverterCollection.register(Sample.class, null);
	}

	/**
	 * Test method for
	 * {@link org.colombbus.tangara.commons.resinject.ResourceConverterCollection#findConverter(java.lang.Class)}
	 * .
	 */
	@Test
	public void testFindConverter() {
		ResourceConverterCollection.register(Sample.class, format1Converter);
		ResourceConverterCollection.register(Sample.class, format2Converter);
		ResourceConverter converter = ResourceConverterCollection.findConverter(Sample.class);
		Sample sampleValue = converter.convert("format1.yyy", SampleA.class);
		assertNotNull(sampleValue);
		assertEquals("format1", sampleValue.getFormat());

		Sample sample2Value = converter.convert("format2.xxx", SampleA.class);
		assertNotNull(sample2Value);
		assertEquals("format2", sample2Value.getFormat());
	}

	@Test(expected = NullPointerException.class)
	public void testFindConverter_null() {
		ResourceConverterCollection.findConverter(null);
	}

	@Test
	public void testFindConverter_noConverter() {
		ResourceConverter converter = ResourceConverterCollection.findConverter(Sample.class);
		assertNull(converter);
	}

	@Test
	public void testDefaultConverter_Short() {
		ResourceConverter converter = ResourceConverterCollection.findConverter(Short.class);
		assertNotNull(converter);
		Object expected = Short.valueOf((short) 13);
		Object value = converter.convert("13", SampleA.class);
		assertEquals(expected, value);
	}

	@Test
	public void testDefaultConverter_Integer() {
		ResourceConverter converter = ResourceConverterCollection.findConverter(Integer.class);
		assertNotNull(converter);
		Object value = converter.convert("16", SampleA.class);
		Object expected = Integer.valueOf(16);
		assertEquals(expected, value);
	}

	@Test
	public void testDefaultConverter_Long() {
		ResourceConverter converter = ResourceConverterCollection.findConverter(Long.class);
		assertNotNull(converter);
		Object expected = Long.valueOf(20l);
		Object value = converter.convert("20", SampleA.class);
		assertEquals(expected, value);
	}

	@Test
	public void testDefaultConverter_Float() {
		ResourceConverter converter = ResourceConverterCollection.findConverter(Float.class);
		assertNotNull(converter);
		Object expected = Float.valueOf(13.67f);
		Object value = converter.convert("13.67", SampleA.class);
		assertEquals(expected, value);
	}

	@Test
	public void testDefaultConverter_Double() {
		ResourceConverter converter = ResourceConverterCollection.findConverter(Double.class);
		assertNotNull(converter);
		Double expected = Double.valueOf(53.45);
		Double value = converter.convert("53.45", SampleA.class);
		assertEquals(expected.doubleValue(), value.doubleValue(), 0.01);
	}

	@Test
	public void testDefaultConverter_ImageIcon() {
		ResourceConverter converter = ResourceConverterCollection.findConverter(ImageIcon.class);
		assertNotNull(converter);
		ImageIcon value = converter.convert("stuff.png", SampleA.class);
		assertNotNull(value);
	}

}
