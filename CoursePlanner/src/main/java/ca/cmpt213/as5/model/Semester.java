package ca.cmpt213.as5.model;

/**
 * Represent a semester's number, year, term
 */
public class Semester {
    private String semesterNumber;
    private int year;
    private String term;
    private int semesterCode;

    public Semester(String semesterNumber) {
        this.semesterNumber = semesterNumber;
        this.semesterCode = Integer.parseInt(semesterNumber);
        setYear();
        setTerm();
    }

    public int getSemesterCode() {
        return semesterCode;
    }

    public String getSemesterNumber() {
        return semesterNumber;
    }

    public int getYear() {
        return year;
    }

    public String getTerm() {
        return term;
    }

    private void setTerm() {
        int termNum = Integer.parseInt("" + semesterNumber.charAt(3));
        switch (termNum) {
            case 1:
                this.term = "Spring";
                break;
            case 4:
                this.term = "Summer";
                break;
            case 7:
                this.term = "Fall";
                break;
        }
    }

    private void setYear() {
        int tem1 = Integer.parseInt("" + (semesterNumber.charAt(0)));
        int tem2 = Integer.parseInt("" + (semesterNumber.charAt(1)));
        int tem3 = Integer.parseInt("" + (semesterNumber.charAt(2)));
        this.year = 1900 + 100 * tem1 + 10 * tem2 + tem3;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Semester semester = (Semester) obj;

        boolean isEqual = semesterNumber.equals(semester.semesterNumber);
        return isEqual;
    }

}
