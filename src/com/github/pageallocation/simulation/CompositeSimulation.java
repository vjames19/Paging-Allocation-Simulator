package com.github.pageallocation.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Simulates all the given simulations. A step in this simulation will be composed
 * of all the steps from the supplied simulations.   
 * @author Victor J.
 *
 */
public class CompositeSimulation implements Simulation {

	private final List<Simulation> simulations;

	public CompositeSimulation(List<Simulation> simulations) {
		this.simulations = new ArrayList<>(simulations);
	}

	@Override
	public boolean hasMoreSteps() {
		System.out.println("CompositeSimulation.hasMoreSteps() " + simulations.size());
		return !simulations.isEmpty();
	}

	@Override
	public void step() {
		Simulation sim = null;
		for (Iterator<Simulation> it = simulations.iterator(); it.hasNext();) {
			sim = it.next();
			if (sim.hasMoreSteps()) {
				sim.step();
			} else {
				it.remove();
			}
		}
	}

}
