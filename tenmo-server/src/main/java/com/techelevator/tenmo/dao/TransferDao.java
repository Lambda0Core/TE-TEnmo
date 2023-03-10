package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface TransferDao {

    List<Transfer> listAllTransfers();
    Transfer createTransfer(Transfer transfer);
    Transfer updateTransfer(Transfer transfer);
    List<Transfer> listPendingTransfers(int userId);
    Transfer getTransferByTransferId(int transferId); //transfer details

}
