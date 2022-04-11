public class IT extends Department {
    public IT(String name) {
        super(name);
    }

    @Override
    public double getTotalSalaryBudget() {
        double totalSalaryBudget = 0;
        for (Employee e : getEmployees()) {
            totalSalaryBudget += e.getSalary();
        }
        return totalSalaryBudget;
    }
}
