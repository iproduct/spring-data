package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshop.data.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
