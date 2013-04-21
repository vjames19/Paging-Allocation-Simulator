package com.github.pageallocation.simulation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.github.pageallocation.gui.PropertiesWindow;
import com.github.pageallocation.simulation.event.SimulationStateEvent;
import com.github.pageallocation.simulation.event.SimulationStateEvent.SimulationState;
import com.github.pageallocation.simulation.event.SimulationStateListener;
import com.github.pageallocation.simulation.event.SimulationStateObservable;

/**
 * Manages the simulations execution
 * 
 * @author Victor J.
 * 
 */
public class SimulationRunnerManager implements SimulationStateObservable {

	private final Simulation simulation;
	private final SimulationRunner runner;
	private boolean paused = false;
	private boolean running = false;
	private Set<SimulationStateListener> listeners = Collections
			.synchronizedSet(new HashSet<SimulationStateListener>(1));

	public SimulationRunnerManager() {
		this(null, null);
	}

	public SimulationRunnerManager(Simulation simulation,
			PropertiesWindow propWin) {
		this.simulation = new SynchronizedSimulation(simulation);
		this.runner = new SimulationRunner(simulation, propWin);
		this.runner.addListener(new FinishedSimulationListener());
		setRunning(true);
	}

	/**
	 * Starts the simulation runner if it has not been started yet
	 */
	private void startRunner() {
		if (runner.getState() == Thread.State.NEW) {
			runner.start();
		}
	}

	public synchronized void play() {
		System.out.println("SimulationRunnerManager.play()");
		if (!isRunning() && !isPaused()) {
			return;
		}

		startRunner();
		setPaused(false);
		runner.play();
		publish(SimulationState.PLAY);
	}

	public synchronized void stopSim() {
		System.out.println("SimulationRunnerManager.stopSim()");
		if (!isRunning()) {
			return;
		}
		runner.requestStop();
		setRunning(false);
		setPaused(false);
		publish(SimulationState.STOP);

	}

	public synchronized void step() {
		System.out.println("SimulationRunnerManager.step()");
		if (isRunning()) {
			pause();
			if (simulation.hasMoreSteps()) {
				simulation.step();
				publish(SimulationState.STEP);
			} else {
				stopSim();
			}
		}

	}

	public synchronized void pause() {
		System.out.println("SimulationRunnerManager.pause()");
		if (!isRunning() || isPaused()) {
			return;
		}
		runner.pause();
		setPaused(true);
		publish(SimulationState.PAUSE);

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

	public SimulationRunner getSimulationRunner() {
		return runner;
	}

	private void publish(SimulationState s) {
		SimulationStateEvent e = new SimulationStateEvent(simulation);
		for (SimulationStateListener l : listeners) {
			switch (s) {
			case PLAY:
				l.playEvent(e);
				break;
			case STOP:
				l.stopEvent(e);
				break;
			case PAUSE:
				l.pauseEvent(e);
				break;
			case STEP:
				l.stepEvent(e);
				break;
			}
		}
	}

	@Override
	public void addListener(SimulationStateListener l) {
		listeners.add(l);

	}

	@Override
	public void removeListener(SimulationStateListener l) {
		listeners.remove(l);

	}

	private class FinishedSimulationListener implements SimulationStateListener {

		@Override
		public void stepEvent(SimulationStateEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void playEvent(SimulationStateEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void pauseEvent(SimulationStateEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void stopEvent(SimulationStateEvent e) {
			System.out
					.println("SimulationRunnerManager.FinishedSimulationListener.stopEvent()");
			if (isRunning()) {
				stopSim();
			}
		}

	}

}
