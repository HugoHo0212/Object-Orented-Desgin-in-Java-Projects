package ca.cmpt213.as5.model;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represent a processor to process the data read from the course data CSV file
 * Holding the processed data, the course manager
 */
public class CourseDataProcessor {
    private DepartmentManager departmentManager;
//    private static AtomicLong nextId = new AtomicLong();

    public static final String filePath = "data/course_data_2018.csv";

    public CourseDataProcessor() {
        this.departmentManager = mergeDataFromCSV();
    }

    public DepartmentManager mergeDataFromCSV() {
        CourseDataReader reader = new CourseDataReader(filePath);
        HashMap<String, List<List<String>>> hashMap = reader.readCSV();
        HashMap<String, Integer> header = reader.getHeader();
        DepartmentManager departmentManager = new DepartmentManager();
        for (String key : hashMap.keySet()) {
            List<List<String>> courses = hashMap.get(key);
            String[] arr = key.split(" ");
            CourseOfferingManager offerings = new CourseOfferingManager();
            for (List<String> s : courses) {
                int capacity = Integer.parseInt(s.get(header.get("ENROLMENTCAPACITY")));
                int enrolledNumber = Integer.parseInt(s.get(header.get("ENROLMENTTOTAL")));
                String semester = s.get(header.get("SEMESTER"));
                String location = s.get(header.get("LOCATION"));
                String sectionType = s.get(header.get("COMPONENTCODE"));
                String instructorName = s.get(header.get("INSTRUCTORS"));
                if (instructorName.equals("(null)") || instructorName.equals("<null>")) {
                    instructorName = "";
                }
                Enrolment enrolment = new Enrolment(capacity, enrolledNumber);
                CourseOffering courseOffering = new CourseOffering(semester, location, instructorName);
                CourseSection section = new CourseSection(enrolment, sectionType);
                offerings.add(courseOffering, section);
                Course course = new Course(arr[1]);
                Department department = new Department(arr[0]);
                departmentManager.add(department, course, courseOffering, section);
            }
        }
        return departmentManager;
    }

    public DepartmentManager getDepartmentManager() {
        return departmentManager;
    }

    public void dumpModel() {
        for (Department department : departmentManager) {
            String departmentName = department.getName();
            for (Course course : department.getCourseManager()) {
                System.out.println(departmentName + " " + course.getCatalogNumber());
                CourseOfferingManager courseOfferingManager = course.getCourseOfferingManager();
                for (CourseOffering offering: courseOfferingManager) {
                    String offeringInfo = "      " + offering.getSemester().getSemesterNumber() + " in " + offering.getLocation() + " by " +
                            offering.getInstructors();
                    System.out.println(offeringInfo);
                    for (CourseSection section : offering.getCourseSectionManager()) {
                        int capacity = section.getEnrolment().getCapacity();
                        int enrolledNumber = section.getEnrolment().getEnrolledNumber();
                        String sectionInfo = "            " + "Type=" + section.getType() +
                                ", Enrollment=" + enrolledNumber + "/" + capacity;
                        System.out.println(sectionInfo);
                    }
                }
            }

        }
    }
}
