package ca.cmpt213.as5.model;

import java.util.*;

/**
 * Represent a course section manager to hold a list of course sections
 */
public class CourseSectionManager implements Iterable<CourseSection>{
    private List<CourseSection> sections = new ArrayList<>();

    public void add(CourseSection section) {
        if (!isSectionExisted(section)) {
            sections.add(section);
        } else {
            for (CourseSection courseSection : sections) {
                if (courseSection.equals(section)) {
                    int capacity = section.getEnrolment().getCapacity();
                    int enrolledNumber = section.getEnrolment().getEnrolledNumber();
                    courseSection.addEnrolment(capacity, enrolledNumber);
                }
            }
        }
        sortSections();
    }

    public List<CourseSection> getSections() {
        return sections;
    }

    public boolean isSectionExisted(CourseSection section) {
        for (CourseSection courseSection : sections) {
            if (courseSection.equals(section)) {
                return true;
            }
        }
        return false;
    }

    public void sortSections() {
        Collections.sort(sections);
    }

    @Override
    public Iterator<CourseSection> iterator() {
        return sections.iterator();
    }
}
