package lifegame;

import java.awt.Graphics;

public class Cell {
	boolean isAlive;
	int x, y, size;

	public Cell(int _x, int _y, int _size) {
		isAlive = false;

		x = _x;
		y = _y;
		size = _size;
	}

	public void paint(Graphics g) {
		if (isAlive) {
			g.fillRect(x, y, size, size);
		} else {
			g.drawRect(x, y, size, size);
		}
	}

	public void setAliveState(boolean _isAlive) {
		isAlive = _isAlive;
	}

	public void setPosition(int _x, int _y) {
		x = _x;
		y = _y;
	}

	public void setSize(int _size) {
		size = _size;
	}
}
