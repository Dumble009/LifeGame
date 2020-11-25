package lifegame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

		JButton nextButton = new JButton("Next");
		JButton undoButton = new JButton("Undo");

		nextButton.addActionListener(
				(ActionEvent e) -> {
					model.next();
					undoButton.setEnabled(true);
				});
		buttonPanel.add(nextButton);

		undoButton.addActionListener((ActionEvent e) -> {
			model.undo();
			if (!model.isUndoable()) {
				undoButton.setEnabled(false);
			}
		});
		buttonPanel.add(undoButton);
		undoButton.setEnabled(false);

		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener((ActionEvent e) -> {
			SwingUtilities.invokeLater(new Main());
		});
		buttonPanel.add(newGameButton);

		base.add(view, BorderLayout.CENTER);

		frame.setVisible(true);
	}
}
