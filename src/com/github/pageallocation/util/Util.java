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

	/*
	 * Takes the string of numbers from the randStrArea and places the numbers
	 * into an integer array. Converts from String to int.
	 */
	public static int[] refStringToArray(String references) {
		String[] s2 = references.split(",\\s*", references.length());
		int[] i = new int[s2.length];

		for (int m = 0; m < s2.length; m++) {
			i[m] = Integer.parseInt(s2[m]);
		}

		return i;
	}

	public static Object[] makeHeaderArray(int[] s) {
		int length = s.length + 1;
		Object[] header = new Object[length];
		header[0] = "Frames";
		for (int i = 0; i < s.length; i++) {
			header[i + 1] = s[i];
		}
		return header;
	}
}
