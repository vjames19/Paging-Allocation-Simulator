package com.github.pageallocation.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.pageallocation.gui.PropertiesWindow;
import com.github.pageallocation.thread.PausableStopabbleThread;

public class SimulationRunner extends PausableStopabbleThread {

	private final Simulation simulation;
	private long sleepTime;
	private PropertiesWindow propWin;

	public SimulationRunner(Simulation simulation,
			PropertiesWindow propWin) {
		this.simulation = simulation;
		this.sleepTime = 0;
		this.propWin = propWin;
	}

	@Override
	public void run() {

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
	}
	


}
