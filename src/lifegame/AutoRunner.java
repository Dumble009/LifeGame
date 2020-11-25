package lifegame;

public class AutoRunner extends Thread {
	boolean isRunning;
	BoardModel model;

	public AutoRunner() {
		isRunning = false;
	}

	public void run() {
		while (true) {
			while (isRunning) {
				try {
					if (model != null) {
						model.next();
					}
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.err.println(e);
			}
		}
	}

	public void stopRun() {
		isRunning = false;
	}

	public void startRun() {
		isRunning = true;
	}

	public boolean getIsRunning() {
		return isRunning;
	}

	public void setModel(BoardModel m) {
		model = m;
	}
}
