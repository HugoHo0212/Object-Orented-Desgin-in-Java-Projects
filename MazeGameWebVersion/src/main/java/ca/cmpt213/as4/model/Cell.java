package ca.cmpt213.as4.model;

/**
 * Represent a cell's value, row position, column position, and if it is visited and visible
 */
public class Cell {
    private CellValueType value;
    private boolean isVisited;
    private int rowPosition;
    private int colPosition;
    private boolean isVisible;

    public Cell(CellValueType value, boolean isVisited, int rowPosition, int colPosition) {
        this.value = value;
        this.isVisited = isVisited;
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
        isVisible = false;
    }

    public CellValueType getValue() {
        return value;
    }

    public void setValue(CellValueType value) {
        this.value = value;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColPosition() {
        return colPosition;
    }

    public boolean getVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "value=" + value +
                ", isVisited=" + isVisited +
                ", rowPosition=" + rowPosition +
                ", colPosition=" + colPosition +
                '}';
    }
}
