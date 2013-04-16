package com.github.pageallocation.algorithms;

import java.util.LinkedList;

import com.github.pageallocation.references.Frames;
import com.github.pageallocation.references.Reference;

public class FIFOPageReplacement extends AbstractPageReplacement {

	@Override
	protected void allocate() {
		
		LinkedList<Integer> queue = new LinkedList<>();
		Frames past = new Frames(frames);
		for(Reference r: references){
			int ref = r.getReference();
			Frames f = r.getFrames();
			f.copyAll(past);
			System.out.println("FIFOAlloc.allocate( )" + ref + " " + f.contains(ref));
			if(!f.contains(ref)){
				faults++;
				queue.addLast(ref);
				if(f.thereIsAnEmptyFrame()){
					System.out.println("empty");
					f.set(f.getEmptyFrame(), ref);
				}else{
					System.out.println("victim");
					int victim = queue.removeFirst();
					f.swap(victim, ref);
				}
			}
			
			past.copyAll(f);
			
		}

	}

}
