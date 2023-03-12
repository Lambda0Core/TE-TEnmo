package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public interface AccountDao {

    BigDecimal getBalance(int userId);
    BigDecimal addToBalance(BigDecimal amountToAdd, int id);
    Account update(Account account,int id);
    BigDecimal subtractFromBalance(BigDecimal amountToSubtract, int id);
    Account getAccount();
    Account getAccountByUserId(int userId);
    Account getAccountByAccountId(int accountId);
    void updateAccount(Account accountToUpdate);
    List<Account> getListOfUsers();

}
