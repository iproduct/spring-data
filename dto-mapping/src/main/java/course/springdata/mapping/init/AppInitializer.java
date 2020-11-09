package course.springdata.mapping.init;

import course.springdata.mapping.dto.EmployeeDto;
import course.springdata.mapping.dto.ManagerDto;
import course.springdata.mapping.entity.Address;
import course.springdata.mapping.entity.Employee;
import course.springdata.mapping.service.AddressService;
import course.springdata.mapping.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppInitializer implements CommandLineRunner {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AddressService addressService;


    @Override
    public void run(String... args) throws Exception {
        ModelMapper mapper = new ModelMapper();
        //1. Create entities
        Address address1 = new Address("Bulgaria", "Sofia", "ul. G.S.Rakovski, 20");
        Employee emp1 = new Employee("Ivan", "Petrov", 3780,
                LocalDate.of(1982, 03, 12), address1);
        addressService.addAddress(address1);
        employeeService.addEmployee(emp1);

        //Fetch employee and map to DTO
        EmployeeDto employeeDto = mapper.map(employeeService.getEmployeeById(1L), EmployeeDto.class);
        System.out.printf("EmployeeDTO: %s%n%s%n",
                employeeDto.toString(), "-".repeat(80));

        // 2. Create addresses and employees - managers and subordinates
        List<Address> addresses = List.of(
                new Address("Bulgaria", "Sofia", "ul. G.S.Rakovski, 50"),
                new Address("Bulgaria", "Sofia", "Bul. Dondukov, 45"),
                new Address("Bulgaria", "Sofia", "ul. Hristo Smirnenski, 40"),
                new Address("Bulgaria", "Sofia", "bul. Alexander Malinov, 93a"),
                new Address("Bulgaria", "Sofia", "bul. Slivnitsa, 50"),
                new Address("Bulgaria", "Plovdiv", "ul. Angel Kanchev,34")
        );
        addresses = addresses.stream().map(addressService::addAddress).collect(Collectors.toList());

        List<Employee> employees = List.of(
                new Employee("Steve", "Adams", 5780, LocalDate.of(1982, 3, 12),
                        addresses.get(0)),
                new Employee("Stephen", "Petrov", 2760, LocalDate.of(1974, 5, 19),
                        addresses.get(1)),
                new Employee("Hristina", "Petrova", 3680, LocalDate.of(1989, 11, 9),
                        addresses.get(1)),
                new Employee("Diana", "Atanasova", 6790, LocalDate.of(1989, 12, 9),
                        addresses.get(2)),
                new Employee("Samuil", "Georgiev", 4780, LocalDate.of(1979, 2, 10),
                        addresses.get(3)),
                new Employee("Ivan", "Petrov", 3780, LocalDate.of(1985, 2, 23),
                        addresses.get(4)),
                new Employee("Ivan", "Petrov", 3960, LocalDate.of(1982, 3, 11),
                        addresses.get(5))
        );
        List<Employee> created = employees.stream().map(employeeService::addEmployee).collect(Collectors.toList());

        // Set managers of employees
        created.get(1).setManager(created.get(0));
        created.get(2).setManager(created.get(0));

        created.get(4).setManager(created.get(3));
        created.get(5).setManager(created.get(3));
        created.get(6).setManager(created.get(3));

        created.forEach(employeeService::updateEmployee);

        // Print managers with subordinates
        List<Employee> managers = employeeService.getManagers();
        TypeMap<Employee, ManagerDto> typeMap = mapper.createTypeMap(Employee.class, ManagerDto.class);
        typeMap.addMappings(m -> {
            m.map(src -> src.getSubordinates(), ManagerDto::setEmployees);
            m.skip(ManagerDto::setNewProp);
        });
        typeMap.validate();

        List<ManagerDto> managerDtos = managers.stream().map(manager -> typeMap.map(manager))
                .collect(Collectors.toList());
        managerDtos.forEach(System.out::println);

        // 3. Employees  born after 1990 with manager last name
        System.out.println("-".repeat(80) + "\n");
        TypeMap<Employee, EmployeeDto> typeMap3 = mapper.getTypeMap(Employee.class, EmployeeDto.class);
        typeMap3.addMappings(m -> m.map(src -> src.getManager().getLastName(), EmployeeDto::setManagerLastName));
        List<EmployeeDto> employeeDtos = employeeService.getAllEmployees().stream()
                .map(emp -> typeMap3.map(emp))
                .collect(Collectors.toList());
        employeeDtos.forEach(System.out::println);

    }
}
