package ca.cmpt213.as5.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A department manager to hold a list of department
 */
public class DepartmentManager implements Iterable<Department>{
    private List<Department> departments = new ArrayList<>();
    private static AtomicLong nextId = new AtomicLong();

    public void add(Department department, Course course, CourseOffering offering, CourseSection section) {
        if (!isDepartmentExisted(department)) {
            department.setDeptId(nextId.getAndIncrement());
            departments.add(department);
            department.addCourse(course, offering, section);
            sortDepartments();
        } else {
            for (Department dept : departments) {
                if (dept.equals(department)) {
                    dept.addCourse(course, offering, section);
                }
            }
        }
    }

    public Department getDepartmentById(long deptId) {
        for (Department department : departments) {
            if (department.getDeptId() == deptId) {
                return department;
            }
        }
        return null;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void sortDepartments() {
        Collections.sort(departments);
    }

    public int getDepartmentsSize() {
        return departments.size();
    }

    public boolean isDepartmentExisted(Department department) {
        for (Department dept : departments) {
            if (dept.equals(department)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Department> iterator() {
        return departments.iterator();
    }
}
