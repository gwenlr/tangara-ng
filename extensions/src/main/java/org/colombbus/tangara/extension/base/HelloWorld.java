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
@LocalizableClass("HelloWorld.class" )
public class HelloWorld {

    private String name;

    /**
     *
     */
    @LocalizableMethod("HelloWorld.HelloWorld")
    public HelloWorld() {
    }

    @LocalizableMethod("HelloWorld.HelloWorld")
    public HelloWorld(String name) {
        this.name = name;
    }

    @LocalizableMethod("HelloWorld.setName")
    public void setName(String name) {
        this.name = name;
    }


    @LocalizableMethod("HelloWorld.sayHello")
    public String sayHello() {
        if (name == null)
            return "Hello, world";
        return "Hello, " + name;
    }

    protected String getName() {
        return name;
    }

}
