/**
 * Tangara is an educational platform to get started with programming. Copyright
 * (C) 2008 Colombbus (http://www.colombbus.org)
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

package org.colombbus.annotation.processor;

import java.util.Collection;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.annotation.processing.Messager;
import org.colombbus.annotation.LocalizableClass;
import org.colombbus.annotation.LocalizableMethod;
import org.colombbus.annotation.Usage;

/**
 * Translation dictionary used during localization processing.
 * <p>
 * The instance of this class shall only be created by a {@link DictionaryStore}
 * instance.
 * </p>
 * 
 * @version $Id: Dictionary.java,v 1.2 2009/01/13 19:50:28 gwenael.le_roux Exp $
 * @author gwen
 */
public class Dictionary {

	/** Suffix of the usage property in the properties file */
	private static final String USAGE_PROP_SUFFIX = ".usage"; //$NON-NLS-1$

	private String resourceName;
	private Properties content;

	private Collection<String> missingKeys = new HashSet<String>();
	private Collection<String> referencedKeys = new HashSet<String>();

	private AnnotationLogger logger = new AnnotationLogger();

	/**
	 * Create a new dictionary based on a bundle
	 * 
	 */
	Dictionary() {
	}

	/**
	 * @param resourceName
	 *            the resource name of the dictionary
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * 
	 * @param content
	 *            the dictionary content (word->translation)
	 */
	public void setContent(Properties content) {
		this.content = content;
	}

	public void setLogger(AnnotationLogger logger) {
		this.logger = logger;
	}

	/**
	 * Get the translation of a localization key (A field
	 * 
	 * @param localizationKey
	 *            the key in {@link LocalizableClass#value()} or
	 *            {@link LocalizableMethod#value()} and the localization bundle
	 *            file
	 * @return the translation or <code>null</code> if the key does not exist in
	 *         the dictionary
	 * @throws MissingResourceException
	 *             if the localizationKey has no translation
	 */
	public String translate(String localizationKey) throws MissingResourceException {
		referencedKeys.add(localizationKey);
		if (content.containsKey(localizationKey) == false) {
			missingKeys.add(localizationKey);
		}
		return content.getProperty(localizationKey);
	}

	/**
	 * Get the usage value
	 * 
	 * @param localizationKey
	 *            the key in {@link LocalizableClass#value()} or
	 *            {@link LocalizableMethod#value()} and the localization bundle
	 *            file
	 * @return the value of {@link Usage#value()} or null if the value cannot be
	 *         found
	 */
	public String getUsage(String localizationKey) {
		String usageKey = localizationKey + USAGE_PROP_SUFFIX;
		referencedKeys.add(usageKey);
		if (content.containsKey(usageKey) == false) {
			missingKeys.add(usageKey);
		}
		return content.getProperty(usageKey);
	}

	/**
	 * Display messages on standard output to fix the bundle
	 * <p>
	 * It shows the missing keys, the unused keys and the fixed keys.
	 * </p>
	 * 
	 * @param messager
	 *            the message reporter
	 */
	public void fixBundle(Messager messager) {
		// Find the unused properties
		Collection<String> unusedKeys = new HashSet<String>(content.stringPropertyNames());
		unusedKeys.removeAll(referencedKeys);

		// Find the inherited properties
		Collection<String> inheritedKeys = new HashSet<String>(referencedKeys);
		inheritedKeys.removeAll(content.keySet());

		boolean noErrorDetected = unusedKeys.size() == 0 && inheritedKeys.size() == 0 && missingKeys.size() == 0;
		if (noErrorDetected) {
			logger.formatInfo("Dictionary.BUNDLE_FINE", resourceName); //$NON-NLS-1$
		} else if (missingKeys.size() == 0) {
			logger.formatWarn("Dictionary.BUNDLE_ERRORS_HEADER", resourceName);//$NON-NLS-1$
		} else {
			logger.formatError("Dictionary.BUNDLE_ERRORS_HEADER", resourceName);//$NON-NLS-1$
		}

		for (String key : unusedKeys) {
			logger.formatWarn("Dictionary.UNUSED_KEY", key); //$NON-NLS-1$
		}

		// For each bundle file, find the missing properties
		for (String key : missingKeys) {
			logger.formatError("Dictionary.MISSING_KEY", key); //$NON-NLS-1$
		}

		for (String key : inheritedKeys) {
			logger.formatWarn("Dictionary.INHERITED_KEY", key); //$NON-NLS-1$
		}
	}

	/**
	 * Get the base name of the bundle
	 * 
	 * @return the base name of the bundle
	 */
	public String getResourceName() {
		return resourceName;
	}

}
