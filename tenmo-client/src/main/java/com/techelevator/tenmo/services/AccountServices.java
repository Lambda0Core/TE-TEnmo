package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;


public interface AccountServices {

    BigDecimal getBalance(int userId);
    BigDecimal addToBalance(BigDecimal amountToAdd, int id);
    BigDecimal subtractFromBalance(BigDecimal amountToSubtract, int id);
    Account getAccount();
    Account getAccountByUserId(int userId);
    Account getAccountByAccountId(int accountId);
    void updateAccount(Account accountToUpdate);

}

