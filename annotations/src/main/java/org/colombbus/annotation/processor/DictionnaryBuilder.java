/**
 * Tangara is an educational platform to get started with programming.
 * Copyright (C) 2009 Colombbus (http://www.colombbus.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.colombbus.annotation.processor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.JavaFileManager.Location;

import org.colombbus.annotation.LocalizableClass;

/**
 * Builder of dictionaries of localized types
 */
class DictionnaryBuilder {

    /** Properties file extension */
    private static final String PROP_EXT = ".properties";//$NON-NLS-1$

    private TypeElement typeElem;
    private Locale locale;
    private ProcessingEnvironment processingEnv;
    private String resourceName;
    private final AnnotationLogger logger = new AnnotationLogger();
    private String bundleName;
    private String packageName;

    /**
     * Create an uninitialized builder
     */
    public DictionnaryBuilder() {
    }

    public void setProcessingEnv(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        logger.setProcessingEnvironment(processingEnv);
    }

    public void setTypeElement(TypeElement typeElem) {
        this.typeElem = typeElem;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Dictionary createDictionnary() {
        buildBundleName();
        buildPackageName();

        try {
            return doCreateDictionnary();
        } catch (IOException ioEx) {
            throw new MissingResourceException("Can't find the resource file", bundleName, ""); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    private Dictionary doCreateDictionnary() throws IOException {
        Dictionary dic = new Dictionary();
        dic.setLogger(logger);
        dic.setResourceName(resourceName);

        Properties content = loadProperties();
        dic.setContent(content);
        return dic;
    }

    private void buildPackageName() {
        if (bundleName.startsWith("/")) { //$NON-NLS-1$
            packageName = ""; //$NON-NLS-1$
        } else {
            packageName = elementUtils().getPackageOf(typeElem).getQualifiedName().toString();
        }
    }

    private Elements elementUtils() {
        return processingEnv.getElementUtils();
    }

    private void buildBundleName() {
        LocalizableClass localizeInfo = typeElem.getAnnotation(LocalizableClass.class);
        bundleName = localizeInfo.bundle() + '_' + locale.toString() + PROP_EXT;
    }

    /**
     * Load the properties from a property file in the sourcepath
     *
     * @return the content of the property file found
     * @throws IOException
     */
    private Properties loadProperties() throws IOException {
        FileObject fo = findResource();

        Properties props = new Properties();
        InputStream in = null;
        try {
            in = fo.openInputStream();
            props.load(in);
        } finally {
            ClosableHelper.closeQuietly(in);
        }
        return props;
    }

    /**
     * Find the file corresponding to a resource
     *
     * @return the file of the requested resource
     * @throws IOException
     */
    private FileObject findResource() throws IOException {
        Location resourceLocation = StandardLocation.locationFor(StandardLocation.SOURCE_PATH
                .getName());
        Filer filer = processingEnv.getFiler();
        FileObject fo = filer.getResource(resourceLocation, packageName, bundleName);
        return fo;
    }

}
