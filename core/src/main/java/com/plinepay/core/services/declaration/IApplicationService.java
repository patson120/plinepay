package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.ApplicationDto;

import java.util.List;
import java.util.UUID;

public interface IApplicationService {

    public ApplicationDto findApplicationById(UUID id);

    public ApplicationDto findApplicationByPublicKey(String publicKey);

    public Long getNumber();

    public List<ApplicationDto> retrieveAll();

    public List<ApplicationDto> retrieveByLimit(int firstResult, int maxResults);

    public List<ApplicationDto> retrieveByTradeAccount(UUID tradeAccountId);

    public ApplicationDto create(ApplicationDto applicationDto);

    public ApplicationDto update(ApplicationDto applicationDto);

    public ApplicationDto delete(UUID applicationId);

    public List<ApplicationDto> retrieveApplicationUpdated(long lastDateUpdate);

    public ApplicationDto approve(UUID applicationId);

    public ApplicationDto disable(UUID applicationId);

    public ApplicationDto enable(UUID applicationId);
}
