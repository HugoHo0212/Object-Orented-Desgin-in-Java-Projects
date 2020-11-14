package ca.cmpt213.as3.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Represent a game's element, a mouse, a cat manager, a cheese, a maze and a map holding the previous position of cats
 */
public class Game {
    private CatManager catManager;
    private Mouse mouse;
    private Maze maze;
    private Cheese cheese;
    private HashMap<Cat, int[]> catsOldPositionMap = new HashMap<>();
    private int numCheeseFound = 0;
    private int numCheeseGoal = 5;

    public Game(int rowSize, int colSize) {
        this.catManager = new CatManager();
        this.mouse = new Mouse(1, 1);
        this.maze = new Maze(rowSize, colSize);
        Cat cat1 = new Cat(1, colSize - 2);
        Cat cat2 = new Cat(rowSize - 2, 1);
        Cat cat3 = new Cat(rowSize - 2, colSize - 2);
        catsOldPositionMap.put(cat1, new int[]{1, colSize - 2});
        catsOldPositionMap.put(cat2, new int[]{rowSize - 2, 1});
        catsOldPositionMap.put(cat3, new int[]{rowSize - 2, colSize - 2});
        catManager.add(cat1);
        catManager.add(cat2);
        catManager.add(cat3);
        cheese = new Cheese(-1, -1);
        setOuterWallsVisible();
        insertGameElementsToMaze();
        revealCellsAroundMouse();
    }

    public void setNumCheeseGoal(int numCheeseGoal) {
        this.numCheeseGoal = numCheeseGoal;
    }

    public boolean hasUserWon() {
        return numCheeseFound >= numCheeseGoal;
    }

    public void increaseCollectedCheese() {
        numCheeseFound += 1;
    }

    public boolean hasUserLost() {
        return isMouseEaten();
    }

    public int getNumCheeseCollected() {
        return numCheeseFound;
    }

    public int getNumCheeseToCollect() {
        return numCheeseGoal;
    }

    public Cell getMouseLocation() {
        Cell mouseCell = maze.getBoard().getCell(mouse.getRowPosition(), mouse.getColPosition());
        return mouseCell;
    }

    public Cell getCheeseLocation() {
        Cell cheeseCell = maze.getBoard().getCell(cheese.getRowPosition(), cheese.getColPosition());
        return cheeseCell;
    }

    public List<Cell> getCatsLocation() {
        List<Cell> catsLocation = new ArrayList<>();
        for (Cat cat : catManager) {
            Cell catCell = maze.getBoard().getCell(cat.getRowPosition(), cat.getColPosition());
            catsLocation.add(catCell);
        }
        return catsLocation;
    }

    public boolean[][] getWallsGrid() {
        Board board = maze.getBoard();
        boolean[][] wallsGrid = new boolean[board.getRowSize()][board.getColSize()];
        for (int row = 0; row < board.getRowSize(); row ++) {
            for (int col = 0; col < board.getColSize(); col ++) {
                if (board.getCellValue(row, col) == CellValueType.WALL) {
                    wallsGrid[row][col] = true;
                } else {
                    wallsGrid[row][col] = false;
                }
            }
        }
        return wallsGrid;
    }

    public boolean[][] getVisibleGrid() {
        Board board = maze.getBoard();
        boolean[][] visibleGrid = new boolean[board.getRowSize()][board.getColSize()];
        for (int row = 0; row < board.getRowSize(); row ++) {
            for (int col = 0; col < board.getColSize(); col ++) {
                if (board.getCellVisible(row, col)) {
                    visibleGrid[row][col] = true;
                } else {
                    visibleGrid[row][col] = false;
                }
            }
        }
        return visibleGrid;
    }

    public boolean isMouseLeftLegal() {
        int row = mouse.getRowPosition();
        int col = mouse.getColPosition() - 1;
        return isLegalMove(row, col);
    }

    public boolean isMouseRightLegal() {
        int row = mouse.getRowPosition();
        int col = mouse.getColPosition() + 1;
        return isLegalMove(row, col);
    }

    public boolean isMouseUpLegal() {
        int row = mouse.getRowPosition() - 1;
        int col = mouse.getColPosition();
        return isLegalMove(row, col);
    }

    public boolean isMouseDownLegal() {
        int row = mouse.getRowPosition() + 1;
        int col = mouse.getColPosition();
        return isLegalMove(row, col);
    }

    public Cheese getCheese() {
        return cheese;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Maze getMaze() {
        return maze;
    }

    public void moveCatsRandomly() {
        for (Cat cat : catManager) {
            int catRow = cat.getRowPosition();
            int catCol = cat.getColPosition();
            int oldRow = catsOldPositionMap.get(cat)[0];
            int oldCol = catsOldPositionMap.get(cat)[1];
            List<Direction> directionList;
            directionList = getPassedDirectionsOfCat(cat);
            if (directionList.size() > 0) {
                if (directionList.size() == 1) {
                    Direction dir = directionList.get(0);
                    moveCatToDirection(cat, dir);
                } else {
                    boolean isDone = false;
                    while (!isDone) {
                        long seed = System.nanoTime();
                        Random rand = new Random(seed);
                        int indexRand = rand.nextInt(directionList.size());
                        Direction dir = directionList.get(indexRand);
                        if (!isCatBacktracking(cat, dir)) {
                            moveCatToDirection(cat, dir);
                            isDone = true;
                        }
                    }
                }
                if (!(catRow == oldRow && catCol == oldCol)) {
                    catsOldPositionMap.put(cat, new int[]{catRow, catCol});
                }
            }
        }
    }

    public boolean isCatBacktracking(Cat cat, Direction dir) {
        int catRow = cat.getRowPosition();
        int catCol = cat.getColPosition();
        int oldRow = catsOldPositionMap.get(cat)[0];
        int oldCol = catsOldPositionMap.get(cat)[1];
        if (dir == Direction.LEFT) {
            if (catRow == oldRow && catCol - 1 == oldCol) {
                return true;
            }
        } else if (dir == Direction.RIGHT) {
            if (catRow == oldRow && catCol + 1 == oldCol) {
                return true;
            }
        } else if (dir == Direction.UP) {
            if (catRow - 1 == oldRow && catCol == oldCol) {
                return true;
            }
        } else if (dir == Direction.DOWN) {
            if (catRow + 1 == oldRow && catCol == oldCol) {
                return true;
            }
        }

        return false;
    }

    public void moveCatToDirection(Cat cat, Direction dir) {
        if (dir == Direction.LEFT) {
            moveCatLeft(cat);
        } else if (dir == Direction.RIGHT) {
            moveCatRight(cat);
        } else if (dir == Direction.UP) {
            moveCatUp(cat);
        } else if (dir == Direction.DOWN) {
            moveCatDown(cat);
        }
    }

    public List<Direction> getPassedDirectionsOfCat(Cat cat) {
        int catRow = cat.getRowPosition();
        int catCol = cat.getColPosition();
        List<Direction> directionList = new ArrayList<>();
        CellValueType wall = CellValueType.WALL;
        boolean isLeftWall = maze.isCellMatchedType(catRow, catCol - 1, wall);
        boolean isRightWall = maze.isCellMatchedType(catRow, catCol + 1, wall);
        boolean isUpWall = maze.isCellMatchedType(catRow - 1, catCol, wall);
        boolean isDownWall = maze.isCellMatchedType(catRow + 1, catCol, wall);
        if (!isLeftWall) {
            directionList.add(Direction.LEFT);
        }
        if (!isRightWall) {
            directionList.add(Direction.RIGHT);
        }
        if (!isUpWall) {
            directionList.add(Direction.UP);
        }
        if (!isDownWall) {
            directionList.add(Direction.DOWN);
        }
        return directionList;
    }

    public void moveCatOnBoard(Cat cat, int rowPosition, int colPosition) {
        int catRow = cat.getRowPosition();
        int catCol = cat.getColPosition();
        if (maze.getBoard().getCellValue(catRow, catCol) == CellValueType.CAT_AND_CHEESE) {
            maze.getBoard().setCellValue(catRow, catCol, CellValueType.CHEESE);
        } else {
            maze.getBoard().setCellValue(catRow, catCol, CellValueType.NOTHING);
        }
        if (maze.getBoard().getCellValue(rowPosition, colPosition) == CellValueType.CHEESE) {
            maze.getBoard().setCellValue(rowPosition, colPosition, CellValueType.CAT_AND_CHEESE);
        } else {
            maze.getBoard().setCellValue(rowPosition, colPosition, CellValueType.CAT);
        }
    }

    public void moveCatLeft(Cat cat) {
        int catRow = cat.getRowPosition();
        int catCol = cat.getColPosition();

        moveCatOnBoard(cat, catRow, catCol - 1);
        cat.moveLeft();
    }

    public void moveCatRight(Cat cat) {
        int catRow = cat.getRowPosition();
        int catCol = cat.getColPosition();

        moveCatOnBoard(cat, catRow, catCol + 1);
        cat.moveRight();
    }

    public void moveCatUp(Cat cat) {
        int catRow = cat.getRowPosition();
        int catCol = cat.getColPosition();

        moveCatOnBoard(cat, catRow - 1, catCol);
        cat.moveUp();
    }

    public void moveCatDown(Cat cat) {
        int catRow = cat.getRowPosition();
        int catCol = cat.getColPosition();

        moveCatOnBoard(cat, catRow + 1, catCol);
        cat.moveDown();
    }

    public boolean isMouseEaten() {
        int mouseRow = mouse.getRowPosition();
        int mouseCol = mouse.getColPosition();
        for (Cat cat : catManager) {
            int catRow = cat.getRowPosition();
            int catCol = cat.getColPosition();
            if (mouseRow == catRow && mouseCol == catCol) {
                maze.getBoard().setCellValue(mouseRow, mouseCol, CellValueType.EATEN_MOUSE);
                return true;
            }
        }
        return false;
    }

    public boolean isCheeseEaten() {
        int mouseRow = mouse.getRowPosition();
        int mouseCol = mouse.getColPosition();
        int cheeseRow = cheese.getRowPosition();
        int cheeseCol = cheese.getColPosition();

        return (mouseRow == cheeseRow && mouseCol == cheeseCol);
    }

    public void moveMouseLeft() {
        int mouseRow = mouse.getRowPosition();
        int mouseCol = mouse.getColPosition();
        maze.getBoard().setCellValue(mouseRow, mouseCol, CellValueType.NOTHING);
        maze.getBoard().setCellValue(mouseRow, mouseCol - 1, CellValueType.MOUSE);
        mouse.moveLeft();
    }

    public void moveMouseRight() {
        int mouseRow = mouse.getRowPosition();
        int mouseCol = mouse.getColPosition();
        maze.getBoard().setCellValue(mouseRow, mouseCol, CellValueType.NOTHING);
        maze.getBoard().setCellValue(mouseRow, mouseCol + 1, CellValueType.MOUSE);
        mouse.moveRight();
    }

    public void moveMouseUp() {
        int mouseRow = mouse.getRowPosition();
        int mouseCol = mouse.getColPosition();
        maze.getBoard().setCellValue(mouseRow, mouseCol, CellValueType.NOTHING);
        maze.getBoard().setCellValue(mouseRow - 1, mouseCol, CellValueType.MOUSE);
        mouse.moveUp();
    }

    public void moveMouseDown() {
        int mouseRow = mouse.getRowPosition();
        int mouseCol = mouse.getColPosition();
        maze.getBoard().setCellValue(mouseRow, mouseCol, CellValueType.NOTHING);
        maze.getBoard().setCellValue(mouseRow + 1, mouseCol, CellValueType.MOUSE);
        mouse.moveDown();
    }

    public boolean isLegalMove(int rowPosition, int colPosition) {
        CellValueType valueType = maze.getBoard().getCellValue(rowPosition, colPosition);

        return valueType != CellValueType.WALL;
    }

    public void revealCellsAroundMouse() {
        int row = mouse.getRowPosition();
        int col = mouse.getColPosition();
        for (int i = row - 1; i <= row + 1; i ++) {
            for (int j = col - 1; j <= col + 1; j ++) {
                if (maze.isInnerCellValid(i, j)) {
                    setMazeCellVisible(i, j, true);
                }
            }
        }
    }

    public void revealMaze() {
        Board board = maze.getBoard();
        for (int row = 0; row < board.getRowSize(); row ++) {
            for (int col = 0; col < board.getColSize(); col ++) {
                board.setCellVisible(row, col, true);
            }
            System.out.println();
        }
    }

    public void generateCheese() {
        boolean isDone = false;
        while (!isDone){
            long seed = System.nanoTime();
            Random rand = new Random(seed);
            int rowRand = rand.nextInt(maze.getBoard().getRowSize() - 2) + 1;
            int colRand = rand.nextInt(maze.getBoard().getColSize() - 2) + 1;
            CellValueType valueType = maze.getBoard().getCellValue(rowRand, colRand);
            if (valueType == CellValueType.NOTHING) {
                maze.getBoard().setCellValue(rowRand, colRand, CellValueType.CHEESE);
//                maze.setBoardCellVisible(rowRand, colRand, true);
                cheese.setRowPosition(rowRand);
                cheese.setColPosition(colRand);
                isDone = true;
            } else if (valueType == CellValueType.CAT) {
                maze.getBoard().setCellValue(rowRand, colRand, CellValueType.CAT_AND_CHEESE);
//                maze.setBoardCellVisible(rowRand, colRand, true);
                cheese.setRowPosition(rowRand);
                cheese.setColPosition(colRand);
                isDone = true;
            }
        }
    }

    public void insertGameElementsToMaze() {
        maze.getBoard().setCellValue(mouse.getRowPosition(), mouse.getColPosition(), CellValueType.MOUSE);
        for (Cat cat : catManager) {
            maze.getBoard().setCellValue(cat.getRowPosition(), cat.getColPosition(), CellValueType.CAT);
        }

        generateCheese();
    }

    public void setMazeCellVisible(int rowPosition, int colPosition, boolean isVisible) {
        maze.setBoardCellVisible(rowPosition, colPosition, isVisible);
    }

    public boolean getMazeCellVisible(int rowPosition, int colPosition) {
        return maze.getBoardCellVisible(rowPosition, colPosition);
    }

    public void setOuterWallsVisible() {
        int rowSize = maze.getBoard().getRowSize();
        int colSize = maze.getBoard().getColSize();
        for (int row = 0; row < rowSize; row ++) {
            for (int col = 0; col < colSize; col ++) {
                if (row == 0 || row == rowSize - 1) {
                    setMazeCellVisible(row, col, true);
                } else if (col == 0 || col == colSize - 1) {
                    setMazeCellVisible(row, col, true);
                }
            }
        }
    }
}

enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN
}