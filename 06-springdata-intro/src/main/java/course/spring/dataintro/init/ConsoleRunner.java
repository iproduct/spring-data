package course.spring.dataintro.init;

import course.spring.dataintro.entity.Account;
import course.spring.dataintro.entity.User;
import course.spring.dataintro.service.AccountService;
import course.spring.dataintro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private UserService userService;
    private AccountService accountService;

    @Autowired
    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Ivan Petrov", 42);
        Account account1 = new Account(new BigDecimal(1500));

        userService.registerUser(user1);
        accountService.createUserAccount(user1, account1);
        accountService.depositMoney(new BigDecimal(1000), account1.getId());
        accountService.withdrawMoney(new BigDecimal(1000), account1.getId());

        User user2 = new User("Dimitar Georgiev", 25);
        Account account2 = new Account(new BigDecimal(2400));
        userService.registerUser(user2);
        accountService.createUserAccount(user2, account2);

        accountService.transferMoney(new BigDecimal(2000), account1.getId(), account2.getId());

        accountService.getAllAccounts().forEach(System.out::println);
    }
}
