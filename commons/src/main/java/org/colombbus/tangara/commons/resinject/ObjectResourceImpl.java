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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import org.apache.commons.lang3.Validate;
import org.colombbus.tangara.commons.mvc.Command;

/**
 * Default implementation of {@link ObjectResource}
 * 
 */
class ObjectResourceImpl implements ObjectResource {

	private Object objectInstance;
	private ClassResource classResource;
	private ActionResource actionResource;

	public ObjectResourceImpl() {
	}

	public void setObjectInstance(Object objectInstance) {
		Validate.notNull(objectInstance, "objectInstance argument is null"); //$NON-NLS-1$
		this.objectInstance = objectInstance;

		initializeClassResource();
		initializeActionResource();
	}

	private void initializeClassResource() {
		Class<?> objectClass = this.objectInstance.getClass();
		classResource = ResourceFactory.getClassResource(objectClass);
	}

	private void initializeActionResource() {
		ActionResourceImpl actionResourceImpl = new ActionResourceImpl();
		actionResourceImpl.setClassResource(this.classResource);
		actionResourceImpl.setActionHolder(this.objectInstance);
		actionResourceImpl.initialize();
		this.actionResource = actionResourceImpl;
	}

	@Override
	public boolean containsKey(String key) {
		checkInitialized();
		return classResource.containsKey(key);
	}

	private void checkInitialized() {
		if (objectInstance == null)
			throw new IllegalStateException("objectInstance not set"); //$NON-NLS-1$
	}

	@Override
	public Boolean getBoolean(String key) {
		checkInitialized();
		return classResource.getBoolean(key);
	}

	@Override
	public Color getColor(String key) {
		checkInitialized();
		return classResource.getColor(key);
	}

	@Override
	public Double getDouble(String key) {
		checkInitialized();
		return classResource.getDouble(key);
	}

	@Override
	public Float getFloat(String key) {
		checkInitialized();
		return classResource.getFloat(key);
	}

	@Override
	public Font getFont(String resource) {
		checkInitialized();
		return classResource.getFont(resource);
	}

	@Override
	public String getFormattedString(String key, Object... args) {
		checkInitialized();
		return classResource.getFormattedString(key, args);
	}

	@Override
	public String getI18NString(String key, Object... args) {
		checkInitialized();
		return classResource.getI18NString(key, args);
	}

	@Override
	public Image getImage(String key) {
		checkInitialized();
		return classResource.getImage(key);
	}

	@Override
	public ImageIcon getImageIcon(String key) {
		checkInitialized();
		return classResource.getImageIcon(key);
	}

	@Override
	public Integer getInteger(String key) {
		checkInitialized();
		return classResource.getInteger(key);
	}

	@Override
	public KeyStroke getKeyStroke(String key) {
		checkInitialized();
		return classResource.getKeyStroke(key);
	}

	@Override
	public Long getLong(String key) {
		checkInitialized();
		return classResource.getLong(key);
	}

	@Override
	public Short getShort(String key) {
		checkInitialized();
		return classResource.getShort(key);
	}

	@Override
	public String getString(String key) {
		checkInitialized();
		return classResource.getString(key);
	}

	@Override
	public URL getResourceURL(String key) {
		checkInitialized();
		return classResource.getResourceURL(key);
	}

	@Override
	public Cursor getCursor(String key) {
		checkInitialized();
		return classResource.getCursor(key);
	}

	@Override
	public Action getAction(Command command) {
		Action action = actionResource.getAction(command);
		return action;
	}

	@Override
	public void injectAction(AbstractButton button, Command command) {
		checkInitialized();
		actionResource.injectAction(button, command);
	}

	@Override
	public void inject(JLabel label) {
		checkInitialized();
		classResource.inject(label);
	}

	@Override
	public void inject(AbstractButton button) {
		checkInitialized();
		classResource.inject(button);
	}

	@Override
	public void inject(JMenu menu) {
		checkInitialized();
		classResource.inject(menu);
	}

	@Override
	public void inject(JComponent component) {
		checkInitialized();
		classResource.inject(component);
	}
}
