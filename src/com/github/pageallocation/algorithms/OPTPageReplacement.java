package com.github.pageallocation.algorithms;

import com.github.pageallocation.algorithms.model.Frames;
import com.github.pageallocation.algorithms.model.Reference;

public class OPTPageReplacement extends AbstractPageReplacement {

	@Override
	protected void allocate() {
		
		Frames past = new Frames(frames);
		for(Reference r: references){
			int ref = r.getReference();
			Frames f = r.getFrames();
			f.copyAll(past);
			if(!f.contains(ref)){
				faults++;
				if(f.thereIsAnEmptyFrame()){
					f.set(f.getEmptyFrame(), ref);
				}else{
					int victim = getLastReferenced(f);	
					f.swap(victim, ref);
				}
			}			
			past.copyAll(f);
			refs.remove(0);
			
		}
	}
	
	private int getLastReferenced(Frames f){
		int last = -1;
		int lastOne=-1;
		for(int i =0; i < frames; i++){
			int victim = f.get(i);
			int index = refs.indexOf(victim);
			if(index == -1){
				return victim;
			}
			else if(index > last){
				last = index;
				lastOne = victim;
			}
		}
		return lastOne;
	}

}
