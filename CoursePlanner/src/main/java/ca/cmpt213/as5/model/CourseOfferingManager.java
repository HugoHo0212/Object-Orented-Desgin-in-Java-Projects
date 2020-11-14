package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represent a course offering manager to hold a list of course offerings
 */
public class CourseOfferingManager implements Iterable<CourseOffering>{
    private List<CourseOffering> offerings = new ArrayList<>();

    public void add(CourseOffering offering, CourseSection section) {
        if (!isOfferingExisted(offering)) {
            offering.setCourseOfferingId(offerings.size());
            offering.addSection(section);
            offerings.add(offering);
        } else {
            for (CourseOffering courseOffering : offerings) {
                if (courseOffering.equals(offering)) {
                    if (courseOffering.getInstructors().equals("")) {
                        courseOffering.setInstructors(offering.getInstructors().trim());
                    } else if (!courseOffering.getInstructors().contains(offering.getInstructors().trim()) && !offering.getInstructors().equals("")) {
                        String newName = courseOffering.getInstructors() + ", " + offering.getInstructors();
                        courseOffering.setInstructors(newName);
                    }
                    courseOffering.addSection(section);
                }
            }
        }
        sortCourseOffering();
    }

    public List<CourseOffering> getOfferings() {
        return offerings;
    }

    public CourseOffering getOfferingById(long courseOfferingId) {
        for (CourseOffering offering : offerings) {
            if (offering.getCourseOfferingId() == courseOfferingId) {
                return offering;
            }
        }
        return null;
    }

    public void sortCourseOffering() {
        Collections.sort(offerings);
    }

    public boolean isOfferingExisted(CourseOffering offering) {
        for (CourseOffering courseOffering : offerings) {
            if (courseOffering.equals(offering)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<CourseOffering> iterator() {
        return offerings.iterator();
    }
}
