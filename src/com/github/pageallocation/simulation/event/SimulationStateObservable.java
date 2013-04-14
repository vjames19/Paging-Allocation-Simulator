package com.github.pageallocation.simulation.event;

public interface SimulationStateObservable {

	void addListener(SimulationStateListener l);

	void removeListener(SimulationStateListener l);
}
