package com.github.pageallocation.simulation;

/**
 * A synchronized wrapper for a simulation
 * 
 * @author Victor J.
 * 
 */
public class SynchronizedSimulation implements Simulation {

	private Simulation simulation;

	public SynchronizedSimulation(Simulation sim) {
		this.simulation = sim;
	}

	@Override
	public synchronized boolean hasMoreSteps() {
		return simulation.hasMoreSteps();
	}

	@Override
	public synchronized void step() {
		simulation.step();
	}

}
