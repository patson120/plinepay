package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.TraderAccountDto;

import java.util.List;
import java.util.UUID;

public interface ITraderAccountService {

    public TraderAccountDto findTraderAccountById(UUID id);

    public Long getNumber();

    public List<TraderAccountDto> retrieveAll();

    public List<TraderAccountDto> retrieveByLimit(int firstResult, int maxResults);

    public TraderAccountDto create(TraderAccountDto traderAccountDto);

    public TraderAccountDto update(TraderAccountDto traderAccountDto);

    public TraderAccountDto delete(UUID traderAccountId);

    public List<TraderAccountDto> retrieveTraderAccountUpdated(long lastDateUpdate);

    public TraderAccountDto approve(UUID traderAccountId);

    public TraderAccountDto disable(UUID traderAccountId);

    public TraderAccountDto enable(UUID traderAccountId);
}
