package ca.cmpt213.as3.model;

/**
 * Represent a mouse's row position and column position
 */
public class Mouse {
    private int rowPosition;
    private int colPosition;

    public Mouse(int rowPosition, int colPosition) {
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColPosition() {
        return colPosition;
    }

    public void moveLeft() {
        colPosition --;
    }

    public void moveRight() {
        colPosition ++;
    }

    public void moveUp() {
        rowPosition --;
    }

    public void moveDown() {
        rowPosition ++;
    }
}
