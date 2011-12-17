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

import java.awt.event.ActionEvent;

import org.colombbus.tangara.commons.resinject.MessageSenderAction;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("nls")
public class MessageSenderActionTest {

	private MessageSenderAction msAction;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		msAction = new MessageSenderAction();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSetTarget() {
		msAction.setTarget(new Object());
	}

	@Test(expected = NullPointerException.class)
	public final void testSetTarget_nullArg() {
		msAction.setTarget(null);
	}

	@Test
	public final void testSetMessage() {
		msAction.setMessage("aMethod");
	}

	@Test(expected = NullPointerException.class)
	public final void testSetMessage_nullArg() {
		msAction.setMessage(null);
	}

	interface ActionPerformer {

		void performAction();

	}

	@Test
	public final void testActionPerformed() {
		ActionPerformer target = EasyMock.createStrictMock(ActionPerformer.class);
		target.performAction();
		EasyMock.replay(target);

		msAction.setTarget(target);
		msAction.setMessage("performAction");
		msAction.actionPerformed(new ActionEvent(this, 0, "command"));

		EasyMock.verify(target);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void testActionPerformed_invalidMethodName() {
		Object target = new Object();

		msAction.setTarget(target);
		msAction.setMessage("performAction");
		msAction.actionPerformed(new ActionEvent(this, 0, "command"));
	}

	@Test(expected = NullPointerException.class)
	public final void testActionPerformed_noMessage() {
		msAction.setTarget(new Object());
		msAction.actionPerformed(new ActionEvent(this, 0, "command"));
	}

	@Test(expected = NullPointerException.class)
	public final void testActionPerformed_noTarget() {
		msAction.setMessage("performAction");
		msAction.actionPerformed(new ActionEvent(this, 0, "command"));
	}
}
