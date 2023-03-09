package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;


//    @Override
//    public Balance getBalance(int accountId) {
//        Balance[] balances = new Balance[0];
//        for (Balance balance : balances) {
//            if (balance.g == accountId) {
//                return balance;
//            }
//        }
//        return null;
//    }

    @Override
    public Balance getBalance(int accountId) {
        return null;
    }

    @Override
    public Account getAccount() {
        return null;
    }


//    @Override
//    public BigDecimal getBalance(int userId) {
//        BigDecimal balance = null;
//        String sql = "SELECT balance FROM account \n" +
//                "WHERE account_id = ?;";
//
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
//
//        if (results.next()) {
//            balance = results.getBigDecimal("BigDecimal");
//        }
//        return balance;
//    }

    @Override
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        Account account = null;
        if (result.next()) {
            account = mapResultsToAccount(result);
        }
        return account;
    }

    @Override
    public Account getAccountByAccountId(int accountId) {
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountId);
        Account account = null;
        if (result.next()) {
            account = mapResultsToAccount(result);
        }
        return account;
    }

    @Override
    public void updateAccount(Account accountToUpdate) {
        String sql = "UPDATE account " +
                "SET balance = ? " +
                "WHERE account_id = ?";

        jdbcTemplate.update(sql, accountToUpdate.getBalance(), accountToUpdate.getAccountId(), accountToUpdate.getUserId());
    }

    private Account mapResultsToAccount(SqlRowSet result) {
        int accountId = result.getInt("account_id");
        int userAccountId = result.getInt("user_id");

        Balance balance = new Balance();
        String accountBalance = result.getString("balance");
        balance.setBalance(new BigDecimal(accountBalance));
        return new Account(accountId, userAccountId, balance);
    }
}