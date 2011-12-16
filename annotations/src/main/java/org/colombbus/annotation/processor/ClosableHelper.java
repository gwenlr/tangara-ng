/**
 * Tangara is an educational platform to get started with programming.
 * Copyright (C) 2008 Colombbus (http://www.colombbus.org)
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

import java.io.Closeable;
import java.io.IOException;

/**
 * Set of methods relative to {@link Closeable}
 * 
 * @version $Id: ClosableHelper.java,v 1.2 2009/01/10 12:29:26 gwenael.le_roux Exp $
 * @author gwen
 */
public final class ClosableHelper {

    /** Singleton */
    private ClosableHelper() {
    }

    /**
     * Close a stream without throwing an exception
     * 
     * @param closeable
     *            the stream to close, maybe <code>null</code>
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ioEx) {  
                // ignored
            }
        }
    }

}
