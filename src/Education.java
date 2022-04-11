public class Education implements Comparable<Education>{
    private DateFormat startDate;
    private DateFormat endDate;
    private String institutionName;
    private String levelOfEducation;
    private double grade;

    public DateFormat getStartDate() {
        return startDate;
    }

    public void setStartDate(DateFormat startDate) {
        this.startDate = startDate;
    }

    public DateFormat getEndDate() {
        return endDate;
    }

    public void setEndDate(DateFormat endDate) {
        this.endDate = endDate;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getLevelOfEducation() {
        return levelOfEducation;
    }

    public void setLevelOfEducation(String levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }

    public double getGrade() {
        return grade;
    }

    public void setGPA(int grade) {
        this.grade = grade;
    }

    public Education(DateFormat startDate, DateFormat endDate, String institutionName,
                     String levelOfEducation, double grade) throws InvalidDatesException {
        if (endDate != null && startDate.compareTo(endDate) > 0)
            throw new InvalidDatesException();
        this.startDate = startDate;
        this.endDate = endDate;
        this.institutionName = institutionName;
        this.levelOfEducation = levelOfEducation;
        this.grade = grade;

    }

    @Override
    public String toString() {
        return levelOfEducation + " degree at " + institutionName + " from " + startDate + " to " + endDate + "\n";
    }

    @Override
    public int compareTo(Education o) {
        if (this.endDate == null || o.endDate == null)
            return this.startDate.compareTo(o.startDate);

        if (this.endDate.compareTo(o.endDate) == 0)
            if (o.grade > this.grade)
                return 1;
            else
                return -1;
        else
            return o.endDate.compareTo(this.endDate);
    }
}