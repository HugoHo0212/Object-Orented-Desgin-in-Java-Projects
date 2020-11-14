package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represent a course manager to hold a list of courses
 */
public class CourseManager implements Iterable<Course>{
    private List<Course> courses = new ArrayList<>();

    public void add(Course course, CourseOffering offering, CourseSection section) {
        if (!isCourseExisted(course.getCatalogNumber())) {
            course.setCourseId(courses.size());
            courses.add(course);
            course.addOffering(offering, section);
            sortCourse();
        } else {
            Course c = getCourseByCatalog(course.getCatalogNumber());
            c.addOffering(offering, section);
        }
    }

    public Course getCourseByCatalog(String catalogNumber) {
        for (Course course : courses) {
            if (course.getCatalogNumber().equals(catalogNumber)) {
                return course;
            }
        }
        return null;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Course getCourseById(long courseId) {
        for (Course course : courses) {
            if (course.getCourseId() == courseId) {
                return course;
            }
        }
        return null;
    }

    public boolean isCourseExisted(String catalogNumber) {
        for (Course course : courses) {
            if (course.getCatalogNumber().equals(catalogNumber)) {
                return true;
            }
        }
        return false;
    }

    public void sortCourse() {
        Collections.sort(courses);
    }

    @Override
    public Iterator<Course> iterator() {
        return courses.iterator();
    }
}
