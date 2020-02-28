package demos.springdata.springdataintro.domain;

import demos.springdata.springdataintro.dao.AccountRepository;
import demos.springdata.springdataintro.exception.IllegalBankOperationException;
import demos.springdata.springdataintro.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service("accService")
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccount(long accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    @Override
    public void withdrawMoney(long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId);
        if(account.getBalance().compareTo(amount) < 0){
            throw new IllegalBankOperationException(
                    "Current balance: $" + account.getBalance()
                    + " is not sufficient to withdraw amount: " + amount);
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void depositMoney(long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void transferMoney(long fromId, long toId, BigDecimal amount) {
        depositMoney(toId, amount);
        withdrawMoney(fromId, amount);
    }
}
