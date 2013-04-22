package com.github.pageallocation.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.github.pageallocation.algorithms.model.Reference;

public abstract class AbstractPageReplacement implements
		PageReplacementStrategy {

	protected List<Reference> references;
	protected List<Integer> refs;
	protected int faults;
	protected int frames;

	protected abstract void allocate();

	@Override
	public List<Reference> call() throws Exception {
		return allocateReferences();
	}

	@Override
	public int faults() {
		return faults;
	}

	@Override
	public double faultRate() {
		return (faults * 1.0 / references.size() * 100);
	}

	@Override
	public void clearStats() {
		faults = 0;

	}

	@Override
	public void setParams(int[] references, int frames) {
		faults = 0;
		this.frames = frames;
		this.refs = new ArrayList<>(references.length);
		this.references = new ArrayList<Reference>(references.length);
		for (int i : references) {
			refs.add(i);
			this.references.add(new Reference(i, frames));
		}
		
		faults = 0;

	}

	@Override
	public List<Reference> allocateReferences() {
		allocate();
		return references;
	}

}
