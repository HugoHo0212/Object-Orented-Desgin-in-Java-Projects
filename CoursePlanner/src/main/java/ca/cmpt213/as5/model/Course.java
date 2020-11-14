package ca.cmpt213.as5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a course's subject, number, and course offering manager
 */
public class Course implements Comparable<Course>{
    private long courseId;
    private String catalogNumber;
    private CourseOfferingManager courseOfferingManager = new CourseOfferingManager();
    private List<Observer> observers = new ArrayList<>();

   public Course(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public void addOffering(CourseOffering offering, CourseSection section) {
        courseOfferingManager.add(offering, section);
        notifyObservers(offering, section);
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void addObserver(Observer observer) {
       observers.add(observer);
    }

    public void removeObserver(Observer observer) {
       observers.remove(observer);
    }

    private void notifyObservers(CourseOffering offering, CourseSection section) {
       for (Observer observer : observers) {
           observer.stateChange(offering, section);
       }
    }

    @JsonIgnore
    public CourseOfferingManager getCourseOfferingManager() {
        return courseOfferingManager;
    }

    @Override
    public int compareTo(Course o) {
        return (catalogNumber).compareTo( o.catalogNumber);
    }

}
