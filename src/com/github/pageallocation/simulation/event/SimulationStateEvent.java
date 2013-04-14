package com.github.pageallocation.simulation.event;

import java.util.EventObject;

public class SimulationStateEvent extends EventObject {

	public enum SimulationState {
		PLAY, PAUSE, STOP, STEP;
	}

	public SimulationStateEvent(Object source) {
		super(source);
	}

}
