package lifegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public class Main implements Runnable {
	BoardModel model;

	public static void main(String[] args) {
		/*model.addListener(new ModelPrinter());
		model.changeCellState(1, 1);
		model.changeCellState(2, 2);
		model.changeCellState(0, 3);
		model.changeCellState(1, 3);
		model.changeCellState(2, 3);
		model.changeCellState(4, 4);
		model.changeCellState(4, 4);*/
		SwingUtilities.invokeLater(new Main());
	}

	public void run() {
		model = new BoardModel(12, 12);
		JFrame frame = new JFrame("Lifegame");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(400, 300));
		frame.setMinimumSize(new Dimension(300, 200));
		frame.pack();

		base.setLayout(new BorderLayout());
		BoardView view = new BoardView(model);
		view.addMouseListener(view);
		view.addMouseMotionListener(view);
		model.addListener(view);

		JPanel buttonPanel = new JPanel();
		base.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());

		JDialog dialog = new JDialog(frame, "Dialog");
		dialog.setPreferredSize(new Dimension(200, 200));
		dialog.getContentPane().setLayout(new FlowLayout());
		dialog.pack();
		JPanel dialogInputPanel = new JPanel();
		dialog.add(dialogInputPanel, BorderLayout.WEST);
		dialogInputPanel.setLayout(new FlowLayout());
		dialog.getContentPane().add(dialogInputPanel);

		JPanel dialogButtonPanel = new JPanel();
		dialog.add(dialogButtonPanel, BorderLayout.SOUTH);
		dialogButtonPanel.setLayout(new FlowLayout());
		dialog.getContentPane().add(dialogButtonPanel);

		JButton nextButton = new JButton("Next");
		JButton undoButton = new JButton("Undo");

		nextButton.addActionListener(
				(ActionEvent e) -> {
					model.next();
				});
		buttonPanel.add(nextButton);

		undoButton.addActionListener((ActionEvent e) -> {
			model.undo();
		});
		buttonPanel.add(undoButton);
		undoButton.setEnabled(false);
		model.addListener((BoardModel m) -> {
			undoButton.setEnabled(model.isUndoable());
		});

		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener((ActionEvent e) -> {
			SwingUtilities.invokeLater(new Main());
		});
		buttonPanel.add(newGameButton);

		AutoRunner autoThread = new AutoRunner();
		autoThread.setModel(model);
		autoThread.start();

		JButton autoButton = new JButton("Auto");
		autoButton.addActionListener((ActionEvent e) -> {
			if (autoThread.getIsRunning()) {
				autoThread.stopRun();
				autoButton.setText("Auto");
			} else {
				autoThread.startRun();
				autoButton.setText("Stop");
			}
		});
		buttonPanel.add(autoButton);

		JButton configButton = new JButton("Config");
		configButton.addActionListener((ActionEvent e) -> {
			dialog.setVisible(true);
		});
		buttonPanel.add(configButton);

		JSpinner calField = new JSpinner(new SpinnerNumberModel(12, 2, 30, 1)),
				rowField = new JSpinner(new SpinnerNumberModel(12, 2, 30, 1));
		dialogInputPanel.add(calField);
		dialogInputPanel.add(rowField);

		base.add(view, BorderLayout.CENTER);

		frame.setVisible(true);
		dialog.setVisible(false);
	}
}
