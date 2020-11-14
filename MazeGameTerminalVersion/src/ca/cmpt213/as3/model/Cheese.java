package ca.cmpt213.as3.model;

/**
 * Represent a cheese's row position and column position
 */
public class Cheese {
    private int rowPosition;
    private int colPosition;

    public Cheese(int rowPosition, int colPosition) {
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColPosition() {
        return colPosition;
    }

    public void setColPosition(int colPosition) {
        this.colPosition = colPosition;
    }
}
