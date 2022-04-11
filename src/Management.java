public class Management extends Department{
    public Management(String name) {
        super(name);
    }

    @Override
    public double getTotalSalaryBudget() {
        double totalSalaryBudget = 0;
        for (Employee e : getEmployees()) {
            totalSalaryBudget += e.getSalary();
        }
        return totalSalaryBudget * 0.84;
    }
}
