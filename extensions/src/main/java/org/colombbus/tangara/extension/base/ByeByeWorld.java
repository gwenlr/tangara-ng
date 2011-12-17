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

package org.colombbus.tangara.extension.base;

import org.colombbus.annotation.LocalizableClass;
import org.colombbus.annotation.LocalizableMethod;

/**
 *
 */
@SuppressWarnings("nls")
@LocalizableClass("ByeByeWorld.class" )
public class ByeByeWorld {

    private String name;

    /**
     *
     */
    @LocalizableMethod("ByeByeWorld.ByeByeWorld")
    public ByeByeWorld() {
    }

    @LocalizableMethod("ByeByeWorld.ByeByeWorld")
    public ByeByeWorld(String name) {
        this.name = name;
    }

    @LocalizableMethod("ByeByeWorld.setName")
    public void setName(String name) {
        this.name = name;
    }


    @LocalizableMethod("ByeByeWorld.sayByeBye")
    public String sayByeBye() {
        if (name == null)
            return "Bye bye, world";
        return "Bye bye, " + name;
    }

    protected String getName() {
        return name;
    }

}
