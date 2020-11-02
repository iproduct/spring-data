package course.spring.dataintro.dao;

import course.spring.dataintro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
