package lifegame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class BoardModel {
	private int cols;
	private int rows;
	private boolean[][] cells;
	private ArrayList<BoardListener> listeners;
	private Deque<boolean[][]> cellHistory;
	final int undoMaxCount = 32;

	public BoardModel(int c, int r) {
		cols = c;
		rows = r;
		cells = new boolean[rows][cols];
		listeners = new ArrayList<BoardListener>();
		cellHistory = new ArrayDeque<boolean[][]>();
	}

	public int getCols() {
		return cols;
	}

	public int getRows() {
		return rows;
	}

	public boolean getAliveState(int x, int y) {
		return cells[y][x];
	}

	public void printForDebug() {
		for (int i = 0; i < rows; i++) {
			String l = "";
			for (int j = 0; j < cols; j++) {
				char o = '.';
				if (cells[i][j]) {
					o = '*';
				}
				l += o;
			}

			System.out.println(l);
		}
	}

	public void changeCellState(int x, int y) {
		if (x >= cols || y >= rows || x < 0 || y < 0) {
			return;
		}
		cells[y][x] = !cells[y][x];
		this.fireUpdate();
	}

	public void addListener(BoardListener listener) {
		listeners.add(listener);
	}

	private void fireUpdate() {
		for (BoardListener listener : listeners) {
			listener.updated(this);
		}
	}

	public void next() {
		addToHistory();
		var nextCells = new boolean[rows][cols];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				nextCells[y][x] = calcNext(x, y);
			}
		}
		cells = nextCells;

		fireUpdate();
	}

	public void undo() {
		if (isUndoable()) {
			cells = cellHistory.getFirst();
			cellHistory.removeFirst();

			fireUpdate();
		}
	}

	public boolean isUndoable() {
		return cellHistory.size() > 0;
	}

	private boolean calcNext(int x, int y) {
		if (x >= cols || y >= rows || x < 0 || y < 0) {
			return false;
		}

		int livingCellCount = 0;
		for (int aX = x - 1; aX <= x + 1; aX++) {
			for (int aY = y - 1; aY <= y + 1; aY++) {
				if (aX == x && aY == y) {
					continue;
				}
				if (getCurrentCellState(aX, aY)) {
					livingCellCount++;
				}
			}
		}

		if (getCurrentCellState(x, y)) {
			if (livingCellCount <= 1 || livingCellCount >= 4) {
				return false;
			}
			return true;
		} else {
			if (livingCellCount == 3) {
				return true;
			}
			return false;
		}
	}

	private void addToHistory() {
		cellHistory.addFirst(cells);
		if (cellHistory.size() > undoMaxCount) {
			cellHistory.removeLast();
		}
	}

	private boolean getCurrentCellState(int x, int y) {
		if (x >= cols || y >= rows || x < 0 || y < 0) {
			return false;
		}

		return cells[y][x];
	}
}
