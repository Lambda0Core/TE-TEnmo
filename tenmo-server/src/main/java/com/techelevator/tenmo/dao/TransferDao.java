package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> listAllTransfers();
    Transfer startTransfer(Transfer transfer);
    Transfer updateTransfer(Transfer transfer);
    List<Transfer> listPendingTransfers(int userId);
    Transfer getTransferByTransferId(int transferId); //transfer details

}
