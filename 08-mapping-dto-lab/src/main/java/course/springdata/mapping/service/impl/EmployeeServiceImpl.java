package course.springdata.mapping.service.impl;

import course.springdata.mapping.dao.EmployeeRepository;
import course.springdata.mapping.entity.Employee;
import course.springdata.mapping.exception.NonexistingEntityException;
import course.springdata.mapping.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepo.findById(id).orElseThrow(
                () -> new NonexistingEntityException(
                        String.format("Employee with ID=%s does not exists.", id))
        );
    }

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {
        employee.setId(null);
        return employeeRepo.save(employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        getEmployeeById(employee.getId());
        return employeeRepo.save(employee);
    }

    @Override
    @Transactional
    public Employee deleteEmployee(Long id) {
        Employee removed = getEmployeeById(id);
        employeeRepo.deleteById(id);
        return removed;
    }

    @Override
    public long getEmployeeCount() {
        return employeeRepo.count();
    }
}
