package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class CoursePlannerController {
    private CourseDataProcessor courseDataProcessor = new CourseDataProcessor();
    private MyAPI myApi = new MyAPI();
    private DepartmentManager departmentManager = courseDataProcessor.getDepartmentManager();
    private List<Watcher> watchers = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong();

    @GetMapping("/dump-model")
    public void getDumpModel() {
        courseDataProcessor.dumpModel();
    }

    @GetMapping("/about")
    public MyAPI getInfo() {
        return myApi;
    }

    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return departmentManager.getDepartments();
    }

    @GetMapping("/departments/{deptId}/courses")
    public List<Course> getDepartmentCourses(@PathVariable("deptId") long deptId) {
        Department department = departmentManager.getDepartmentById(deptId);
        if (department != null) {
            return department.getCourseManager().getCourses();
        }

        throw new IllegalArgumentException();
    }

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings")
    public List<CourseOffering> getDepartmentCoursesOfferings(@PathVariable("deptId") long deptId,
                                                      @PathVariable("courseId") long courseId) {

        Department department = departmentManager.getDepartmentById(deptId);
        if (department != null) {
            CourseManager courses = department.getCourseManager();
            Course course = courses.getCourseById(courseId);
            if (course != null) {
                return course.getCourseOfferingManager().getOfferings();
            }
        }

        throw new IllegalArgumentException();
    }

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings/{offeringId}")
    public List<CourseSection> getOfferingSections(@PathVariable("deptId") long deptId,
                                                    @PathVariable("courseId") long courseId,
                                                    @PathVariable("offeringId") long offeringId) {

        Department department = departmentManager.getDepartmentById(deptId);
        if (department != null) {
            CourseManager courses = department.getCourseManager();
            Course course = courses.getCourseById(courseId);
            if (course != null) {
                CourseOfferingManager offerings = course.getCourseOfferingManager();
                CourseOffering offering = offerings.getOfferingById(offeringId);
                if (offering != null) {
                    return offering.getCourseSectionManager().getSections();
                }
            }
        }

        throw new IllegalArgumentException();
    }

    @PostMapping("/addoffering")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addOffering(@RequestBody NewOfferingInfo offeringInfo) {
        Department department = new Department(offeringInfo.getSubjectName());
        Course course = new Course(offeringInfo.getCatalogNumber());
        CourseOffering offering = new CourseOffering("" + offeringInfo.getSemester(), offeringInfo.getLocation(), offeringInfo.getInstructor());
        CourseSection section = new CourseSection(new Enrolment(offeringInfo.getEnrollmentCap(), offeringInfo.getEnrollmentTotal()), offeringInfo.getComponent());
        departmentManager.add(department, course, offering, section);
    }

    @GetMapping("/stats/students-per-semester")
    public List<DataPoint> getStudentsPerSemester(@RequestParam(value = "deptId") long deptId) {
        Department department = departmentManager.getDepartmentById(deptId);
        if (department != null) {
            return department.getDataPointManager().getDataPoints();
        }

        throw new IllegalArgumentException();
    }

    @GetMapping("/watchers")
    public List<Watcher> getWatchers() {
        return watchers;
    }

    @PostMapping("/watchers")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addWatcher(@RequestBody WatcherRequestInfo watcherRequestInfo) {
       long deptId = watcherRequestInfo.getDeptId();
       long courseId = watcherRequestInfo.getCourseId();

       Department department = departmentManager.getDepartmentById(deptId);
       if (department != null) {
           Course course = department.getCourseManager().getCourseById(courseId);
           if (course != null) {
               Watcher watcher = new Watcher(nextId.getAndIncrement(), department, course);
               watchers.add(watcher);
               course.addObserver(watcher);
               return;
           }
       }

       throw new IllegalArgumentException();
    }

    @GetMapping("/watchers/{id}")
    public Watcher getWatcher(@PathVariable("id") long id) {
        for (Watcher watcher : watchers) {
            if (watcher.getId() == id) {
                return watcher;
            }
        }

        throw new IllegalArgumentException();
    }

    @DeleteMapping("/watchers/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteWatcher(@PathVariable("id") long id) {
        int index = 0;
        for (Watcher watcher : watchers) {
            if (watcher.getId() == id) {
                watcher.getCourse().removeObserver(watcher);
                watchers.remove(index);
                return;
            }
            index ++;
        }
        throw new IllegalArgumentException();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason ="Request ID not found.")
    public void badIdException() {

    }
}
