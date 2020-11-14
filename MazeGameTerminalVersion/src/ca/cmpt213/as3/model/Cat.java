package ca.cmpt213.as3.model;

/**
 * Represent a cat's row position and column position
 */
public class Cat {
    private int rowPosition;
    private int colPosition;

    public Cat(int rowPosition, int colPosition) {
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
