package ca.cmpt213.as3.ui;

import ca.cmpt213.as3.model.*;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Represent a text ui for the game
 */
public class TextUI {
    private Game game;
    private int numOfCheeseToCollect;
    private int numOfCollectedCheese = 0;

    public TextUI(Game game, int numOfCheeseToCollect) {
        this.game = game;
        this.numOfCheeseToCollect = numOfCheeseToCollect;
    }

    public void printWelcomeWords() {
        String welcomeWords = "----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by Yuansheng He\n" +
                "----------------------------------------";
        System.out.println(welcomeWords);
    }

    public void printInstruction() {
        String instruction = "\nDIRECTIONS:\n" +
                "\tFind " + numOfCheeseToCollect + " cheese before a cat eats you!\n" +
                "LEGEND:\n" +
                "\t#: Wall\n" +
                "\t@: You (a mouse)\n" +
                "\t!: Cat\n" +
                "\t$: Cheese\n" +
                "\t.: Unexplored space\n" +
                "MOVES:\n" +
                "\tUse W (up), A (left), S (down) and D (right) to move.\n" +
                "\t(You must press enter after each move).";
        System.out.println(instruction);
    }

    public void printCollectedCheese(int numOfCollectedCheese) {
        System.out.println("Cheese collected: " + numOfCollectedCheese + " of " + numOfCheeseToCollect);
    }

    public void showMenu() {
        printWelcomeWords();
        printInstruction();
        while (numOfCollectedCheese < numOfCheeseToCollect) {
            game.revealCellsAroundMouse();
            printMaze();
            printCollectedCheese(numOfCollectedCheese);
            inputAndMoveMouse();
            if (isCheeseEaten()) {
                game.generateCheese();
                numOfCollectedCheese ++;
            }
            if (game.isMouseEaten()) {
                printMouseGotEaten();
                break;
            }
            if (isWin()) {
                System.out.println("Congratulations! You won!");
                revealMaze();
                printCollectedCheese(numOfCollectedCheese);
                break;
            }
            game.moveCatsRandomly();
            if (game.isMouseEaten()) {
                printMouseGotEaten();
                break;
            }
        }
    }

    public Key askUserToInput() {
        HashMap<String, Key> keyMap = new HashMap<>();
        keyMap.put("w", Key.UP);
        keyMap.put("a", Key.LEFT);
        keyMap.put("s", Key.DOWN);
        keyMap.put("d", Key.RIGHT);
        keyMap.put("m", Key.DISPLAY);
        keyMap.put("c", Key.CHEESE_NUM_TO_ONE);
        keyMap.put("?", Key.HELP);
        boolean isDone = false;
        Key result = null;
        while (!isDone) {
            System.out.print("Enter your move [WASD?]: ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (keyMap.containsKey(input.toLowerCase())) {
                Key key = keyMap.get(input.toLowerCase());
                if (key != Key.DISPLAY && key != Key.CHEESE_NUM_TO_ONE && key != Key.HELP) {
                    result = key;
                    isDone = true;
                } else if (key == Key.DISPLAY) {
                    revealMaze();
                    printCollectedCheese(numOfCollectedCheese);
                } else if (key == Key.HELP) {
                    printInstruction();
                } else if (key == Key.CHEESE_NUM_TO_ONE) {
                    numOfCheeseToCollect = 1;
                }
            } else {
                System.out.println("Invalid move. Please enter just A (left), S (down), D (right), or W (up).");
            }
        }

        return result;
    }

    public void inputAndMoveMouse() {
        boolean isDone = false;
        while (!isDone) {
            Key userInput = askUserToInput();
            int mouseRow = game.getMouse().getRowPosition();
            int mouseCol = game.getMouse().getColPosition();
            if (userInput == Key.LEFT) {
                if (game.isLegalMove(mouseRow, mouseCol - 1)) {
                    game.moveMouseLeft();
                    isDone = true;
                } else {
                    System.out.println("Invalid move: you cannot move through walls!");
                }
            } else if (userInput == Key.RIGHT) {
                if (game.isLegalMove(mouseRow, mouseCol + 1)) {
                    game.moveMouseRight();
                    isDone = true;
                } else {
                    System.out.println("Invalid move: you cannot move through walls!");
                }
            } else if (userInput == Key.UP) {
                if (game.isLegalMove(mouseRow - 1, mouseCol)) {
                    game.moveMouseUp();
                    isDone = true;
                } else {
                    System.out.println("Invalid move: you cannot move through walls!");
                }
            } else if (userInput == Key.DOWN) {
                if (game.isLegalMove(mouseRow + 1, mouseCol)) {
                    game.moveMouseDown();
                    isDone = true;
                } else {
                    System.out.println("Invalid move: you cannot move through walls!");
                }
            }
        }
    }

    public boolean isCheeseEaten() {
        int mouseRow = game.getMouse().getRowPosition();
        int mouseCol = game.getMouse().getColPosition();
        int cheeseRow = game.getCheese().getRowPosition();
        int cheeseCol = game.getCheese().getColPosition();

        return (mouseRow == cheeseRow && mouseCol == cheeseCol);
    }

    public boolean isWin() {
        return numOfCollectedCheese == numOfCheeseToCollect;
    }

    public void printMouseGotEaten() {
        if (game.isMouseEaten()) {
            System.out.println("I'm sorry, you have been eaten!");
            revealMaze();
            printCollectedCheese(numOfCollectedCheese);
            System.out.println("GAME OVER; please try again.");
        }
    }
    public void printCell(CellValueType valueType) {
        if (valueType == CellValueType.WALL) {
            System.out.print("#");
        } else if (valueType == CellValueType.NOTHING) {
            System.out.print(" ");
        } else if (valueType == CellValueType.MOUSE) {
            System.out.print("@");
        } else if (valueType == CellValueType.CHEESE) {
            System.out.print("$");
        } else if (valueType == CellValueType.CAT_AND_CHEESE) {
            System.out.print("!");
        } else if (valueType == CellValueType.EATEN_MOUSE) {
            System.out.print("X");
        } else if (valueType == CellValueType.CAT) {
            System.out.print("!");
        }
    }

    public void revealMaze() {
        System.out.println("\nMaze: ");
        Maze maze = game.getMaze();
        Board board = maze.getBoard();
        for (int row = 0; row < board.getRowSize(); row ++) {
            for (int col = 0; col < board.getColSize(); col ++) {
                CellValueType cellValueType = board.getCell(row, col).getValue();
                printCell(cellValueType);
            }
            System.out.println();
        }
    }

    public void printMaze() {
        System.out.println("\nMaze:");
        Maze maze = game.getMaze();
        Board board = maze.getBoard();
        for (int row = 0; row < board.getRowSize(); row ++) {
            for (int col = 0; col < board.getColSize(); col ++) {
                CellValueType cellValueType = board.getCell(row, col).getValue();
                if (game.getMazeCellVisible(row, col) == true) {
                    printCell(cellValueType);
                } else {
                    if (cellValueType == CellValueType.CAT) {
                        System.out.print("!");
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.println();
        }
    }
}

enum Key{
    LEFT,
    RIGHT,
    UP,
    DOWN,
    DISPLAY,
    CHEESE_NUM_TO_ONE,
    HELP
}