package course.spring.dataintro.dao;

import course.spring.dataintro.entity.Account;
import course.spring.dataintro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
