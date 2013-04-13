package com.github.pageallocation.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CompositeSimulation implements Simulation {

	private final List<Simulation> simulations;

	public CompositeSimulation(List<Simulation> simulations) {
		this.simulations = Collections.synchronizedList(new ArrayList<>(
				simulations));
	}

	@Override
	public synchronized boolean hasMoreSteps() {
		return !simulations.isEmpty();
	}

	@Override
	public synchronized void step() {
		Simulation sim = null;
		for (Iterator<Simulation> it = simulations.iterator(); it.hasNext();) {
			sim = it.next();
			if (sim.hasMoreSteps()) {
				System.out.println("stepping");
				sim.step();
			} else {
				it.remove();
			}
		}

		System.out.println(sim);

	}

}
