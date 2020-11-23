package softuni.workshop.service.services;

public interface EmployeeService {

    void importEmployees();

    boolean areImported();

    String readEmployeesXmlFile();

    String exportEmployeesWithAgeAbove();
}
