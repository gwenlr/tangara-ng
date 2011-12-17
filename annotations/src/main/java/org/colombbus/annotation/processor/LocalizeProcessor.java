package org.colombbus.annotation.processor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import org.colombbus.annotation.LocalizableClass;
import org.colombbus.annotation.LocalizableMethod;

/**
 * Processor of the {@link LocalizableClass} and {@link LocalizableMethod}
 * annotations
 * 
 * @version $Id: LocalizeProcessor.java,v 1.4 2009/01/10 12:45:00
 *          gwenael.le_roux Exp $
 * @author Benoit
 * @author gwen
 */
@SupportedOptions("org.colombbus.annotation.configuration")
@SupportedAnnotationTypes({ "org.colombbus.annotation.LocalizableClass", "org.colombbus.annotation.LocalizableMethod" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class LocalizeProcessor extends AbstractProcessor {

	private static final String CONFIG_OPT = "org.colombbus.annotation.configuration"; //$NON-NLS-1$

	private final Collection<TypeElement> processedClasses = new HashSet<TypeElement>();

	private DictionaryStore dicStore;

	private final AnnotationLogger logger = new AnnotationLogger();

	private final Configuration conf = new Configuration();

	private ClassGenerator generator = new ClassGenerator();

	/**
	 * Create a new processor
	 */
	public LocalizeProcessor() {
		conf.setLogger(logger);
	}

	@Override
	public synchronized void init(ProcessingEnvironment processEnv) {
		super.init(processEnv);
		logger.setProcessingEnvironment(processEnv);
		logger.formatInfo("LocalizeProcessor.INITIALIZATION"); //$NON-NLS-1$

		if (processEnv.getOptions().containsKey(CONFIG_OPT)) {
			conf.loadConfiguration(processEnv);
			dicStore = new DictionaryStore(processEnv);
		} else {
			logger.formatError("LocalizeProcessor.MISSING_OPTION", CONFIG_OPT); //$NON-NLS-1$
		}

		generator.setConfiguration(conf);
		generator.setDictionnaryStore(dicStore);
		generator.setLogger(logger);
		generator.setProcessingEnvironment(processEnv);
	}

	private boolean firstProcessMethodCall = true;

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		int processedClassCount = 0;

		if (firstProcessMethodCall) {
			Set<? extends Element> localizElemSet = roundEnv.getElementsAnnotatedWith(LocalizableClass.class);
			Set<TypeElement> localizTypeSet = ElementFilter.typesIn(localizElemSet);

			for (TypeElement localizableTypeElem : localizTypeSet) {
				if (processedClasses.contains(localizableTypeElem) == false) {
					generator.localizeClass(localizableTypeElem);
					processedClasses.add(localizableTypeElem);
					processedClassCount++;
				}
			}

			// Generate the fixes
			if (conf.fixBundlesEnabled()) {
				fixBundles();
			}
			firstProcessMethodCall = false;
		}
		return processedClassCount > 0;
	}

	private void fixBundles() {
		logger.formatInfo("LocalizeProcessor.GENERATE_FIX"); //$NON-NLS-1$
		// TODO find the unused bundle files
		for (Dictionary dictionary : dicStore.dictionaries()) {
			dictionary.fixBundle(processingEnv.getMessager());
		}
	}

}