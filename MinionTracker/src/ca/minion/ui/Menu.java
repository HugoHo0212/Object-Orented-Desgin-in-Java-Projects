package ca.minion.ui;

import ca.minion.model.Minion;
import ca.minion.model.MinionManager;

import java.util.Collections;
import java.util.Scanner;

/**
 * Represent a text menus
 */
public class Menu {
    private String title;
    private String[] options;
    private MinionManager manager;
    public static final String CANCELLATION_WORDS = "(Enter 0 to cancel)";

    public Menu(String title, MinionManager manager) {
        this.title = title;
        this.manager = manager;
        options = new String[]{"List minions",
                               "Add a new minion",
                               "Remove a minion",
                               "Attribute evil deed to a minion",
                               "DEBUG: Dump objects (toString)",
                               "Exit"};
    }

    public String createStars(int length) {
        return String.join("", Collections.nCopies(length, "*"));
    }

    public boolean isEmptyMinionList() {
        if (manager.getMinionsLen() == 0) {
            System.out.println("No minions found.");
            return true;
        }
        return false;
    }

    public void loopMinions(boolean isList) {
        if (!isEmptyMinionList()){
            if (!isList) {
                System.out.println("All minion objects:");
            }
            int count = 1;
            for (Minion m : manager) {
                if (isList){
                    System.out.println(count + ". " + m.getInfo());
                } else {
                    System.out.println(count + ". " + m);
                }
                count ++;
            }
        }
        System.out.println("");
    }

    public void showMinionList() {
        String str = "List of Minions:";
        String stars = createStars(str.length());
        System.out.println(str + "\n" + stars);
        boolean isList = true;
        loopMinions(isList);
    }

    public void showMinionObjects() {
        boolean isList = false;
        loopMinions(isList);
    }

    public int askUserForSelection(int min, int max) {
        System.out.print("> ");
        Scanner input = new Scanner(System.in);
        int selection = input.nextInt();
        while (selection < min || selection > max) {
            System.out.println("Error: Please enter a selection between " + min + " and " + max);
            System.out.print("> ");
            selection = input.nextInt();
        }
        return selection;
    }

    public Minion inputToCreateMinion() {
        Scanner s = new Scanner(System.in);
        boolean isNameDone = false;
        boolean isHeightDone = false;
        String name = null;
        double height = 0.0;
        while (!isNameDone) {
            System.out.print("Enter minion's name:   ");
            name = s.nextLine();
            if (name != null && !name.equals("")){
                isNameDone = true;
            } else {
                System.out.println("Please enter a correct name ");
            }
        }
        while (!isHeightDone){
            System.out.print("Enter minion's height: ");
            height = s.nextDouble();
            if (height >= 0){
                isHeightDone = true;
            } else {
                System.out.println("ERROR: Height must be >= 0.");
            }
        }

        Minion m = new Minion(name, height);
        return m;
    }

    public void removeOrAttributeMinion(boolean isRemove) {
        showMinionList();
        System.out.println(CANCELLATION_WORDS);
        int choice = askUserForSelection(0, manager.getMinionsLen());

        if (isRemove && choice != 0) {
            int index = choice - 1;
            manager.remove(index);
        } else if (!isRemove && choice != 0) {
            int index = choice - 1;
            manager.incEvilDeedAtIndex(index);
            Minion m = manager.getMinionAtIndex(index);
            System.out.println(m.getName() + " has now down " + m.getNumOfEvilDeeds() + " evil deed(s)!" );
        }
        System.out.println("");
    }

    public void showMenu() {
        String welcomeWords = "Welcome to the Evil Minion Tracker (tm)";
        String stars = createStars(welcomeWords.length()) + "\n";
        welcomeWords = stars +
                    welcomeWords +
                    "\n" +
                    "by Your Name Here.\n" +
                    stars;

        System.out.println(welcomeWords);

        boolean isDone = false;
        String titleWithStars = "* " + title + " *";
        stars = createStars(titleWithStars.length());
        String starsRect = stars + "\n" + titleWithStars + "\n" + stars;

        while (!isDone) {
            System.out.println(starsRect);
            int i = 1;
            for (String opt : options) {
                System.out.println(i + ". " + opt);
                i ++;
            }

            int choice = askUserForSelection(1, 6);

            switch (choice) {
                case 1:
                    showMinionList();
                    break;
                case 2:
                    Minion minion = inputToCreateMinion();
                    manager.add(minion);
                    System.out.println();
                    break;
                case 3:
                    removeOrAttributeMinion(true);
                    break;
                case 4:
                    removeOrAttributeMinion(false);
                    break;
                case 5:
                    showMinionObjects();
                    break;
                case 6:
                    isDone = true;
                    break;
            }
        }
    }
}
