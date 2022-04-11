import java.util.ArrayList;

public class Application {
    private ArrayList<Company> companies;
    private ArrayList<User> users;
    private static Application instance;

    private Application() {
        companies = new ArrayList<>();
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public static Application getInstance() {
        if (instance == null)
            instance = new Application();
        return instance;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
    }
    public Company getCompany(String name) {
        for (Company company : companies) {
            if (company.getName().compareTo(name) == 0)
                return company;
        }
        return null;
    }

    public void add(Company company) {
        companies.add(company);
    }

    public void add(User user) {
        users.add(user);
    }

    public boolean remove(Company company) {
        return companies.remove(company);
    }

    public boolean remove(User user) {
        return users.remove(user);
    }

    public void removeObservers(Observer observer) {
        for (Company c : getCompanies()) {
            c.removeObserver((User) observer);
        }
    }

    public ArrayList<Job> getJobs(ArrayList<String> companies) {
        ArrayList<Job> jobs = new ArrayList<>();

        for (String name : companies) {
            Company company = getCompany(name);
            jobs.addAll(company.getJobs());
        }
        return jobs;
    }
}
