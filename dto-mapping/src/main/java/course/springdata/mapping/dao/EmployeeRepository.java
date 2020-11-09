package course.springdata.mapping.dao;

import course.springdata.mapping.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.subordinates IS NOT EMPTY")
    List<Employee> getAllBySubordinatesIsNotEmpty();
}
