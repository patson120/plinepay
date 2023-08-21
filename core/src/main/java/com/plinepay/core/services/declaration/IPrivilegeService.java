package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.PrivilegeDto;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface IPrivilegeService {

    public PrivilegeDto findPrivilegeById(UUID id);

    public Long getNumber();

    public List<PrivilegeDto> retrieveAll();

    public List<PrivilegeDto> retrieveByLimit(int firstResult, int maxResults);

    public List<PrivilegeDto> retrieveByRole(UUID roleId);

    public List<PrivilegeDto> retrieveByResource(UUID resourceId);

    public PrivilegeDto retrieveByResourceAndRole(UUID resourceId, UUID roleId);

    public PrivilegeDto create(PrivilegeDto privilegeDto);

    public PrivilegeDto update(PrivilegeDto privilegeDto);

    public PrivilegeDto delete(UUID privilegeId);

    public PrivilegeDto uncheck(UUID privilegeId);

    public PrivilegeDto check(UUID privilegeId);
}
