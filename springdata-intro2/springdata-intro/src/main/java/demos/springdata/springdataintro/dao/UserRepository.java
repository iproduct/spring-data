package demos.springdata.springdataintro.dao;

import demos.springdata.springdataintro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
