package demos.springdata.springdataintro.dao;

import demos.springdata.springdataintro.model.Account;
import demos.springdata.springdataintro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(long id);
}
