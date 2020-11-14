package ca.cmpt213.as4.restapi;

import ca.cmpt213.as4.model.Game;

public class ApiGameWrapper {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int numCheeseFound;
    public int numCheeseGoal;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiGameWrapper makeFromGame(Game game, int id) {
        ApiGameWrapper wrapper = new ApiGameWrapper();
        wrapper.gameNumber = id;
        // Populate this object!
        wrapper.isGameWon = game.hasUserWon();
        wrapper.isGameLost = game.hasUserLost();
        wrapper.numCheeseFound = game.getNumCheeseCollected();
        wrapper.numCheeseGoal = game.getNumCheeseToCollect();
        return wrapper;
    }
}
