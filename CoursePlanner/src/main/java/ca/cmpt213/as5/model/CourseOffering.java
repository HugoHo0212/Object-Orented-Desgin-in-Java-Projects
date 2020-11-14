package ca.cmpt213.as5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represent a course offering's semester, location, instructor name and section manager
 */
public class CourseOffering implements Comparable<CourseOffering>{
    private long courseOfferingId;
    private Semester semester;
    private String location;
    private String instructors;
    private CourseSectionManager courseSectionManager;

    public CourseOffering(String semesterNumber, String location, String instructors) {
        this.semester = new Semester(semesterNumber);
        this.location = location;
        this.instructors = instructors;
        this.courseSectionManager = new CourseSectionManager();
    }

    public void setCourseOfferingId(long courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public long getCourseOfferingId() {
        return courseOfferingId;
    }

    public void addSection(CourseSection section) {
        courseSectionManager.add(section);
    }

    @JsonIgnore
    public Semester getSemester() {
        return semester;
    }

    public int getSemesterCode() {
        return semester.getSemesterCode();
    }

    public int getYear() {
        return semester.getYear();
    }

    public String getTerm() {
        return semester.getTerm();
    }

    public String getLocation() {
        return location;
    }

    public String getInstructors() {
        return instructors;
    }

    @JsonIgnore
    public CourseSectionManager getCourseSectionManager() {
        return courseSectionManager;
    }

    public void setInstructors(String instructors) {
        this.instructors = instructors;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        CourseOffering offering = (CourseOffering) obj;

        boolean isEqual = this.semester.equals(offering.semester) &&
                this.location.equals(offering.location);
        return isEqual;
    }

    @Override
    public int compareTo(CourseOffering o) {
        return (semester.getSemesterNumber() + this.location).compareTo(o.semester.getSemesterNumber() + o.location);
    }
}
