package org.colombbus.annotation.processor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * I18n of the output messages in the package
 * 
 * @version $Id: Messages.java,v 1.3 2009/01/10 10:11:36 gwenael.le_roux Exp $
 * @author gwen
 */
final class Messages {

	/** The address of the bundle containing the translated messages */
	private static final String BUNDLE_NAME = "org.colombbus.annotation.processor.messages"; //$NON-NLS-1$

	/** The bundle containing the translation of the messages */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Singleton, no instantiation
	 */
	private Messages() {
		throw new IllegalAccessError("singleton"); //$NON-NLS-1$
	}

	/**
	 * Gets a string for the given key from this resource bundle or one of its
	 * parents.
	 * 
	 * @param key
	 *            the resource identifier
	 * @return the resource value with a string format
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * Gets a formatted string for the given key from this resource bundle or
	 * one of its parents, and format it with some arguments
	 * 
	 * @param key
	 *            the resource identifier
	 * @param args
	 *            the arguments of the format
	 * @return a formatted string
	 */
	public static String format(String key, Object... args) {
		String format = getString(key);
		if (args.length == 0)
			return format;
		return String.format(format, args);
	}
}
