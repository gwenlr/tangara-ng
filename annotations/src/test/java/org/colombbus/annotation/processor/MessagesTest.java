package org.colombbus.annotation.processor;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of class {@link Messages}
 * 
 * @version $Id: MessagesTest.java,v 1.1 2009/01/10 12:15:13 gwenael.le_roux Exp
 *          $
 * @author gwen
 */
@SuppressWarnings("nls")
public class MessagesTest {

	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
     * 
     */
	@Test
	public void testGetString() {
		assertEquals("LocalizableClass processor initialization...", Messages.getString("LocalizeProcessor.INITIALIZATION"));
	}

	/**
     * 
     */
	@Test
	public void testFormat() {
		assertEquals("Fail to load the bundle 'org.package.bundle'", Messages.format("Dictionary.BUNDLE_LOAD_FAILED", "org.package.bundle"));
		assertEquals("The bundle org.package.bundle is missing", Messages.format("LocalizeProcessor.MISSING_BUNDLE", "org.package.bundle"));
	}

}
