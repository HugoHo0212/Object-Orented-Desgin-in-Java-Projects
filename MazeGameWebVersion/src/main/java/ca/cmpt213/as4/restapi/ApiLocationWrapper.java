package ca.cmpt213.as4.restapi;

import ca.cmpt213.as4.model.Cell;

import java.util.ArrayList;
import java.util.List;

public class ApiLocationWrapper {
    public int x;
    public int y;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiLocationWrapper makeFromCellLocation(Cell cell) {
        ApiLocationWrapper location = new ApiLocationWrapper();

        // Populate this object!
        location.y = cell.getRowPosition();
        location.x = cell.getColPosition();

        return location;
    }
    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static List<ApiLocationWrapper> makeFromCellLocations(Iterable<Cell> cells) {
        List<ApiLocationWrapper> locations = new ArrayList<>();

        for (Cell cell : cells) {
            ApiLocationWrapper location = new ApiLocationWrapper();
            location.y = cell.getRowPosition();
            location.x = cell.getColPosition();
            locations.add(location);
        }
        // Populate this object!

        return locations;
    }
}
