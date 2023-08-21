package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.TransferDto;
import com.plinepay.core.utils.PaymentSearch;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface ITransferService {

    public TransferDto findTransferById(UUID id);

    public Long getNumber();

    public List<TransferDto> retrieve(PaymentSearch paymentSearch);

    public TransferDto initOperation(TransferDto transferDto);

    public TransferDto delete(UUID transferId);

    List<TransferDto> retrieveTransferUpdated(long lastDateUpdate);

    public TransferDto makeTransfer(TransferDto transferDto);


}
