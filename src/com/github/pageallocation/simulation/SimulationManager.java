package com.github.pageallocation.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.pageallocation.gui.SimulationPanel;
import com.github.pageallocation.thread.PausableStopabbleThread;

/**
 * Manages the simulations execution
 * 
 * @author Victor J.
 * 
 */
public class SimulationManager {

	private List<SimulationPanel> simulations;
	private boolean paused = false;
	private boolean running = false;
	private volatile PausableStopabbleThread observer;

	public SimulationManager() {
		simulations = new ArrayList<>();
	}

	public SimulationManager(List<SimulationPanel> simulations) {
		this.simulations = Collections.unmodifiableList(simulations);
	}

	public void start() {
		if (isRunning()) {
			return;
		}
		for (SimulationPanel sim : simulations) {
			sim.getTableInsertion().start();
		}
		setRunning(true);
		observer = new SimulationsObserver();
		observer.start();

	}

	public void play() {
		if (!isRunning() && !isPaused()) {
			return;
		}
		setPaused(false);
		for (SimulationPanel sim : simulations) {
			sim.getTableInsertion().play();
		}
		observer.play();

	}

	public void stop() {
		if (!isRunning()) {
			return;
		}
		setRunning(false);
		setPaused(false);
		observer.stopWork();
		observer = null;
		for (SimulationPanel sim : simulations) {
			sim.getTableInsertion().stopWork();
		}

	}

	public void pause() {
		if (!isRunning() || isPaused()) {
			return;
		}
		setPaused(true);
		observer.pause();
		for (SimulationPanel sim : simulations) {
			sim.getTableInsertion().pause();
		}

	}

	private synchronized void setPaused(boolean b) {
		this.paused = b;

	}

	private synchronized boolean isPaused() {
		return paused;
	}

	public synchronized boolean isRunning() {
		return running;
	}

	private synchronized void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Specifies when the simulations have finished
	 * 
	 * @author Victor J.
	 * 
	 */
	class SimulationsObserver extends PausableStopabbleThread {

		private final int nSimulations = simulations.size();

		@Override
		public void run() {
			int finished = 0;
			while (!stopRequested()) {
				for (SimulationPanel sim : simulations) {
					if (!sim.getTableInsertion().isAlive()) {
						finished++;
					}
				}
				if (finished == nSimulations) {
					setRunning(false);
					return;

				} else {
					finished = 0;
				}
				pausePoint();
			}

		}

	}

}
