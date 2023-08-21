package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.RoleDto;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface IRoleService {

    public RoleDto findRoleById(UUID id);

    public Long getNumber();

    public List<RoleDto> retrieveAll();

    public List<RoleDto> retrieveByLimit(int firstResult, int maxResults);

    public RoleDto create(RoleDto roleDto);

    public RoleDto update(RoleDto roleDto);

    public RoleDto delete(UUID roleId);

    public List<RoleDto> retrieveRoleUpdated(long lastDateUpdate);

}
