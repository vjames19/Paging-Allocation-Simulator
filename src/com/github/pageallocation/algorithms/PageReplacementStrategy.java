package com.github.pageallocation.algorithms;

import java.util.List;
import java.util.concurrent.Callable;

import com.github.pageallocation.algorithms.model.Reference;

public interface PageReplacementStrategy extends Callable<List<Reference>> {

	public List<Reference> allocateReferences();

	// This function returns the number of page faults counted.
	public int faults();

	public double faultRate();

	public void clearStats();

	public void setParams(int[] references, int frames);

}