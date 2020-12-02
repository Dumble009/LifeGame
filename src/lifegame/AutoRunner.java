package lifegame;

public class AutoRunner extends Thread {
	private boolean isRunning;
	private boolean isFinish;
	private BoardModel model;

	public AutoRunner() {
		isRunning = false;
		isFinish = false;
	}

	public void run() {
		while (!isFinish) {
			while (isRunning) {
				try {
					if (model != null) {
						model.next();
					}
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.err.println(e);
					return;
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.err.println(e);
				return;
			}
		}
	}

	public void finish() {
		isRunning = false;
		isFinish = true;
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
