package ca.cmpt213.as4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represent a maze's size and board
 */
public class Maze {
    private int rowSize;
    private int colSize;
    private Board board;

    public Maze(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.board = new Board(rowSize, colSize);
        generateMaze();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoardCellVisible(int rowPosition, int colPosition, boolean isVisible) {
        board.setCellVisible(rowPosition, colPosition, isVisible);
    }

    public boolean getBoardCellVisible(int rowPosition, int colPosition) {
        return board.getCellVisible(rowPosition, colPosition);
    }

    private void generateMaze() {
        boolean isDone = false;
        while (!isDone) {
            generateRandomizedPrimMaze();
            addMazeRequirement(6);
            if (!isOpenOrWallSquareExisted()) {
                isDone = true;
            }
        }
    }

    private void fillBoardWillWalls() {
        for (int row = 0; row < rowSize; row ++) {
            for (int col = 0; col < colSize; col ++) {
                Cell wall = new Cell(CellValueType.WALL, false, row, col);
                board.setCell(row, col, wall);
            }
        }
    }

    private void generateRandomizedPrimMaze() {
        fillBoardWillWalls();
        board.setCellVisited(1, 1, true);
        List<Cell> wallList = new ArrayList<>();
        List<Cell> walls = getAllNotVisitedValidWallsOfCell(1, 1);
        wallList.addAll(walls);
        while (wallList.size() != 0) {
            System.out.println();
            long seed = System.nanoTime();
            Random rand = new Random(seed);
            int indexRand = rand.nextInt(wallList.size());
            Cell randomWall = wallList.get(indexRand);
            if (isOnlyOneOfTwoCellsBlockedByWallVisited(randomWall)) {
                int row = randomWall.getRowPosition();
                int col = randomWall.getColPosition();
                board.setCellValue(row, col, CellValueType.NOTHING);
                board.setCellVisited(row, col, true);
                List<Cell> neighbourWalls = getAllNotVisitedValidWallsOfCell(row, col);
                wallList.addAll(neighbourWalls);
            }

            wallList.remove(indexRand);
        }
    }

    public boolean isCellMatchedType(int rowPosition, int colPosition, CellValueType valueType) {
        return board.getCellValue(rowPosition, colPosition) == valueType;
    }

    public boolean isOpenOrWallSquareExisted() {
        for (int row = 1; row < rowSize - 2; row ++) {
            for (int col = 1; col < colSize -2; col ++) {
                CellValueType nothing = CellValueType.NOTHING;
                boolean isOpenSquare = isCellMatchedType(row, col, nothing) &&
                            isCellMatchedType(row + 1, col, nothing) &&
                            isCellMatchedType(row, col + 1, nothing) &&
                            isCellMatchedType(row + 1, col + 1, nothing);
                CellValueType wall = CellValueType.WALL;
                boolean isWallSquare = isCellMatchedType(row, col, wall) &&
                            isCellMatchedType(row + 1, col, wall) &&
                            isCellMatchedType(row, col + 1, wall) &&
                            isCellMatchedType(row + 1, col + 1, wall);
                if (isOpenSquare || isWallSquare) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isSurroundedByWalls(int rowPosition, int colPosition) {
        CellValueType wall = CellValueType.WALL;
        boolean isUpWall = isCellMatchedType(rowPosition - 1, colPosition, wall);
        boolean isDownWall = isCellMatchedType(rowPosition + 1, colPosition, wall);
        boolean isLeftWall = isCellMatchedType(rowPosition, colPosition - 1, wall);
        boolean isRightWall = isCellMatchedType(rowPosition, colPosition + 1, wall);

        return (isUpWall && isDownWall && isLeftWall && isRightWall);
    }

    private void addMazeRequirement(int numOfRemovedWall) {
        board.setCellValue(1, 1, CellValueType.NOTHING);
        board.setCellValue(1, colSize - 2, CellValueType.NOTHING);
        board.setCellValue(rowSize - 2, 1, CellValueType.NOTHING);
        board.setCellValue(rowSize - 2, colSize - 2, CellValueType.NOTHING);

        while (numOfRemovedWall > 0) {
            long seed = System.nanoTime();
            Random rand = new Random(seed);
            int rowRand = rand.nextInt(rowSize - 2) + 1;
            int colRand = rand.nextInt(colSize - 2) + 1;
            boolean isWall = isCellMatchedType(rowRand, colRand, CellValueType.WALL);
            boolean isSurroundedByWalls = isSurroundedByWalls(rowRand, colRand);
            boolean isNoSquareCreated = isNoOpenSquareCreated(rowRand, colRand);
            if (isWall && isNoSquareCreated && !isSurroundedByWalls) {
                board.setCellValue(rowRand, colRand, CellValueType.NOTHING);
                numOfRemovedWall --;
            }
        }
    }

    private boolean isNoOpenSquareCreated(int rowPosition, int colPosition) {
        CellValueType nothing = CellValueType.NOTHING;
        boolean isLeftUpSquare = isCellMatchedType(rowPosition, colPosition - 1, nothing) &&
                    isCellMatchedType(rowPosition - 1, colPosition - 1, nothing) &&
                    isCellMatchedType(rowPosition - 1, colPosition, nothing);
        boolean isLeftDownSquare = isCellMatchedType(rowPosition, colPosition - 1, nothing) &&
                    isCellMatchedType(rowPosition + 1, colPosition - 1, nothing) &&
                    isCellMatchedType(rowPosition + 1, colPosition, nothing);
        boolean isRightUpSquare = isCellMatchedType(rowPosition, colPosition + 1, nothing) &&
                    isCellMatchedType(rowPosition - 1, colPosition + 1, nothing) &&
                    isCellMatchedType(rowPosition - 1, colPosition, nothing);
        boolean isRightDownSquare = isCellMatchedType(rowPosition, colPosition + 1, nothing) &&
                    isCellMatchedType(rowPosition + 1, colPosition + 1, nothing) &&
                    isCellMatchedType(rowPosition + 1, colPosition, nothing);

        if (isLeftUpSquare || isLeftDownSquare || isRightUpSquare || isRightDownSquare) {
            return false;
        }

        return true;
    }

    private boolean isBothSideOfCellVisited(int row1, int col1, int row2, int col2) {
        boolean bothVisited = (board.getCellVisited(row1, col1) && board.getCellVisited(row2, col2));
        if (bothVisited) {
            return true;
        }
        return false;
    }

    private boolean isOnlyOneSideOfCellVisited(int row1, int col1, int row2, int col2) {
        if (isBothSideOfCellVisited(row1, col1, row2, col2)) {
            return false;
        }
        boolean isOnlyLeftVisited = (board.getCellVisited(row1, col1) && !board.getCellVisited(row2, col2));
        boolean isOnlyRightVisited = (!board.getCellVisited(row1, col1) && board.getCellVisited(row2, col2));
        if (isOnlyLeftVisited || isOnlyRightVisited) {
            return true;
        }
        return false;
    }

    private boolean isOnlyOneOfTwoCellsBlockedByWallVisited(Cell wall) {
        int row = wall.getRowPosition();
        int col = wall.getColPosition();
        int counter = 0;
        if (isCellValid(row, col - 1) && isCellValid(row, col + 1)) {
            if (isBothSideOfCellVisited(row, col - 1, row, col + 1)) {
                return false;
            }
            if (isOnlyOneSideOfCellVisited(row, col - 1, row, col + 1)) {
                counter ++;
            }
        }
        if (isCellValid(row - 1, col) && isCellValid(row + 1, col)) {
            if (isBothSideOfCellVisited(row - 1, col, row + 1, col)) {
                return false;
            }
            if (isOnlyOneSideOfCellVisited(row - 1, col, row + 1, col)) {
                counter ++;
            }
        }
        if (counter == 1) {
            return true;
        }

        return false;
    }

    private Cell getNotVisitedValidWall(int rowPosition, int colPosition) {
        if (isCellValid(rowPosition, colPosition)) {
            boolean isNotVisitedCell = (board.getCellVisited(rowPosition, colPosition) == false);
            if (isNotVisitedCell) {
                Cell wall = board.getCell(rowPosition, colPosition);
                return wall;
            }
        }

        return null;
    }

    private List<Cell> getAllNotVisitedValidWallsOfCell(int rowPosition, int colPosition) {
        List<Cell> validWalls = new ArrayList<>();
        Cell upWall = getNotVisitedValidWall(rowPosition - 1, colPosition);
        if (upWall != null) {
            validWalls.add(upWall);
        }
        Cell downWall = getNotVisitedValidWall(rowPosition + 1, colPosition);
        if (downWall != null) {
            validWalls.add(downWall);
        }
        Cell leftWall = getNotVisitedValidWall(rowPosition, colPosition - 1);
        if (leftWall != null) {
            validWalls.add(leftWall);
        }
        Cell rightWall = getNotVisitedValidWall(rowPosition, colPosition + 1);
        if (rightWall != null) {
            validWalls.add(rightWall);
        }

        return validWalls;
    }

    private boolean isCellValid(int rowPosition, int colPosition) {
        int leftLimit = 0;
        int rightLimit = colSize - 1;
        int upLimit = 0;
        int downLimit = rowSize - 1;
        boolean isRowValid = (rowPosition >= upLimit && rowPosition <= downLimit);
        boolean isColValid = (colPosition >= leftLimit && colPosition <= rightLimit);

        return (isRowValid && isColValid);
    }

    public boolean isInnerCellValid(int rowPosition, int colPosition) {
        int leftLimit = 1;
        int rightLimit = colSize - 2;
        int upLimit = 1;
        int downLimit = rowSize - 2;
        boolean isRowValid = (rowPosition >= upLimit && rowPosition <= downLimit);
        boolean isColValid = (colPosition >= leftLimit && colPosition <= rightLimit);

        return (isRowValid && isColValid);
    }
}
