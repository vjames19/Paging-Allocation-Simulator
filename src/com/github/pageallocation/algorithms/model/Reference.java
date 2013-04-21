package com.github.pageallocation.algorithms.model;

public class Reference {

	private int reference;
	private Frames frames;
	
	public Reference(int reference, int numberOfFrames){
		this(reference, new Frames(numberOfFrames));
	}
	public Reference(int reference, Frames frames){
		this.reference = reference;
		this.frames = frames;
	}

	public int getReference() {
		return reference;
	}

	public Frames getFrames() {
		return frames;
	}
	
	
	
	
}
