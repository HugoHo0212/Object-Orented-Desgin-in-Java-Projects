package ca.cmpt213.as5.model;

/**
 * Represent a course section's enrolment and type
 */
public class CourseSection implements Comparable<CourseSection>{
    private Enrolment enrolment;
    private String type;

    public CourseSection(Enrolment enrolment, String type) {
        this.enrolment = enrolment;
        this.type = type;
    }

    public Enrolment getEnrolment() {
        return enrolment;
    }

    public int getEnrollmentTotal() {
        return enrolment.getEnrolledNumber();
    }

    public int getEnrollmentCap() {
        return enrolment.getCapacity();
    }

    public String getType() {
        return type;
    }

    public void addEnrolment(int capacity, int enrolledNumber) {
        enrolment.addCapacity(capacity);
        enrolment.addEnrolledNumber(enrolledNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof CourseSection)) {
            return false;
        }

        CourseSection section = (CourseSection) obj;

        boolean isEqual = type.equals(section.getType());
        return isEqual;
    }

    @Override
    public int compareTo(CourseSection o) {
        return type.compareTo(o.getType());
    }
}
