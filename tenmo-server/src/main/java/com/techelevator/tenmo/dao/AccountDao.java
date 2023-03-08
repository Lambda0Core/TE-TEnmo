package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

public interface AccountDao {

    Account getBalance(int id);
    Account getAccountByUserId(int userId);
    Account getAccount();
    Account getAccountByAccountId(int accountId);

}
