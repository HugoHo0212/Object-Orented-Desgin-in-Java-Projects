package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represent a data point manager to hold a list of data points
 */
public class DataPointManager {
    private List<DataPoint> dataPoints = new ArrayList<>();

    public void addDataPoint(DataPoint dataPoint, int numOfCourseTaken) {
        if (!isDataPointExisted(dataPoint)) {
            dataPoints.add(dataPoint);
            dataPoint.addNumOfCoursesTaken(numOfCourseTaken);
        } else {
            for (DataPoint dp : dataPoints) {
                if (dp.getSemesterCode() == dataPoint.getSemesterCode()) {
                    dp.addNumOfCoursesTaken(numOfCourseTaken);
                }
            }
        }
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void sort() {
        Collections.sort(dataPoints);
    }

    public boolean isDataPointExisted(DataPoint dataPoint) {
        for (DataPoint dp : dataPoints) {
            if (dp.getSemesterCode() == dataPoint.getSemesterCode()) {
                return true;
            }
        }
        return false;
    }
}
