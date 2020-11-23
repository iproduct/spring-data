package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.entities.Company;
import softuni.workshop.data.entities.Employee;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.CompanyRepository;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.excepion.EntityNotFoundException;
import softuni.workshop.service.dtos.EmployeeDto;
import softuni.workshop.service.dtos.EmployeeRootDto;
import softuni.workshop.service.dtos.ProjectDto;
import softuni.workshop.service.dtos.ProjectRootDto;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final static String EMPLOYEES_PATH = "src/main/resources/files/xmls/employees.xml";


    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, CompanyRepository companyRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeeRootDto employeeRootDto = this.xmlParser.parseXml(EmployeeRootDto.class, EMPLOYEES_PATH);

        for (EmployeeDto employeeDto : employeeRootDto.getEmployeeDtoList()) {
            Employee employee = this.modelMapper.map(employeeDto, Employee.class);
            Company company = companyRepository.findByName(employee.getProject().getCompany().getName())
                    .orElseThrow(() ->  new EntityNotFoundException(
                            String.format("Company '%s' not found.", employee.getProject().getCompany().getName()))
                    );
            Project project = projectRepository.findByNameAndCompanyAndStartDate(
                    employee.getProject().getName(), company, employee.getProject().getStartDate())
                    .orElseThrow(() ->  new EntityNotFoundException(
                            String.format("Project '%s' not found.", employee.getProject().getName()))
                    );
            employee.setProject(project);
            employeeRepository.saveAndFlush(employee);
        }
    }

    @Override
    public boolean areImported() {
       return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return String.join("\n", Files.readAllLines(Path.of(EMPLOYEES_PATH)));
    }

    @Override
    public String exportEmployeesWithAgeAbove(int age) {
        List<Employee> employees = employeeRepository.findAllByAgeGreaterThan(age);
        List<EmployeeDto> employeeDtos = employees.stream()
                .map(p -> modelMapper.map(p, EmployeeDto.class))
                .collect(Collectors.toList());
        return xmlParser.exportXml(new EmployeeRootDto(employeeDtos), EmployeeRootDto.class);
    }
}
