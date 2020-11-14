package ca.cmpt213.as5.model;

/**
 * Represent a data point's semester code and the number of total courses taken
 */
public class DataPoint implements Comparable<DataPoint>{
    private int semesterCode;
    private int totalCoursesTaken;

    public DataPoint(int semesterCode) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = 0;
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public int getTotalCoursesTaken() {
        return totalCoursesTaken;
    }

    public void addNumOfCoursesTaken(int num) {
        totalCoursesTaken += num;
    }

    @Override
    public int compareTo(DataPoint o) {
        return semesterCode - o.semesterCode;
    }
}
