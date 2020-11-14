package ca.cmpt213.as4.restapi;

import ca.cmpt213.as4.model.Game;

import java.util.List;

public class ApiBoardWrapper {
    public int boardWidth;
    public int boardHeight;
    public ApiLocationWrapper mouseLocation;
    public ApiLocationWrapper cheeseLocation;
    public List<ApiLocationWrapper> catLocations;
    public boolean[][] hasWalls;
    public boolean[][] isVisible;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiBoardWrapper makeFromGame(Game game) {
        ApiBoardWrapper wrapper = new ApiBoardWrapper();

        // Populate this object!
        wrapper.boardWidth = game.getMaze().getBoard().getColSize();
        wrapper.boardHeight = game.getMaze().getBoard().getRowSize();
        wrapper.mouseLocation = ApiLocationWrapper.makeFromCellLocation(game.getMouseLocation());
        wrapper.cheeseLocation = ApiLocationWrapper.makeFromCellLocation(game.getCheeseLocation());
        wrapper.catLocations = ApiLocationWrapper.makeFromCellLocations(game.getCatsLocation());
        wrapper.hasWalls = game.getWallsGrid();
        wrapper.isVisible = game.getVisibleGrid();

        return wrapper;
    }
}
