package com.github.pageallocation.algorithms;

import java.util.LinkedList;

import com.github.pageallocation.algorithms.model.Frames;
import com.github.pageallocation.algorithms.model.Reference;

public class LRUPageReplacement extends AbstractPageReplacement {

	@Override
	protected void allocate() {
		LinkedList<Integer> queue = new LinkedList<>();
		Frames past = new Frames(frames);
		for(Reference r: references){
			int ref = r.getReference();
			Frames f = r.getFrames();
			f.copyAll(past);
			queue.remove(new Integer(ref));
			queue.addLast(ref);
			System.out.println("FIFOAlloc.allocate( )" + ref + " " + f.contains(ref));
			if(!f.contains(ref)){
				faults++;
				
				if(f.thereIsAnEmptyFrame()){
					f.set(f.getEmptyFrame(), ref);
				}else{
					int victim = queue.removeFirst();
					f.swap(victim, ref);
				}
			}
			
			past.copyAll(f);
			
		}
		
	}

}
