package ca.cmpt213.as5.model;

/**
 * Represent an observer to be notified
 */
public interface Observer {
    void stateChange(CourseOffering offering, CourseSection section);
}
