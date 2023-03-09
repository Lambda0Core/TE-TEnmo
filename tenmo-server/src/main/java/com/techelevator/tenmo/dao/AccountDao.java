package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface AccountDao {

   Balance getBalance(int accountId);

   Account getAccount();

    Account getAccountByUserId(int userId);
    Account getAccountByAccountId(int accountId);
    void updateAccount(Account accountToUpdate);

}
