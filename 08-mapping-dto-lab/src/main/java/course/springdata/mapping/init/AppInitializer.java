package course.springdata.mapping.init;

import course.springdata.mapping.dto.EmployeeDto;
import course.springdata.mapping.entity.Address;
import course.springdata.mapping.entity.Employee;
import course.springdata.mapping.service.AddressService;
import course.springdata.mapping.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AppInitializer implements CommandLineRunner {
    private final EmployeeService employeeService;

    private final AddressService addressService;

    public AppInitializer(EmployeeService employeeService, AddressService addressService) {
        this.employeeService = employeeService;
        this.addressService = addressService;
    }

    @Override
    public void run(String... args) throws Exception {
        ModelMapper mapper = new ModelMapper();

        // 1. Create address and employee and map it to EmployeeDto
        Address address1 = new Address("Bulgaria", "Sofia", "Graf Ignatiev 50");
        address1 = addressService.addAddress(address1);
        Employee employee1 = new Employee("Ivan", "Petrov", 3500,
                LocalDate.of(1981, 5, 12), address1);
        employee1 = employeeService.addEmployee(employee1);

        EmployeeDto employeeDto = mapper.map(employee1, EmployeeDto.class);
        System.out.printf("EmployeeDto: %s%n", employeeDto);
    }
}
