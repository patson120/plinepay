package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.PaymentMethodDto;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface IPaymentMethodService {

    public PaymentMethodDto findPaymentMethodById(UUID id);

    public Long getNumber();

    public List<PaymentMethodDto> retrieveAll();

    public List<PaymentMethodDto> retrieveByLimit(int firstResult, int maxResults);

    public PaymentMethodDto create(PaymentMethodDto paymentMethodDto);

    public PaymentMethodDto update(PaymentMethodDto paymentMethodDto);

    public PaymentMethodDto delete(UUID paymentMethodId);

    public List<PaymentMethodDto> retrievePaymentMethodUpdated(long lastDateUpdate);
    
}
