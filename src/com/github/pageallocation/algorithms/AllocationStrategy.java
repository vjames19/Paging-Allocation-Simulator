package com.github.pageallocation.algorithms;

public interface AllocationStrategy {

	// The primary method performs all the algorithmic replacements and fills a
	// 2D array
	public int[][] allocation(int[] references, int frames);

	// This function returns the number of page faults counted.
	public int faults();

	public double faultRate(int refs, int f);

	public void clearStats();

}