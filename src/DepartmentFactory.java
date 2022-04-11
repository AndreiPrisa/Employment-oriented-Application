public class DepartmentFactory {
    public Department getDepartment(String departmentType) {
        if (departmentType == null)
            return null;
        if (departmentType.compareTo("IT") == 0)
            return new IT("IT");
        if (departmentType.compareTo("Management") == 0)
            return new Management("Management");
        if (departmentType.compareTo("Marketing") == 0)
            return new Marketing("Marketing");
        if (departmentType.compareTo("Finance") == 0)
            return new Finance("Finance");

        throw new IllegalArgumentException("Nu exista tipul " + departmentType);
    }
}
