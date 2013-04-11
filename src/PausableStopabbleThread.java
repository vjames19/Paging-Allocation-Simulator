public class PausableStopabbleThread extends Thread {
	private boolean pause = false;
	private boolean stop = false;

	protected final synchronized void pausePoint() {
		try {
			while (pause) {
				wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void pause() {
		pause = true;

	}
	
	public synchronized void stopWork(){
		stop = true;
	}
	
	protected final synchronized boolean stopRequested(){
		return stop;
	}

	public synchronized void play() {
		pause = false;
		this.notify();
	}

}
