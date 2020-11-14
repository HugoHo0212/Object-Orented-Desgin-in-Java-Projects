package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represent a watcher's id, department, course and events
 */
public class Watcher implements Observer{
    private long id;
    private Department department;
    private Course course;
    private List<String> events = new ArrayList<>();

    public Watcher(long id, Department department, Course course) {
        this.id = id;
        this.department = department;
        this.course = course;
    }

    public void addEvent(String event) {
        events.add(event);
    }

    public long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public Course getCourse() {
        return course;
    }

    public List<String> getEvents() {
        return events;
    }

    @Override
    public void stateChange(CourseOffering offering, CourseSection section) {
        Date date = new Date();
        String event = "" + date + ": Added section " + section.getType() + " with enrollment (" +
                section.getEnrollmentTotal() + " / " + section.getEnrollmentCap() + ") to offering " +
                offering.getTerm() + " " + offering.getYear();
        addEvent(event);
    }
}
