package lifegame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class BoardView extends JPanel implements BoardListener, MouseMotionListener, MouseListener {
	private CellGrid grid;
	private int pointingCellRow, pointingCellCol;
	private BoardModel model;

	boolean isInteractive;

	public BoardView(BoardModel _model) {
		super();
		model = _model;

		int cols = _model.getCols(), rows = _model.getRows();
		grid = new CellGrid(_model.getCols(), _model.getRows());

		pointingCellRow = 0;
		pointingCellCol = 0;

		isInteractive = true;

		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				grid.setAliveState(x, y, _model.getAliveState(x, y));
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		grid.recalc(getWidth(), getHeight());
		grid.paint(g);
	}

	public void setIsInteractive(boolean val) {
		isInteractive = val;
	}

	@Override
	public void updated(BoardModel m) {
		// TODO 自動生成されたメソッド・スタブ
		int cols = m.getCols(), rows = m.getRows();
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				grid.setAliveState(x, y, m.getAliveState(x, y));
				this.repaint();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (isInteractive) {
			// TODO 自動生成されたメソッド・スタブ
			int pRow = pointingCellRow, pCol = pointingCellCol;
			pointingCellRow = grid.getRowFromPointerY(e.getY());
			pointingCellCol = grid.getColFromPointerX(e.getX());

			if (pRow != pointingCellRow || pCol != pointingCellCol) {
				model.changeCellState(pointingCellCol, pointingCellRow);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		pointingCellRow = grid.getRowFromPointerY(e.getY());
		pointingCellCol = grid.getColFromPointerX(e.getX());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (isInteractive) {
			// TODO 自動生成されたメソッド・スタブ
			pointingCellRow = grid.getRowFromPointerY(e.getY());
			pointingCellCol = grid.getColFromPointerX(e.getX());
			if (pointingCellCol < grid.getCols() && pointingCellRow < grid.getRows()) {
				model.startEdit();
				model.changeCellState(pointingCellCol, pointingCellRow);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
