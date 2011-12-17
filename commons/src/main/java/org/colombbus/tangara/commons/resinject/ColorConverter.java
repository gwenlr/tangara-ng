/**
 * Tangara is an educational platform to get started with programming.
 * Copyright (C) 2010 Colombbus (http://www.colombbus.org)
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
package org.colombbus.tangara.commons.resinject;

import java.awt.Color;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

class ColorConverter implements ResourceConverter {

	private static final String HEXA_PATTERN = "#[0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f][0-9A-Fa-f]"; //$NON-NLS-1$
	private static final String DEC_PATTERN = "[0-9]+,[0-9]+,[0-9]+"; //$NON-NLS-1$

	private Pattern hexaColorPattern;
	private Pattern decColorPattern;

	ColorConverter() {
		hexaColorPattern = Pattern.compile(HEXA_PATTERN);
		decColorPattern = Pattern.compile(DEC_PATTERN);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(String stringFormat, Class<?> resourceClass) {
		Color typedValue = Color.decode(stringFormat);
		return (T) typedValue;
	}

	private Color extractColor(String stringColor) throws NumberFormatException, ResourceFormatException {
		if (hexaColor(stringColor)) {
			return convertHexaToColor(stringColor);
		} else if (decColor(stringColor)) {
			return convertDecToColor(stringColor);
		} else {
			String message = String.format("Unsupported color format '%s'", stringColor); //$NON-NLS-1$
			throw new ResourceFormatException(null, message);
		}
	}

	private boolean hexaColor(String colorValue) {
		return hexaColorPattern.matcher(colorValue).matches();
	}

	private boolean decColor(String colorValue) {
		return decColorPattern.matcher(colorValue).matches();
	}

	private static Color convertDecToColor(String colorValue) throws InputMismatchException, NoSuchElementException {
		Scanner scanner = new Scanner(colorValue);
		scanner.useDelimiter(","); //$NON-NLS-1$
		int red = scanner.nextInt();
		int green = scanner.nextInt();
		int blue = scanner.nextInt();
		return new Color(red, green, blue);
	}

	private static Color convertHexaToColor(String colorValue) throws NumberFormatException {
		int red = extractHexaToInt(colorValue, 1);
		int green = extractHexaToInt(colorValue, 3);
		int blue = extractHexaToInt(colorValue, 5);
		return new Color(red, green, blue);
	}

	private static int extractHexaToInt(String hexaListString, int pos) throws NumberFormatException {
		String hexaString = hexaListString.substring(pos, pos + 2);
		return Integer.parseInt(hexaString, 16);
	}

}
