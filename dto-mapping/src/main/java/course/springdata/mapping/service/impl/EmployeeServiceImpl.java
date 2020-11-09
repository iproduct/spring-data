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
    public List<Employee> getManagers() {
        return employeeRepo.getAllBySubordinatesIsNotEmpty();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepo.findById(id).orElseThrow(()->
                new NonexistingEntityException(String.format("Employee with ID=%s does not exist.%n")));
    }

    @Transactional
    @Override
    public Employee addEmployee(Employee employee) {
        employee.setId(null);
        Employee created = employeeRepo.save(employee);
        if(employee.getManager() != null) {
            created.getManager().getSubordinates().add(employee);
        }
        return created;
    }

    @Transactional
    @Override
    public Employee updateEmployee(Employee employee) {
        Employee old = getEmployeeById(employee.getId());
        Employee updated = employeeRepo.save(employee);
        if(old.getManager() != null && !old.getManager().equals(employee.getManager())) {
            old.getManager().getSubordinates().remove(old);
        }
        if(employee.getManager() != null && !employee.getManager().equals(old.getManager())) {
            updated.getManager().getSubordinates().add(employee);
        }
        return updated;
    }

    @Transactional
    @Override
    public Employee deleteEmployee(Long id) {
        Employee removed = getEmployeeById(id);
        if(removed.getManager() == null) {
           removed.getManager().getSubordinates().remove(removed);
        }
        employeeRepo.deleteById(id);
        return removed;
    }

    @Override
    public long getEmployeeCount() {
        return employeeRepo.count();
    }
}
