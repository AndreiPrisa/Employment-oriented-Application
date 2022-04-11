public class Employee extends Consumer {
    private String companyName;
    private Double salary;

    public Employee(String companyName, double salary) {
        this.companyName = companyName;
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

}
