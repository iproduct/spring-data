package demos.springdata.springdataintro.domain;

import demos.springdata.springdataintro.model.Account;
import demos.springdata.springdataintro.model.User;

import java.math.BigDecimal;

public interface AccountService {
    Account getAccount(long accountId);
    void withdrawMoney(long accountId, BigDecimal amount);
    void depositMoney(long accountId, BigDecimal amount);
    void transferMoney(long fromId, long toId, BigDecimal amount);
}
