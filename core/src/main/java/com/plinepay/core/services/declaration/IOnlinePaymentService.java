package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.OnlinePaymentDto;
import com.plinepay.core.utils.PaymentSearch;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface IOnlinePaymentService {

    public OnlinePaymentDto findOnlinePaymentById(UUID id);

    public Long getNumber();

    public List<OnlinePaymentDto> retrieve(PaymentSearch paymentSearch);

    public OnlinePaymentDto initOperation(OnlinePaymentDto paymentDto);

    public OnlinePaymentDto delete(UUID paymentId);

    List<OnlinePaymentDto> retrieveOnlinePaymentUpdated(long lastDateUpdate);

}
