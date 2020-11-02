package course.springdata.intro.service.impl;

import course.springdata.intro.dao.AccountRepository;
import course.springdata.intro.entity.Account;
import course.springdata.intro.entity.User;
import course.springdata.intro.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepo;

    @Autowired
    public void setAccountRepo(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public Account createUserAccount(User user, Account account) {
        account.setId(null);
        account.setUser(user);
        user.getAccounts().add(account);
        return accountRepo.save(account);
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long accountId) {

    }

    @Override
    public void depositMoney(BigDecimal amount, Long accountId) {

    }

    @Override
    public void transferMoney(BigDecimal amount, Long fromAccountId, Long toAccountId) {

    }
}
