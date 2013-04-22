package com.github.pageallocation.algorithms.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Representation of frames and an interface to interact with them.
 * 
 * @author Victor J.
 * 
 */
public class Frames implements Iterable<Integer> {

	private List<Integer> frames;

	public Frames(int numberOfFrames) {
		this.frames = new ArrayList<>(numberOfFrames);
		for (int i = 0; i < numberOfFrames; i++) {
			frames.add(-1);
		}
	}

	public void deallocateFrames() {
		int len = frames.size();
		for (int i = 0; i < len; i++) {
			frames.set(i, -1);
		}
	}

	public boolean contains(int reference) {
		return indexOf(reference) > -1;
	}

	public int indexOf(int reference) {
		return frames.indexOf(reference);
	}

	public int set(int index, int reference) {
		return frames.set(index, reference);
	}

	public void swap(int victim, int reference) {
		set(indexOf(victim), reference);
	}

	public int get(int index) {
		return frames.get(index);
	}

	public boolean thereIsAnEmptyFrame() {
		return contains(-1);
	}

	public void copyAll(Frames f) {
		frames.clear();
		frames = new ArrayList<>(f.frames);
	}

	public int getEmptyFrame() {
		return indexOf(-1);
	}

	@Override
	public Iterator<Integer> iterator() {
		return frames.iterator();
	}

}
