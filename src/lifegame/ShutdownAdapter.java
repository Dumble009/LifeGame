package lifegame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class ShutdownAdapter extends WindowAdapter {
	JFrame frame;
	Thread thread;

	ShutdownAdapter(JFrame f, Thread t) {
		super();
		frame = f;
		thread = t;
	}

	public void windowClosing(WindowEvent e) {
		thread.interrupt();
		frame.dispose();
	}
}
