package ca.cmpt213.as3.model;

/**
 * Represent a board's 2d cells grid and size
 */
public class Board {
    private int rowSize;
    private int colSize;
    private Cell[][] cellsGrid;

    public Board(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.cellsGrid = new Cell[rowSize][colSize];
    }

    public Cell getCell(int rowPosition, int colPosition) {
        return cellsGrid[rowPosition][colPosition];
    }

    public CellValueType getCellValue(int rowPosition, int colPosition) {
        return getCell(rowPosition, colPosition).getValue();
    }

    public void setCell(int rowPosition, int colPosition, Cell cell) {
        this.cellsGrid[rowPosition][colPosition] = cell;
    }

    public boolean getCellVisited(int rowPosition, int colPosition) {
        return cellsGrid[rowPosition][colPosition].isVisited();
    }

    public void setCellVisited(int rowPosition, int colPosition, boolean isVisited) {
        cellsGrid[rowPosition][colPosition].setVisited(isVisited);
    }

    public void setCellValue(int rowPosition, int colPosition, CellValueType value) {
        cellsGrid[rowPosition][colPosition].setValue(value);
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public void setCellVisible(int rowPosition, int colPosition, boolean isVisible) {
        cellsGrid[rowPosition][colPosition].setVisible(isVisible);
    }

    public boolean getCellVisible(int rowPosition, int colPosition) {
        return cellsGrid[rowPosition][colPosition].getVisible();
    }
}
