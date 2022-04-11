public class Experience implements Comparable<Experience> {
    private DateFormat startDate;
    private DateFormat endDate;
    private String position;
    private String companyName;

    public Experience(DateFormat startDate, DateFormat endDate, String position, String companyName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.companyName = companyName;
    }

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompany(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return position + " at " + companyName + "from " + startDate + " to " + endDate + "\n";
    }

    @Override
    public int compareTo(Experience o) {

        if (this.endDate == null || o.endDate == null || this.endDate.compareTo(o.endDate) == 0)
            return companyName.compareTo(o.companyName);
        else
            return o.endDate.compareTo(this.endDate);
    }
}