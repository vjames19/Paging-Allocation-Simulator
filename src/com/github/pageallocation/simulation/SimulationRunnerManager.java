package com.github.pageallocation.simulation;

import com.github.pageallocation.gui.PropertiesWindow;
import com.github.pageallocation.thread.PausableStopabbleThread;

/**
 * Manages the simulations execution
 * 
 * @author Victor J.
 * 
 */
public class SimulationRunnerManager {

	private final Simulation simulation;
	private final SimulationRunner runner;
	private boolean paused = false;
	private boolean running = false;
	private volatile PausableStopabbleThread observer;

	public SimulationRunnerManager() {
		this(null,null);
	}

	public SimulationRunnerManager(Simulation simulation, PropertiesWindow propWin) {
		this.simulation = simulation;
		this.runner = new SimulationRunner(simulation, propWin);
	}

	public synchronized void start() {
		if (isRunning()) {
			return;
		}
		runner.start();
		observer = new SimulationsObserver();
		observer.start();
		setRunning(true);

	}

	public synchronized void play() {
		if (!isRunning() && !isPaused()) {
			return;
		}
		setPaused(false);
		runner.play();
		observer.play();

	}

	public synchronized void stop() {
		if (!isRunning()) {
			return;
		}
		runner.requestStop();
		observer.requestStop();
		setRunning(false);
		setPaused(false);

	}

	public synchronized void step() {
		if(isRunning()){
			pause();
		}
		
		if(simulation.hasMoreSteps()){
			simulation.step();
		}else{
			setRunning(false);
			clearThreads();
		}
		
	}
	
	private void clearThreads(){
		observer.requestStop();
		observer = null;
		runner.requestStop();
	}

	public synchronized void pause() {
		if (!isRunning() || isPaused()) {
			return;
		}
		runner.pause();
		setPaused(true);

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
	
	public SimulationRunner getSimulationRunner(){
		return runner;
	}

	/**
	 * Specifies when the simulations have finished
	 * 
	 * @author Victor J.
	 * 
	 */
	class SimulationsObserver extends PausableStopabbleThread {

		@Override
		public void run() {
			while (!stopRequested() && !simulation.hasMoreSteps()) {
				
				pausePoint();
			}
			
			System.out.println("finished simulation");
			setRunning(false);
			clearThreads();

		}
	}
}
