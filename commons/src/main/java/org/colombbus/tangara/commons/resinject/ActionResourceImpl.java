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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.Action;

import org.apache.commons.lang3.Validate;
import org.colombbus.tangara.commons.mvc.Command;
import org.colombbus.tangara.commons.mvc.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default action store implementation
 * <p>
 * Usage:
 * 
 * <pre>
 * ActionResourceImpl actionStore = new ActionResource();
 * actionStore.setClassResource(...);
 * actionStore.setActionHolder(...);
 * actionStore.initialize();
 * ...
 * Action action = actionStore.getAction("aCommand");
 * </pre>
 * 
 * </p>
 * 
 * @author gwen
 */
public class ActionResourceImpl implements ActionResource {

	private final Logger LOG = LoggerFactory.getLogger(ActionResourceImpl.class);
	private final Map<Command, Action> commandActionMap = new HashMap<Command, Action>();
	private ClassResource classResource;
	private Object actionHolder;

	public ActionResourceImpl() {
	}

	public void setClassResource(ClassResource classResource) {
		Validate.notNull(classResource, "classResource argument is null"); //$NON-NLS-1$
		this.classResource = classResource;
	}

	public void setActionHolder(Object actionHolder) {
		Validate.notNull(actionHolder, "actionHolder argument is null"); //$NON-NLS-1$
		this.actionHolder = actionHolder;
	}

	public void initialize() {
		checkAttributeSet(classResource, "classResource not set"); //$NON-NLS-1$
		checkAttributeSet(actionHolder, "actionHolder not set"); //$NON-NLS-1$

		for (Method method : actionHolder.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(ActionPerformer.class))
				parseMethod(method);
		}
	}

	private void checkAttributeSet(Object attribute, String message) {
		if (attribute == null) {
			LOG.error(message);
			throw new RuntimeException(message);
		}
	}

	private void parseMethod(Method method) {
		ActionPerformer actionPerformer = method.getAnnotation(ActionPerformer.class);

		String commandId = actionPerformer.command();
		if (commandId.length() == 0)
			commandId = method.getName();
		Command command = CommandFactory.getCommand(commandId);

		String resourceKey = actionPerformer.resource();
		if (resourceKey.length() == 0)
			resourceKey = method.getName();

		MessageSenderAction action = new MessageSenderAction();
		action.setTarget(actionHolder);
		action.setMessage(method.getName());

		injectAction(action, resourceKey);

		commandActionMap.put(command, action);
	}

	private void injectAction(Action action, String actionKey) {
		ActionInjecter injecter = new ActionInjecter();

		injecter.setClassResource(classResource);
		injecter.setAction(action);
		injecter.setActionKey(actionKey);

		injecter.inject();
	}

	@Override
	public Action getAction(Command command) {
		checkCommandExist(command);
		Action action = commandActionMap.get(command);
		return action;
	}

	private void checkCommandExist(Command command) {
		Validate.notNull(command, "command argument is null"); //$NON-NLS-1$
		if (commandActionMap.containsKey(command) == false) {
			String format = "Cannot find action method for command '%s'"; //$NON-NLS-1$
			String message = String.format(format, command);
			LOG.error(message);
			throw new IllegalArgumentException(message);
		}
	}

	@Override
	public void injectAction(AbstractButton button, Command command) {
		checkCommandExist(command);
		Validate.notNull(button, "button argument is null"); //$NON-NLS-1$
		Action action = getAction(command);
		button.setAction(action);
	}

}
