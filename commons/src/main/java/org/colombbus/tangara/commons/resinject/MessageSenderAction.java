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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.AbstractAction;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action which send a message (ie no argument method) to a target object when
 * its <code>actionPerformed</code> method is called.
 * <p>
 * Usage:
 * 
 * <pre>
 * Object target = ... // object receiving the message
 * String method = "doAction"; // method of target to call.
 *                             //This method has no arguments
 * 
 * MessageSenderAction msAction = new MessageSenderAction();
 * msAction.setTarget( target );
 * msAction.setMessage(method);
 * ...
 * 
 * msAction.actionPerformed(...); // call target.doAction
 * </pre>
 * 
 * </p>
 * 
 * @author gwen
 */
@SuppressWarnings({ "nls", "serial" })
public class MessageSenderAction extends AbstractAction {

	private static final Logger LOG = LoggerFactory.getLogger(MessageSenderAction.class);

	private Object target;
	private String methodName;
	private Method method;

	public MessageSenderAction() {
	}

	/**
	 * Set the the target of the message
	 * 
	 * @param target
	 *            the target of the message
	 */
	public void setTarget(Object target) {
		Validate.notNull(target, "target argument is null");
		this.target = target;
	}

	/**
	 * Set the message to send to the target when the action is performed
	 * 
	 * @param methodName
	 *            a no argument method name of the target object
	 */
	public void setMessage(String methodName) {
		Validate.notEmpty(methodName, "message argument is not a method call");
		this.methodName = methodName;
	}

	private void initializeMethodIfNecessary() {
		if (method != null)
			return;

		try {
			this.method = target.getClass().getMethod(methodName);
		} catch (NoSuchMethodException ex) {
			String errMsg = String.format("Target does not contains a method '%s'", methodName);
			LOG.warn(errMsg);
			throw new UnsupportedOperationException(errMsg);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Validate.notNull(target, "target not initialized");
		Validate.notNull(methodName, "methodName not initialized");

		initializeMethodIfNecessary();

		try {
			method.invoke(target);
		} catch (IllegalAccessException accessEx) {
			String format = "Method '%s' not accessible";
			String msg = String.format(format, methodName);
			LOG.warn(msg);
			throw new UnsupportedOperationException(msg, accessEx);
		} catch (InvocationTargetException invocEx) {
			String format = "Invocation of method '%s' throw an exception";
			String msg = String.format(format, methodName);
			LOG.warn(msg);
			throw new UnsupportedOperationException(msg, invocEx);
		}
	}

}
