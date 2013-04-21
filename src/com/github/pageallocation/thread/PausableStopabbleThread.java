package com.github.pageallocation.thread;

public class PausableStopabbleThread extends Thread {
	private boolean pause = false;
	private boolean stop = false;

	protected final synchronized void pausePoint() {
		try {
			while (pause) {
				wait();
			}
		} catch (InterruptedException ignore) {
		}
	}

	public final synchronized void pause() {
		pause = true;
	}

	public final synchronized void requestStop() {
		stop = true;
		play();
	}

	protected final synchronized boolean stopRequested() {
		return stop;
	}

	public final synchronized void play() {
		pause = false;
		this.notifyAll();
	}

}
