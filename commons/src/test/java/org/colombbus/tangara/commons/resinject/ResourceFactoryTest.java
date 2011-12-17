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

import org.colombbus.tangara.commons.mvc.CommandFactory;
import org.colombbus.tangara.commons.resinject.ClassResource;
import org.colombbus.tangara.commons.resinject.ObjectResource;
import org.colombbus.tangara.commons.resinject.ResourceFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("nls")
public class ResourceFactoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		CommandFactory.createCommand("doAction1");
	}

	@After
	public void tearDown() throws Exception {
		CommandFactory.clearAllCommands();
	}

	@Test(expected = NullPointerException.class)
	public void testGetClassResource_nullArg() {
		ResourceFactory.getClassResource(null);
	}

	@Test
	public void testGetClassResource() {
		ClassResource res = ResourceFactory.getClassResource(getClass());
		assertNotNull(res);
	}

	@Test
	public void testGetClassResource_sameInstanceForTwice() {
		ClassResource res1 = ResourceFactory.getClassResource(SampleA.class);
		ClassResource res2 = ResourceFactory.getClassResource(SampleA.class);

		assertSame(res1, res2);
	}

	@Test
	public void testGetClassResource_inheritanceChild() {
		ClassResource res = ResourceFactory.getClassResource(SampleChild.class);
		assertNotNull(res);
	}

	@Test
	public void testGetClassResource_inheritanceParent() {
		ClassResource res = ResourceFactory.getClassResource(SampleParent.class);
		assertNotNull(res);
	}

	@Test
	public void testGetClassResource_noResources() {
		ClassResource res = ResourceFactory.getClassResource(SampleEmptyChild.class);
		assertNotNull(res);
	}

	@Test
	public void testGetObjectResource() {
		ObjectResource res = ResourceFactory.getObjectResource(new SampleA());
		assertNotNull(res);
	}

	@Test(expected = NullPointerException.class)
	public void testGetObjectResource_nullArg() {
		ResourceFactory.getObjectResource(null);
	}
}
