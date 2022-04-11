import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Finance extends Department{
    public Finance(String name) {
        super(name);
    }

    @Override
    public double getTotalSalaryBudget() {
        double salary = 0;
        for (Employee e : getEmployees()) {
            Experience currentExperience = null;
            for (Experience experience : e.getResume().getExperienceSortedSet()) {
                if (experience.getEndDate() == null) {
                    currentExperience = experience;
                    break;
                }
            }
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            DateFormat currentDate = new DateFormat(dateTimeFormatter.format(LocalDateTime.now()));
            System.out.println(currentDate);
            int months = currentDate.year * 12 + currentDate.month -
                    (currentExperience.getStartDate().year * 12 + currentExperience.getStartDate().month);

            if (months < 12)
                salary += e.getSalary() * 0.9;
            else
                salary += e.getSalary() * 0.84;
        }

        return salary;
    }
}
