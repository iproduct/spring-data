package course.spring.dataintro.service;

import course.spring.dataintro.entity.Account;
import course.spring.dataintro.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    void withdrawMoney(BigDecimal amount, Long id);
    void depositMoney(BigDecimal amount, Long id);
    void transferMoney(BigDecimal amount, Long fromId, Long toId);

    Account createUserAccount(User user, Account account);
}
