package ca.cmpt213.as5.model;

/**
 * Represent a request body content to create a new watcher
 * Represent the request body's department id and course id
 */
public class WatcherRequestInfo {
    private long deptId;
    private long courseId;

    public WatcherRequestInfo(long deptId, long courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public long getDeptId() {
        return deptId;
    }

    public long getCourseId() {
        return courseId;
    }
}
