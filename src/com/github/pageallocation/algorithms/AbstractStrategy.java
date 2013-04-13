package com.github.pageallocation.algorithms;

public abstract class AbstractStrategy implements AllocationStrategy {

	protected int pageFault = 0;
	protected int[] references;
	protected int frames;

	public AbstractStrategy() {
	}

	@Override
	public int faults() {
		return pageFault;
	}

	@Override
	public double faultRate() {
		return ((pageFault * 1.0) / frames) * 100;
	}

	@Override
	public void clearStats() {
		pageFault = 0;
	}

	@Override
	public void setParams(int[] references, int frames) {
		this.references = references;
		this.frames = frames;
		this.pageFault = 0;

	}

	@Override
	public int[][] call() throws Exception {
		return allocation();
	}
}
