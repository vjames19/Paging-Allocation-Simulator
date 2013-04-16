package com.github.pageallocation.references;

public class Reference {

	private int reference;
	private Frames frames;
	
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
