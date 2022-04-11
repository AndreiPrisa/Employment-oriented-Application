import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;

public class User extends Consumer implements Observer {
    private ArrayList<String> companies;
    private ArrayList<Notification> notifications;

    public ArrayList<String> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<String> companies) {
        this.companies = companies;
    }
    // functie pentru a adauga o companie
    public void addCompany(String company) {
        if (companies == null)
            companies = new ArrayList<>();
        companies.add(company);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void clearNotifications() {
        notifications = null;
    }

    public Employee convert() {
        Employee newEmployee = new Employee(null, 0);
        newEmployee.setFriends(this.getFriends());
        newEmployee.setResume(this.getResume());
        return newEmployee;

    }

    public Double getTotalScore() {
        return (double) getNumberOfYearsOfExperience() * 1.5 + meanGPA();
    }

    public void addNotification(Notification notification) {
        if (notifications == null)
            notifications = new ArrayList<>();
        notifications.add(notification);
    }

    @Override
    public void update(Notification notification) {
        System.out.println(getResume().getInformation().getFullName() + " : " + notification);
        addNotification(notification);
    }
}