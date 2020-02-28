package demos.springdata.springdataintro.init;

import demos.springdata.springdataintro.domain.AccountService;
import demos.springdata.springdataintro.domain.UserService;
import demos.springdata.springdataintro.exception.IllegalBankOperationException;
import demos.springdata.springdataintro.model.Account;
import demos.springdata.springdataintro.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
@Slf4j
public class AccountsDemoRunner implements ApplicationRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user1 = new User("Ivan Petrov", 35);
        User user2 = new User("Stamat Dimitrov", 49);

        Account accoun1 = new Account(new BigDecimal(5200), user1);
        user1.getAccounts().add(accoun1);
        Account accoun2 = new Account(new BigDecimal(35000), user2);
        user2.getAccounts().add(accoun2);
        userService.registerUser(user1);
        userService.registerUser(user2);

        log.info("!!! Initial balance: {}",
                accountService.getAccount(accoun1.getId()));
        accountService.withdrawMoney(accoun1.getId(), new BigDecimal(500));
        log.info("!!! Balance after withdrawal $500: {}",
                accountService.getAccount(accoun1.getId()));
        accountService.depositMoney(accoun1.getId(), new BigDecimal(200));
        log.info("!!! Balance after deposit $200: {}",
                accountService.getAccount(accoun1.getId()));

        try {
            accountService.transferMoney(accoun1.getId(), accoun2.getId(),
                    new BigDecimal(2000));
        } catch (IllegalBankOperationException e) {
            log.error(e.getMessage());
        }
        log.info("!!! Balance FROM after transfer $2000: {}",
                accountService.getAccount(accoun1.getId()));
        log.info("!!! Balance TO after transfer $2000: {}",
                accountService.getAccount(accoun2.getId()));
    }
}
