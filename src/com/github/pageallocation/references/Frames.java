package com.github.pageallocation.references;

import java.util.ArrayList;
import java.util.List;

public class Frames {

	private List<Integer> frames;
	
	public Frames(int numberOfFrames){
		this.frames = new ArrayList<>(numberOfFrames);
		for(int i=0; i < numberOfFrames; i++){
			frames.add(-1);
		}
	}
	
	public void deallocateFrames(){
		int len = frames.size();
		for(int i =0; i < len; i++){
			frames.set(i, -1);
		}
	}
	
	public boolean contains(int i){
		return indexOf(i) > -1;
	}
	
	public int indexOf(int i){
		return frames.indexOf(i);
	}
	
	public int set(int i, int r){
		return frames.set(i, r);
	}
	
	public void swap(int victim, int n){
		set(indexOf(victim), n);
	}
	
	public int get(int i){
		return frames.get(i);
	}
	
	public boolean thereIsAnEmptyFrame(){
		return contains(-1);
	}
	
	public void copyAll(Frames f){
		frames.clear();
		frames = new ArrayList<>(f.frames);
	}
	
	public int getEmptyFrame(){
		return indexOf(-1);
	}
//	public int[] getArray(){
//		int[] a = new int[frames.size()];
//		return frames.toArray(new Integer[0]);
//	}

}
