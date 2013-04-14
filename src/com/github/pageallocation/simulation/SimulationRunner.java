package com.github.pageallocation.simulation;

import java.util.HashSet;
import java.util.Set;

import com.github.pageallocation.gui.PropertiesWindow;
import com.github.pageallocation.simulation.event.SimulationStateEvent;
import com.github.pageallocation.simulation.event.SimulationStateListener;
import com.github.pageallocation.simulation.event.SimulationStateObservable;
import com.github.pageallocation.thread.PausableStopabbleThread;

public class SimulationRunner extends PausableStopabbleThread implements SimulationStateObservable{

	private final Simulation simulation;
	private long sleepTime;
	private PropertiesWindow propWin;
	private Set<SimulationStateListener> listeners = new HashSet<>();

	public SimulationRunner(Simulation simulation, PropertiesWindow propWin) {
		this.simulation = simulation;
		this.sleepTime = 0;
		this.propWin = propWin;
	}

	@Override
	public void run() {

//		pause();
//		pausePoint();
		System.out.println("SimulationRunner.run() after pause");
		while (!stopRequested() && simulation.hasMoreSteps()) {

			simulation.step();

			try {
				if (!(propWin == null) && propWin.getDelay() > 0)
					sleep(propWin.getDelay());
				else
					sleep(sleepTime); // Default sleep time

			} catch (InterruptedException ie) {
				ie.printStackTrace();
				System.out.println("Thread.sleep() throwing an exception.");
			}

			pausePoint();

		}
		
		finished();

	}

	@Override
	public void addListener(SimulationStateListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeListener(SimulationStateListener l) {
		listeners.remove(l);
		
	}
	
	private void finished(){
		SimulationStateEvent e = new SimulationStateEvent(simulation);
		for(SimulationStateListener l:listeners){
			l.stopEvent(e);
		}
	}

}
