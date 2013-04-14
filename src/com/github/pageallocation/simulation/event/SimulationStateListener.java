package com.github.pageallocation.simulation.event;

public interface SimulationStateListener {

	public void stepEvent(SimulationStateEvent e);

	public void playEvent(SimulationStateEvent e);

	public void pauseEvent(SimulationStateEvent e);

	public void stopEvent(SimulationStateEvent e);

}
