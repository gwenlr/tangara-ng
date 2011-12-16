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

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic.Kind;

/**
 *
 */
class AnnotationLogger {

    private ProcessingEnvironment processingEnv;

    public AnnotationLogger() {
    }

    public void setProcessingEnvironment(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    private void logFormattedMessage( Kind kind, String format, Object... params) {
        if( processingEnv != null) {
            String message = Messages.format(format, params);
            log( kind, message);
        }
    }

    private void log(Kind kind, String message) {
        if (processingEnv != null) {
            processingEnv.getMessager().printMessage(kind, message);
        }
    }

    public void info(String message) {
        log(Kind.NOTE, message);
    }

    public void warn(String message) {
        log(Kind.WARNING, message);
    }

    public void error(String message) {
        log(Kind.ERROR, message);
    }

    public void formatInfo(String format, Object... params) {
        logFormattedMessage(Kind.NOTE, format, params);
    }

    public void formatWarn(String format, Object... params) {
        logFormattedMessage(Kind.WARNING, format, params);
    }

    public void formatError(String format, Object... params) {
        logFormattedMessage(Kind.ERROR, format, params);
    }

}
