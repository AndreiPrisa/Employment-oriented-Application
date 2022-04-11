import java.util.ArrayList;

public class Company implements Subject {
    private String name;
    private Manager manager;
    private ArrayList<Department> departments;
    private ArrayList<Recruiter> recruiters;
    ArrayList<Observer> observers = new ArrayList<>();

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public Department getDepartment(String name) {
        for (Department department : getDepartments()) {
            if (department.getName().equals(name))
                return department;
        }
        return null;
    }

    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }

    public ArrayList<Recruiter> getRecruiters() {
        return recruiters;
    }

    public void setRecruiters(ArrayList<Recruiter> recruiters) {
        this.recruiters = recruiters;
    }

    public void add(Department department) {
        if (departments == null)
            departments = new ArrayList<>();
        departments.add(department);
    }

    public void add(Recruiter recruiter) {
        if (recruiters == null)
            recruiters = new ArrayList<>();
        recruiters.add(recruiter);
    }

    public void add(Employee employee, Department department) {
        department.add(employee);
    }

    public void remove(Employee employee) {
        for (Department department : departments) {
            department.remove(employee);
        }
    }

    public void remove(Department department) {
        department.getEmployees().clear();
        departments.remove(department);
    }

    public void remove(Recruiter recruiter) {
        recruiters.remove(recruiter);
    }

    public void move(Department source, Department destination) {
        destination.getEmployees().addAll(source.getEmployees());
        destination.getJobs().addAll(source.getJobs());
        source.getEmployees().clear();
        remove(source);
    }

    public void move(Employee employee, Department newDepartment) {
        for (Department source : departments) {
            if (source.getEmployees().contains(employee)) {
                source.remove(employee);
                break;
            }
        }
        newDepartment.add(employee);
    }

    public boolean contains(Department department) {
        return departments.contains(department);
    }

    public boolean contains(Employee employee) {
        for (Department department : departments) {
            if (department.getEmployees().contains(employee))
                return true;
        }
        return false;
    }

    public Recruiter getRecruiter(User user) {
        ArrayList<Recruiter> possibleRecruiters = new ArrayList<>();
        int maxDepth = 0, depth;

        for (Recruiter r : recruiters) {
            depth = user.getDegreeInFriendship(r);
            if (depth > maxDepth) {
                maxDepth = depth;
                possibleRecruiters.clear();
                possibleRecruiters.add(r);
            }
            else
                if (depth == maxDepth)
                    possibleRecruiters.add(r);
        }

        if (possibleRecruiters.size() > 1) {
            Recruiter bestRecruiter = possibleRecruiters.get(0);
            for (Recruiter r : possibleRecruiters) {
                if (r.getRating() > bestRecruiter.getRating())
                    bestRecruiter = r;
            }
            return bestRecruiter;
        }
        else return possibleRecruiters.get(0);
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        for (Department department : departments) {
            if (department.getJobs() != null)
                jobs.addAll(department.getJobs());
        }
        return jobs;
    }

    @Override
    public void addObserver(User user) {
        if (!observers.contains(user))
            observers.add(user);
    }

    @Override
    public void removeObserver(User c) {
        observers.remove(c);
    }

    @Override
    public void notifyAllObservers(Notification notification) {
        for(Observer o : observers) {
            o.update(notification);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}

