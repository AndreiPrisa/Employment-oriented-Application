import java.util.ArrayList;

public abstract class Department {
    private ArrayList<Employee> employees;
    private ArrayList<Job> jobs;
    private final String name;

    protected Department(String name) {
        this.name = name;
    }

    public abstract double getTotalSalaryBudget();

    public String getName() {
        return name;
    }

    public ArrayList<Job> getJobs() {
        if (jobs == null)
            return null;
        jobs.removeIf(job -> !job.isFlag());
        return jobs;
    }

    public void add(Employee employee) {
        if (employees == null)
            employees = new ArrayList<>();
        employees.add(employee);
    }

    public void remove(Employee employee) {
        employees.remove(employee);
    }

    public void add(Job job) {
        if (jobs == null)
            jobs = new ArrayList<>();
        jobs.add(job);

        Application application = Application.getInstance();
        if (application.getCompany(job.getCompanyName()) != null)
            application.getCompany(job.getCompanyName()).notifyAllObservers(new JobAdded(job));
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    @Override
    public String toString() {
        return name;
    }
}