package lifegame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class ShutdownAdapter extends WindowAdapter {
	private JFrame frame;
	private AutoRunner autoRunner;

	ShutdownAdapter(JFrame f, AutoRunner a) {
		super();
		frame = f;
		autoRunner = a;
	}

	public void windowClosing(WindowEvent ev) {
		autoRunner.finish();
		try {
			autoRunner.join();
		} catch (InterruptedException e) {
			System.err.println(e);
		}
		frame.dispose();
	}
}
