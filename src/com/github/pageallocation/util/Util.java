package com.github.pageallocation.util;

import java.util.Random;

public class Util {

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Generates a random string of numbers between 1 and 30 (numbers are
	 * separated by commas).
	 * 
	 * @param length
	 *            the number of random numbers to generate for the string
	 * @return the string of random numbers
	 */
	public static String generateRandomPageReference(int length, int range) {
		StringBuilder sb = new StringBuilder();
		Random gen = new Random();
		final String commaSpace = ", ";
		for (int i = 0; i < length; i++) {
			int r = gen.nextInt(range + 1);
			if (i != length - 1)
				sb.append(r).append(commaSpace); // Places commas between
													// numbers in the
			// string
			else
				sb.append(r); // End the string with no comma
		}

		return sb.toString();
	}
}
