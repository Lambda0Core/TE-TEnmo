package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> listAllTransfers();
    Transfer sendMoney(Transfer transfer);

    Transfer getTransferByTransferId(int transferId); //transfer details

}
