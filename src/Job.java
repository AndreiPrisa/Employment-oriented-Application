import java.time.Year;
import java.util.ArrayList;

public class Job {
    private String jobName;
    private String departmentName;
    private String companyName;
    private boolean flag;
    private Constraint dateOfGraduationConstraint, YearsOfExperienceConstraint, GPAConstraint;
    private ArrayList<User> candidates;
    private int NoOfNeededCandidates;
    private double salary;

    public Job(String jobName, String departmentName, String companyName, int noOfNeededCandidates, double salary) {
        this.jobName = jobName;
        this.departmentName = departmentName;
        this.companyName = companyName;
        NoOfNeededCandidates = noOfNeededCandidates;
        this.salary = salary;
        this.flag = true;
        this.candidates = new ArrayList<>();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Constraint getDateOfGraduationConstraint() {
        return dateOfGraduationConstraint;
    }

    public void setDateOfGraduationConstraint(Constraint dateOfGraduationConstraint) {
        this.dateOfGraduationConstraint = dateOfGraduationConstraint;
    }

    public Constraint getYearsOfExperienceConstraint() {
        return YearsOfExperienceConstraint;
    }

    public void setYearsOfExperienceConstraint(Constraint yearsOfExperienceConstraint) {
        YearsOfExperienceConstraint = yearsOfExperienceConstraint;
    }

    public Constraint getGPAConstraint() {
        return GPAConstraint;
    }

    public void setGPAConstraint(Constraint GPAConstraint) {
        this.GPAConstraint = GPAConstraint;
    }

    public ArrayList<User> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<User> candidates) {
        this.candidates = candidates;
    }

    public void addCandidate(User candidate) {
        if (candidates == null)
            candidates = new ArrayList<>();
        candidates.add(candidate);
    }

    public int getNoOfNeededCandidates() {
        return NoOfNeededCandidates;
    }

    public void setNoOfNeededCandidates(int noOfNeededCandidates) {
        NoOfNeededCandidates = noOfNeededCandidates;
    }
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void apply(User user) {
        Application application = Application.getInstance();
        Recruiter bestRecruiter = application.getCompany(companyName).getRecruiter(user);
        application.getCompany(companyName).addObserver(user);
        if (meetsRequirements(user))
            bestRecruiter.evaluate(this, user);

        else {
            user.update(new JobRejected(this));
        }
    }

    public boolean meetsRequirements(User user) {
        Double graduationYear = null;

        if (user.getGraduationYear() != null)
            graduationYear = (double) user.getGraduationYear();
        Double numberOfYearsOfExperience = (double) user.getNumberOfYearsOfExperience();
        Double meanGPA = user.meanGPA();

        if (dateOfGraduationConstraint.check(graduationYear)
                && YearsOfExperienceConstraint.check( numberOfYearsOfExperience)
        && GPAConstraint.check(meanGPA))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return jobName;
    }
}