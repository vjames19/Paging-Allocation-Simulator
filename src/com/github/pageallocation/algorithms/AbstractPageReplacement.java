package com.github.pageallocation.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.pageallocation.references.Frames;
import com.github.pageallocation.references.Reference;

public abstract class AbstractPageReplacement implements PageReplacementStrategy{

	protected List<Reference> references;
	protected List<Integer> refs;
	protected int faults;
	protected int frames;

	@Override
	public int[][] allocation() {
		// TODO Auto-generated method stub
		allocate();
		int[][] alloc = new int[references.size() +1 ][frames];
		
		for (int i = 0; i < frames; i++) {
			alloc[0][i] = -1;
		}

		int size = references.size();
		for(int i= 1; i < alloc.length; i++){
			Frames f = references.get(i-1).getFrames();
			for(int j = 0; j < frames; j++){
				alloc[i][j] = f.get(j);
			}
		}
		
		return alloc;
	}
	
	protected abstract void allocate();


	@Override
	public int[][] call() throws Exception {
		// TODO Auto-generated method stub
		return allocation();
	}

	@Override
	public int faults() {
		// TODO Auto-generated method stub
		return faults;
	}

	@Override
	public double faultRate() {
		// TODO Auto-generated method stub
		return (faults * 1.0 /references.size() * 100);
	}

	@Override
	public void clearStats() {
		faults = 0;
		
	}

	@Override
	public void setParams(int[] references, int frames) {
		faults =0;
		refs = new ArrayList<>(references.length);
		for(int i: references){
			refs.add(i);
		}
		//refs = Collections.unmodifiableList(refs);
		this.frames = frames;
		this.references = new ArrayList<Reference>(references.length);
		for(int i: references){
			this.references.add(new Reference(i, new Frames(frames)));
		}
		
	}
	


}
