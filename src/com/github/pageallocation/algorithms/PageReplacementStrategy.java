package com.github.pageallocation.algorithms;

import java.util.concurrent.Callable;

public interface PageReplacementStrategy extends Callable<int[][]> {

	// The primary method performs all the algorithmic replacements and fills a
	// 2D array
	public int[][] allocation();

	// This function returns the number of page faults counted.
	public int faults();

	public double faultRate();

	public void clearStats();

	public void setParams(int[] references, int frames);

}