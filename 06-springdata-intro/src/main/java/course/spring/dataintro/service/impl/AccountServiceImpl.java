package course.spring.dataintro.service.impl;

import course.spring.dataintro.dao.AccountRepository;
import course.spring.dataintro.entity.Account;
import course.spring.dataintro.entity.User;
import course.spring.dataintro.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepo;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepo = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public Account createUserAccount(User user, Account account) {
        account.setId(null);
        account.setUser(user);
        if(!user.getAccounts().contains(account)) {
            user.getAccounts().add(account);
        }
        return accountRepo.save(account);
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) {
        Account account = accountRepo.findById(id).orElseThrow(RuntimeException::new);
        account.setBallance(account.getBallance().subtract(amount));
    }

    @Override
    public void depositMoney(BigDecimal amount, Long id) {
        Account account = accountRepo.findById(id).orElseThrow(RuntimeException::new);
        account.setBallance(account.getBallance().add(amount));
    }

    @Override
    public void transferMoney(BigDecimal amount, Long fromId, Long toId) {
//        Set<Account> accounts = accountRepo.findAllById(Set.of(fromId, toId));
        Account fromAccount = accountRepo.findById(fromId).orElseThrow(RuntimeException::new);
        Account toAccount = accountRepo.findById(toId).orElseThrow(RuntimeException::new);
        fromAccount.setBallance(fromAccount.getBallance().subtract(amount));
        if (fromAccount.getBallance().compareTo(amount) < 0)
            throw new RuntimeException(String.format("Not enough money in the acount: %d. Can not withdraw $%s. ",
                    fromAccount.getId(), amount));
        toAccount.setBallance(toAccount.getBallance().add(amount));
    }


}
