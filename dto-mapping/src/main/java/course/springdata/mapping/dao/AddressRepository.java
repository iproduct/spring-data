package course.springdata.mapping.dao;

import course.springdata.mapping.entity.Address;
import course.springdata.mapping.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
