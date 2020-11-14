package ca.cmpt213.as5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represent a department's ID, name, a course manager and a data point manager
 */
public class Department implements Comparable<Department>{
    private long deptId;
    private String name;
    private CourseManager courseManager = new CourseManager();
    private DataPointManager dataPointManager;

    public Department(String name) {
        this.name = name;
    }

    public void addCourse(Course course, CourseOffering offering, CourseSection section) {
        courseManager.add(course, offering, section);
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public long getDeptId() {
        return deptId;
    }

    public String getName() {
        return name;
    }

    public DataPointManager getDataPointManager() {
        dataPointManager = new DataPointManager();
        for (Course course : courseManager) {
            CourseOfferingManager offerings = course.getCourseOfferingManager();
            for (CourseOffering offering : offerings) {
                for (CourseSection section : offering.getCourseSectionManager()) {
                    if (section.getType().equals("LEC")) {
                        int semesterCode = offering.getSemesterCode();
                        int enrollmentTotal = section.getEnrollmentTotal();
                        DataPoint dataPoint = new DataPoint(semesterCode);
                        dataPointManager.addDataPoint(dataPoint, enrollmentTotal);
                    }
                }
            }
        }
        dataPointManager.sort();
        return dataPointManager;
    }

    @JsonIgnore
    public CourseManager getCourseManager() {
        return courseManager;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Department oDepartment = (Department) obj;

        return this.name.equals(oDepartment.name);
    }

    @Override
    public int compareTo(Department o) {
        return (name).compareTo( o.name);
    }

}
