package ca.cmpt213.as5.model;

/**
 * Represent a enrolment's capacity and enrolled number
 */
public class Enrolment {
    private int capacity;
    private int enrolledNumber;

    public Enrolment(int capacity, int enrolledNumber) {
        this.capacity = capacity;
        this.enrolledNumber = enrolledNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledNumber() {
        return enrolledNumber;
    }

    public void addCapacity(int capacity) {
        this.capacity += capacity;
    }

    public void addEnrolledNumber(int enrolledNumber) {
        this.enrolledNumber += enrolledNumber;
    }
}
