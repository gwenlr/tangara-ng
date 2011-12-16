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
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import javax.annotation.Generated;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.FileObject;

import org.colombbus.annotation.LocalizableClass;
import org.colombbus.annotation.LocalizableMethod;
import org.colombbus.annotation.Usage;

/**
 *
 */
class ClassGenerator {

    /** Argument separator */
    private static final String ARG_SEP = ", ";//$NON-NLS-1$

    private static final String USAGE_STATEMENT = "\t@Usage(\"%s\")\n"; //$NON-NLS-1$

    private static final String IMPORT_S = "import %s;\n"; //$NON-NLS-1$

    private static final String GENERATE_STMT = "@Generated(value=\"%1$s\",date=\"%2$tY-%2$tm-%2$tdT%2$tH:%2$tM:%2$tS.%2$tL%2$tz\")\n";//$NON-NLS-1$

    private final List<Entry<ExecutableElement, TypeElement>> scannedMethodList = new ArrayList<Entry<ExecutableElement, TypeElement>>();

    private ProcessingEnvironment processingEnv;

    private AnnotationLogger logger;

    private DictionaryStore dicStore;

    private Configuration conf = new Configuration();

    /**
     *
     */
    public ClassGenerator() {
        // TODO Auto-generated constructor stub
    }

    public void setProcessingEnvironment(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    public void setLogger(AnnotationLogger logger) {
        this.logger = logger;
    }

    public void setDictionnaryStore(DictionaryStore dicStore) {
        this.dicStore = dicStore;
    }

    public void setConfiguration(Configuration conf) {
        this.conf = conf;
    }

    /**
     * Translate a method and generate the code
     *
     * @param method
     *            the method to translate
     * @param dictionary
     *            the translation dictionary
     * @param localizableInfo
     *            the localizable information of the method
     * @return the java code of a method definition or an empty String
     * @throws MissingResourceException
     *             if the translation of a method is missing
     */
    private String generateMethodCode(ExecutableElement method, Dictionary dictionary,
            LocalizableMethod localizableInfo) throws MissingResourceException {
        StringBuilder javaCode = new StringBuilder();

        if (method.getKind() == ElementKind.METHOD) {
            String usageValue = dictionary.getUsage(localizableInfo.value());
            String usageCode = String.format(USAGE_STATEMENT, usageValue);
            javaCode.append(usageCode);
        }

        // Modifiers
        javaCode.append('\t');
        for (Modifier modifier : method.getModifiers()) {
            javaCode.append(modifier.toString()).append(' ');
        }

        if (method.getKind() == ElementKind.METHOD) {
            javaCode.append(method.getReturnType().toString()).append(' ');
        }

        String methodName = dictionary.translate(localizableInfo.value());
        if (methodName == null) {
            methodName = localizableInfo.value();
            logger.formatWarn("LocalizeProcessor.NO_TRANSLATION_METHOD", localizableInfo.value());//$NON-NLS-1$
        }
        javaCode.append(methodName).append("(");//$NON-NLS-1$
        int paramNum = 0;
        StringBuilder parameters = new StringBuilder();
        for (VariableElement parameter : method.getParameters()) {
            if (paramNum > 0) {
                javaCode.append(ARG_SEP);
                parameters.append(ARG_SEP);
            }
            String paramName = "p" + paramNum;//$NON-NLS-1$
            javaCode.append(parameter.asType().toString()).append(' ').append(paramName);
            parameters.append(paramName);
            paramNum++;
        }
        javaCode.append(")");//$NON-NLS-1$

        // Exception
        if (method.getThrownTypes().size() > 0) {
            javaCode.append(" throws "); //$NON-NLS-1$
            String separator = "";//$NON-NLS-1$
            for (TypeMirror reference : method.getThrownTypes()) {
                javaCode.append(separator);
                separator = ARG_SEP;
                javaCode.append(reference.toString());
            }
        }

        // Method's body
        javaCode.append(" {\n\t\t"); //$NON-NLS-1$

        if (method.getReturnType().getKind() != TypeKind.VOID) {
            javaCode.append("return "); //$NON-NLS-1$
        }

        javaCode.append("super"); //$NON-NLS-1$
        if (method.getKind() == ElementKind.METHOD) {
            javaCode.append('.').append(method.getSimpleName());
        }
        javaCode.append("(").append(parameters.toString()).append(");\n"); //$NON-NLS-1$//$NON-NLS-2$

        javaCode.append("\t}\n\n"); //$NON-NLS-1$

        return javaCode.toString();
    }

    /**
     * checks whether a method is overridden by an already scanned one
     *
     * @param method
     *            the method to check
     * @return <code>true</code> if the method us overridden, <code>false</code> otherwise
     */
    private boolean isMethodOverridden(ExecutableElement method) {
        // TODO check the exact meaning of this method and its usage
        for (Entry<ExecutableElement, TypeElement> parsedMethod : scannedMethodList) {
            if (elementUtils().overrides(parsedMethod.getKey(), method, parsedMethod.getValue())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generate code from inherited methods
     *
     * @param classType
     *            the inherited class
     * @param locale
     *            the translation language
     * @return an extract java code representing a set of methods definition or an empty string
     */
    private String generateInheritedCode(TypeElement classType, Locale locale) {
        if (classType == null) {
            // java.lang.Object has been reached
            return ""; //$NON-NLS-1$
        }

        // Get the list of methods
        List<? extends Element> enclosedElemSet = classType.getEnclosedElements();
        Collection<ExecutableElement> methods = ElementFilter.methodsIn(enclosedElemSet);

        StringBuilder javaCode = new StringBuilder();
        // Scan methods
        for (ExecutableElement method : methods) {
            LocalizableMethod localizeInfo = method.getAnnotation(LocalizableMethod.class);
            if (localizeInfo != null) {
                // Method has to be translated
                // Scan modifiers, check that the method is not private
                boolean isPrivate = method.getModifiers().contains(Modifier.PRIVATE);
                boolean overridden = isMethodOverridden(method);
                if (isPrivate == false && overridden == false) {
                    Dictionary dictionary = dicStore.findDictionary(classType, locale);
                    String localizeMethod = generateMethodCode(method, dictionary, localizeInfo);
                    javaCode.append(localizeMethod);
                }
            }
            Entry<ExecutableElement, TypeElement> entry = new SimpleEntry<ExecutableElement, TypeElement>(
                    method, classType);
            scannedMethodList.add(entry);
        }
        // Check if the superclass has to be scanned
        LocalizableClass localizeInfo = classType.getAnnotation(LocalizableClass.class);
        if (localizeInfo.inherit()) {
            TypeElement superClassType = (TypeElement) typeUtils().asElement(
                    classType.getSuperclass());
            String inheriteCode = generateInheritedCode(superClassType, locale);
            javaCode.append(inheriteCode);
        }

        return javaCode.toString();
    }

    /**
     * Localize a class
     *
     * @param typeElem
     *            the class to localize
     */
    public void localizeClass(TypeElement typeElem) {
        logger.formatInfo("LocalizeProcessor.LOCALIZE_CLASS", typeElem.getQualifiedName());//$NON-NLS-1$

        LocalizableClass localizeInfo = typeElem.getAnnotation(LocalizableClass.class);
        for (Locale locale : conf.getLocales()) {
            if (localizeInfo.localize()) {
                generateLocalizedClass(typeElem, locale);
            }
        }
    }

    private void generateLocalizedClass(TypeElement typeElem, Locale locale) {
        try {
            generateClassCode(typeElem, locale);
        } catch (MissingResourceException resEx) {
            String typeName = typeElem.getQualifiedName().toString();
            logger.formatError("LocalizeProcessor.RESOURCE_BUNDLE_NOT_FOUND", typeName, //$NON-NLS-1$
                    resEx.getMessage());
        } catch (IOException ioEx) {
            String typeName = typeElem.getQualifiedName().toString();
            logger.formatError("LocalizeProcessor.ERROR_LOCALIZING_CLASS", typeName, ioEx //$NON-NLS-1$
                    .getMessage());
            ioEx.printStackTrace();
        }
    }

    /**
     * Generate the code of a class
     *
     * @param modelType
     *            the type which the generated class will inherit
     * @param locale
     *            the dictionary to translate the class name and methods
     * @throws IOException
     * @throws MissingResourceException
     */
    private void generateClassCode(TypeElement modelType, Locale locale) throws IOException,
            MissingResourceException {
        Dictionary dictionary = dicStore.findDictionary(modelType, locale);
        LocalizableClass localizeInfo = modelType.getAnnotation(LocalizableClass.class);
        String genClassName = dictionary.translate(localizeInfo.value());
        String typePackage = elementUtils().getPackageOf(modelType).getQualifiedName().toString();
        String genPackage = typePackage + '.' + conf.packageNameOfLocale(locale);
        String canonicalGenClassName = genPackage + '.' + genClassName;

        logger.formatInfo("LocalizeProcessor.GENERATE_JAVA_FILE", canonicalGenClassName);//$NON-NLS-1$

        scannedMethodList.clear();

        Writer javaWriter = null;
        try {
            FileObject javaClassFile = processingEnv.getFiler().createSourceFile(
                    canonicalGenClassName);
            javaWriter = javaClassFile.openWriter();
            javaWriter.append("package ").append(genPackage).append(";\n"); //$NON-NLS-1$//$NON-NLS-2$
            javaWriter.append(generateImportCode(Usage.class));
            javaWriter.append(generateImportCode(Generated.class));

            String genAnnotCode = String.format(GENERATE_STMT, LocalizeProcessor.class
                    .getCanonicalName(), Calendar.getInstance());
            javaWriter.append(genAnnotCode);

            javaWriter.append("public class ").append(genClassName).append( //$NON-NLS-1$
                    " extends ").append(modelType.getQualifiedName()).append( //$NON-NLS-1$
                    "{\n"); //$NON-NLS-1$
            for (Element enclosedElem : modelType.getEnclosedElements()) {
                ElementKind kind = enclosedElem.getKind();
                if (kind == ElementKind.CONSTRUCTOR || kind == ElementKind.METHOD) {
                    ExecutableElement method = (ExecutableElement) enclosedElem;
                    LocalizableMethod localizableInfo = method
                            .getAnnotation(LocalizableMethod.class);
                    if (localizableInfo != null) {
                        String methodCode = generateMethodCode(method, dictionary, localizableInfo);
                        javaWriter.append(methodCode);
                    }

                    if (kind == ElementKind.METHOD) {
                        SimpleEntry<ExecutableElement, TypeElement> scannedMethod = new SimpleEntry<ExecutableElement, TypeElement>(
                                method, modelType);
                        scannedMethodList.add(scannedMethod);
                    }
                }
            }

            // Check if the superclass has to be scanned
            if (localizeInfo.inherit()) {
                TypeElement superType = (TypeElement) typeUtils().asElement(
                        modelType.getSuperclass());
                String inheritedCode = generateInheritedCode(superType, locale);
                javaWriter.append(inheritedCode);
            }

            javaWriter.append("}\n"); //$NON-NLS-1$
            javaWriter.flush();
        } finally {
            ClosableHelper.closeQuietly(javaWriter);
        }
    }

    private Elements elementUtils() {
        return processingEnv.getElementUtils();
    }

    private Types typeUtils() {
        return processingEnv.getTypeUtils();
    }

    /**
     * Generate an import statement
     *
     * @param clazz
     *            the class to import
     * @return a java code extract
     */
    private static String generateImportCode(Class<?> clazz) {
        return String.format(IMPORT_S, clazz.getCanonicalName());
    }
}
