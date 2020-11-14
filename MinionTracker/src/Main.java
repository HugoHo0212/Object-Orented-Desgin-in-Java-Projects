import ca.minion.model.MinionManager;
import ca.minion.ui.Menu;

/**
 * The main application to run the program
 */
public class Main {
    public static void main(String args[]){
        MinionManager manager = new MinionManager();

        Menu textMenu = new Menu("Main Menu", manager);

        textMenu.showMenu();
    }
}
