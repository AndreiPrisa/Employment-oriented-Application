public class Marketing extends Department {

    public Marketing(String name) {
        super(name);
    }

    @Override
    public double getTotalSalaryBudget() {
        double totalSalaryBudget = 0;
        for (Employee e : getEmployees()) {
            double employeeSalary = e.getSalary();
            if (employeeSalary > 5000)
                employeeSalary *= 0.9;
            else
            if (employeeSalary >= 3000)
                employeeSalary *= 0.84;
            totalSalaryBudget += employeeSalary;
        }
        return totalSalaryBudget;
    }
}
