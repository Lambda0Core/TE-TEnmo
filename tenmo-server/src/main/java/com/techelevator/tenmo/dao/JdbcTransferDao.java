package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcTransferDao implements TransferDao{
    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }
    private JdbcTemplate jdbcTemplate;

    //Creates a new transfer
    @Override
    public Transfer createTransfer(Transfer transfer) {
        String sql = "insert into transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
        jdbcTemplate.update(sql, transfer.getTransferId(), transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());

        return transfer;
    }

    //Updates existing transfer
    @Override
    public Transfer updateTransfer(Transfer transfer, int id) {
        String sql = "UPDATE transfer " +
                "SET transfer_type_id = ?, transfer_status_id = ?, account_from = ?, account_to = ?, amount = ? " +
                "WHERE transfer_id = ?";

        jdbcTemplate.update(sql, transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount(), id);
        return transfer;
    }

    //Lists all transfers
    @Override
    public List<Transfer> listAllTransfers() {
        List<Transfer> transferList = new ArrayList<>();
        String sql = "select transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "from transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transferList.add(transfer);
        }
        return transferList;
    }

    //Gets a Transfer by ID and gives additional information
    @Override
    public Transfer getTransferByTransferId(int id) {
        Transfer transfer = null;
        String sql = "select transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "from transfer where transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
        if(results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }
    //Lists all pending transfers
    @Override
    public List<Transfer> listPendingTransfers(int userId) {
        List<Transfer> pendingList = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer.transfer_status_id, account_from, account_to, amount " +
                "FROM transfer " +
                "JOIN account ON account.account_id = transfer.account_from " +
                "JOIN transfer_status ON transfer.transfer_status_id = transfer_status.transfer_status_id " +
                "WHERE transfer_status_desc = 'Pending'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            pendingList.add(transfer);
        }
        return pendingList;
    }
    @Override
    public void deductFrom(int userIdFrom, BigDecimal amount) {  //Updates the account that the transfer is coming from (user inputting the transfer)
        Account transferFromAccount = new Account(); //created a from account object
        String sqlSetTransferFromAccountData = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";//setting the values using postGres searching by the id number
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSetTransferFromAccountData, userIdFrom);
        while (results.next()) {

            transferFromAccount.setAccountId(results.getInt("account_id"));
            transferFromAccount.setUserId(results.getInt("user_id"));
            transferFromAccount.setBalance(results.getBigDecimal("balance"));
        }//no helper method but is basically our mapToRowSet
        String sqlUpdateUserIdFrom = "UPDATE accounts SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(sqlUpdateUserIdFrom, transferFromAccount.getBalance().subtract(amount), userIdFrom);//updates the amount in the account it's from


    }

    @Override
    public void addMoneyTo(int userIdTo, BigDecimal amount) { // set up SQL stuff to add money to the user specified in the front end
        Account transferToAccount = new Account(); //created a from account object
        String sqlSetTransferFromAccountData = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";//setting the values using postGres searching by the id number
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSetTransferFromAccountData, userIdTo);
        while (results.next()) {

            transferToAccount.setAccountId(results.getInt("account_id"));
            transferToAccount.setUserId(results.getInt("user_id"));
            transferToAccount.setBalance(results.getBigDecimal("balance"));

            String sqlUpdateUserIdTo = "UPDATE accounts SET balance = ? WHERE user_id = ?";
            jdbcTemplate.update(sqlUpdateUserIdTo, (transferToAccount.getBalance().add(amount)), userIdTo);

        }

    }

    public Transfer addToTransferTable(Transfer newTransfer) {
        String sqlPostToTransfer = "insert into transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)"
                + "values (2, 2, ?, ?, ?)";
        jdbcTemplate.update(sqlPostToTransfer, newTransfer.getTransferTypeId(), newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
        return newTransfer;

    }


    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }
}
