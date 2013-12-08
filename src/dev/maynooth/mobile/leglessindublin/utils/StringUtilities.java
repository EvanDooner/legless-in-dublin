package dev.maynooth.mobile.leglessindublin.utils;

import java.util.Locale;

/**
 * Performs various utility functions on strings
 * 
 * @author Evan Dooner, 12262480
 * @version 2013-12-08-00
 */
public class StringUtilities {

	/**
	 * Converts a string to title case
	 * <p>
	 * The first character of every space-seperated word in a title case string
	 * is capitalized.
	 * 
	 * @param inString
	 *            a string - the string to be converted
	 * @return a string in title case
	 */
	public static String toTitleCase(String inString) {
		if (inString == null || inString.equalsIgnoreCase("")) {
			return inString;
		}
		if (inString.length() == 1) {
			return inString.toUpperCase(Locale.getDefault());
		}
		String[] words = inString.split("\\s");
		StringBuilder builder = new StringBuilder();
		for (String word : words) {
			builder.append(Character.toUpperCase(word.charAt(0))
					+ word.substring(1));
			builder.append(" ");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	/**
	 * Converts a string to sentence case
	 * 
	 * The first character of a sentence case string is capitalised
	 * 
	 * @param inString
	 *            a string - the string to be converted
	 * @return a string in sentence case
	 */
	public static String toSentenceCase(String inString) {
		if (inString == null || inString.equalsIgnoreCase("")) {
			return inString;
		}
		if (inString.length() == 1) {
			return inString.toUpperCase(Locale.getDefault());
		}
		return Character.toUpperCase(inString.charAt(0))
				+ inString.substring(1);
	}

	private StringUtilities() {
	};

}
