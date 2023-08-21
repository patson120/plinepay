package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.MobileAccountDto;

import java.util.List;
import java.util.UUID;

public interface IMobileAccountService {

    public MobileAccountDto findMobileAccountById(UUID id);

    public Long getNumber();

    public List<MobileAccountDto> retrieveAll();

    public List<MobileAccountDto> retrieveByLimit(int firstResult, int maxResults);

    public List<MobileAccountDto> retrieveByTradeAccount(UUID tradeAccountId);

    public MobileAccountDto create(MobileAccountDto mobileAccountDto);

    public MobileAccountDto update(MobileAccountDto mobileAccountDto);

    public MobileAccountDto delete(UUID mobileAccountId);

    public List<MobileAccountDto> retrieveMobileAccountUpdated(long lastDateUpdate);
}
