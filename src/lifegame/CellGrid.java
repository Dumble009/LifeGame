package lifegame;

import java.awt.Graphics;

public class CellGrid {
	int cols, rows;
	int offsetX, offsetY;

	Cell[][] cells;

	int size = 20;

	public CellGrid(int _cols, int _rows) {
		cols = _cols;
		rows = _rows;

		offsetX = 0;
		offsetY = 0;

		cells = new Cell[rows][cols];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				cells[y][x] = new Cell(x * size, y * size, size);
			}
		}
	}

	public void paint(Graphics g) {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				cells[y][x].paint(g);
			}
		}
	}

	public void recalc(int width, int height) {
		size = width / cols < height / rows ? width / cols : height / rows;
		int centerX = width / 2, centerY = height / 2;

		offsetX = centerX - size * (cols / 2);

		if (cols % 2 != 0) {
			offsetX -= size / 2;
		}

		offsetY = centerY - size * (rows / 2);

		if (rows % 2 != 0) {
			offsetY -= size / 2;
		}

		resetCellParam();
	}

	private void resetCellParam() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				cells[y][x].setSize(size);
				cells[y][x].setPosition(offsetX + x * size, offsetY + y * size);
			}
		}
	}

	public void setAliveState(int x, int y, boolean state) {
		cells[y][x].setAliveState(state);
	}

	public int getRowFromPointerY(int pointerY) {
		return (pointerY - offsetY) / size;
	}

	public int getColFromPointerX(int pointerX) {
		return (pointerX - offsetX) / size;
	}
}
