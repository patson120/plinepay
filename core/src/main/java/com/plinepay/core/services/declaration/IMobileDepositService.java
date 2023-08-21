package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.MobileDepositDto;
import com.plinepay.core.utils.PaymentSearch;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface IMobileDepositService {

    public MobileDepositDto findMobileDepositById(UUID id);

    public Long getNumber();

    public List<MobileDepositDto> retrieve(PaymentSearch paymentSearch);

    public MobileDepositDto initOperation(MobileDepositDto mobileDepositDto);

    public MobileDepositDto delete(UUID mobileDepositId);

    List<MobileDepositDto> retrieveMobileDepositUpdated(long lastDateUpdate);

}
