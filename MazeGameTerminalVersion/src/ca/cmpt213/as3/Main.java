package ca.cmpt213.as3;

import ca.cmpt213.as3.model.Game;
import ca.cmpt213.as3.ui.TextUI;

/**
 * Represent the main application to launch the program
 */
public class Main {
    public static void main(String[] args){
        Game game = new Game(15, 20);
        TextUI ui = new TextUI(game, 5);
        ui.showMenu();
    }
}
