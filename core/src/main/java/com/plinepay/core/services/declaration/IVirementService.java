package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.VirementDto;
import com.plinepay.core.utils.PaymentSearch;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface IVirementService {

    public VirementDto findVirementById(UUID id);

    public Long getNumber();

    public List<VirementDto> retrieve(PaymentSearch paymentSearch);

    public VirementDto initOperation(VirementDto virementDto);

    public VirementDto delete(UUID virementId);

    List<VirementDto> retrieveVirementUpdated(long lastDateUpdate);

}
