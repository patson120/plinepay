/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.PrivilegeDto;
import com.plinepay.core.entities.Privilege;
import com.plinepay.core.exceptions.EntityNoneExistException;
import com.plinepay.core.mappers.PrivilegeMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.PrivilegeRepository;
import com.plinepay.core.services.declaration.IPrivilegeService;
import com.plinepay.core.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class PrivilegeService implements IPrivilegeService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private PrivilegeMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    @Override
    public PrivilegeDto findPrivilegeById(UUID id) {
        return mapper.modelToDto(privilegeRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities
     *
     * @return
     */
    @Override
    public Long getNumber() {
        return privilegeRepository.count();
    }

    /**
     * @return
     */
    @Override
    public List<PrivilegeDto> retrieveAll() {
        return mapper.modelsToDtos(entityManagerRepository.retrievePrivilege());
    }

    /**
     * Retourne les éléments en limitant les résultats
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Override
    public List<PrivilegeDto> retrieveByLimit(int firstResult, int maxResults) {
        return mapper.modelsToDtos(entityManagerRepository.retrievePrivilegeLimited(firstResult, maxResults));
    }

    /**
     * @param roleId
     * @return
     */
    @Override
    public List<PrivilegeDto> retrieveByRole(UUID roleId) {
        return mapper.modelsToDtos(entityManagerRepository.retrievePrivilegeByRole(roleId));
    }

    /**
     * @param resourceId
     * @return
     */
    @Override
    public List<PrivilegeDto> retrieveByResource(UUID resourceId) {
        return mapper.modelsToDtos(entityManagerRepository.retrievePrivilegeByResource(resourceId));
    }

    /**
     * @param resourceId
     * @param roleId
     * @return
     */
    @Override
    public PrivilegeDto retrieveByResourceAndRole(UUID resourceId, UUID roleId) {
        List<Privilege> privileges = entityManagerRepository.retrievePrivilegeByResourceAndRole(resourceId, roleId);
        return (!privileges.isEmpty()) ? mapper.modelToDto(privileges.get(0)) : null;
    }

    /**
     * Enregistrement
     *
     * @param privilegeDto
     * @return
     */
    @Override
    public PrivilegeDto create(PrivilegeDto privilegeDto) {

        Privilege privilege = mapper.dtoToModel(privilegeDto);
        UtilsMethods.createID(privilege);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, privilege, Boolean.TRUE);
        privilege.setStatus(Boolean.FALSE);
        privilege.setResource(utilsComponent.findResourceById(privilegeDto.getResourceId()));
        privilege.setRole(utilsComponent.findRoleById(privilegeDto.getRoleId()));
        privilege = privilegeRepository.save(privilege);
        return mapper.modelToDto(privilege);
    }

    /**
     * Modification
     *
     * @param privilegeDto
     * @return
     */
    @Override
    public PrivilegeDto update(PrivilegeDto privilegeDto) {

        Privilege privilege = mapper.dtoToModel(privilegeDto);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, privilege, Boolean.TRUE);
        privilege.setResource(utilsComponent.findResourceById(privilegeDto.getResourceId()));
        privilege.setRole(utilsComponent.findRoleById(privilegeDto.getRoleId()));
        privilege = privilegeRepository.save(privilege);
        return mapper.modelToDto(privilege);
    }

    /**
     * Delete Privilege from database
     *
     * @param privilegeId
     * @return
     */
    @Override
    public PrivilegeDto delete(UUID privilegeId) {
        Privilege privilege = utilsComponent.findPrivilegeById(privilegeId);
        privilegeRepository.delete(privilege);
        return mapper.modelToDto(privilege);
    }

    /**
     * Désactivation d'un privilège
     *
     * @param privilegeId
     * @return
     */
    @Override
    public PrivilegeDto uncheck(UUID privilegeId) {
        Privilege privilege = utilsComponent.findPrivilegeById(privilegeId);
        if (privilege == null) {
            throw new EntityNoneExistException("Le privilège que vous avez renseigné n'existe pas dans le système");
        }
        privilege.setStatus(Boolean.FALSE);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, privilege, Boolean.TRUE);
        privilege = privilegeRepository.save(privilege);
        return mapper.modelToDto(privilege);
    }

    /**
     * Activation d'un utilisateur
     *
     * @param privilegeId
     * @return
     */
    @Override
    public PrivilegeDto check(UUID privilegeId) {
        Privilege privilege = utilsComponent.findPrivilegeById(privilegeId);
        if (privilege == null) {
            throw new EntityNoneExistException("Le privilège que vous avez renseigné n'existe pas dans le système");
        }
        privilege.setStatus(Boolean.TRUE);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, privilege, Boolean.TRUE);
        privilege = privilegeRepository.save(privilege);
        return mapper.modelToDto(privilege);
    }

}
