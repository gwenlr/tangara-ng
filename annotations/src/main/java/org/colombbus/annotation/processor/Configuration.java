/**
 * Tangara is an educational platform to get started with programming. Copyright
 * (C) 2009 Colombbus (http://www.colombbus.org)
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.processing.ProcessingEnvironment;

/**
 *
 */
class Configuration {
	/** Fix bundles property in the configuration file */
	private static final String FIXBUNDLES_PROP = "fixbundles";//$NON-NLS-1$

	/** Localize property prefix */
	private static final String LOCALIZE_PROP_PREFIX = "localize."; //$NON-NLS-1$

	private static final String CONFIG_OPT = "org.colombbus.annotation.configuration"; //$NON-NLS-1$

	private static final String LANG_FIELD_SEP = "_"; //$NON-NLS-1$

	private AnnotationLogger logger;

	/**
	 * Configuration parameter to enable the correction of the localization
	 * bundles
	 */
	private boolean fixBundles = false;

	private final Map<Locale, String> localeToPackageName = new Hashtable<Locale, String>();

	/**
     *
     */
	public Configuration() {
	}

	public void setLogger(AnnotationLogger logger) {
		this.logger = logger;
	}

	/**
	 * Load the configuration
	 * 
	 * @param processingEnv
	 */
	public void loadConfiguration(ProcessingEnvironment processingEnv) {
		String cfgOptValue = processingEnv.getOptions().get(CONFIG_OPT);
		File configFile = new File(cfgOptValue);

		try {
			Properties config = loadProperties(configFile);
			extractLocalePackageNames(config);
			readFixParameter(config);
		} catch (FileNotFoundException notFoundEx) {
			logger.formatError("LocalizeProcessor.CONF_FILE_NOT_FOUND", configFile //$NON-NLS-1$
					.getAbsolutePath());
		} catch (IOException ioEx) {
			logger.formatError("LocalizeProcessor.ERROR_READ_CONF_FILE", configFile //$NON-NLS-1$
					.getAbsolutePath());
		}
	}

	private void readFixParameter(Properties config) {
		String fixBundlesValue = config.getProperty(FIXBUNDLES_PROP, Boolean.toString(fixBundles));
		fixBundles = Boolean.parseBoolean(fixBundlesValue);
	}

	private void extractLocalePackageNames(Properties config) {
		for (Map.Entry<Object, Object> confEntry : config.entrySet()) {
			String confKey = (String) confEntry.getKey();
			if (localizationKey(confKey)) {
				registerLocaleToPackage(confEntry);
			}
		}
	}

	private void registerLocaleToPackage(Map.Entry<Object, Object> confEntry) {
		String confKey = (String) confEntry.getKey();
		String localeName = confKey.substring(LOCALIZE_PROP_PREFIX.length());
		Locale locale = toLocale(localeName);
		if (locale != null) {
			String packageName = (String) confEntry.getValue();
			if (nullOrEmpty(packageName)) {
				packageName = localeName;
			}
			localeToPackageName.put(locale, packageName);
		}
	}

	private static boolean localizationKey(String confKey) {
		return confKey.startsWith(LOCALIZE_PROP_PREFIX);
	}

	/**
	 * Convert a language code to a {@link Locale}
	 * 
	 * @param lang
	 *            the language code (ex: <code>en</code>, <code>en_US</code>)
	 * @return the associated {@link Locale} or <code>null</code> if the locale
	 *         cannot be found
	 */
	private Locale toLocale(String lang) {
		String[] langFields = lang.split(LANG_FIELD_SEP);
		Locale locale = null;
		switch (langFields.length) {
		case 1:
			locale = new Locale(langFields[0]);
			break;
		case 2:
			locale = new Locale(langFields[0], langFields[1]);
			break;
		case 3:
			locale = new Locale(langFields[0], langFields[1], langFields[2]);
			break;
		default: {
			logger.formatWarn("LocalizeProcessor.UNSUPPORTED_LANG", lang);//$NON-NLS-1$
		}
		}
		return locale;
	}

	private static boolean nullOrEmpty(String string) {
		return string == null || string.length() == 0;
	}

	private static Properties loadProperties(File configFile) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(configFile);
			Properties config = new Properties();
			config.load(in);
			return config;
		} finally {
			ClosableHelper.closeQuietly(in);
		}
	}

	public boolean fixBundlesEnabled() {
		return fixBundles;
	}

	public String packageNameOfLocale(Locale locale) {
		return localeToPackageName.get(locale);
	}

	public Collection<Locale> getLocales() {
		return this.localeToPackageName.keySet();
	}

}
