/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.RoleDto;
import com.plinepay.core.entities.Role;
import com.plinepay.core.mappers.RoleMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.RoleRepository;
import com.plinepay.core.services.declaration.IRoleService;
import com.plinepay.core.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private RoleMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    public RoleDto findRoleById(UUID id) {
        return mapper.modelToDto(roleRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities Role
     *
     * @return
     */
    public Long getNumber() {
        return roleRepository.count();
    }

    /**
     * @return
     */
    public List<RoleDto> retrieveAll() {
        return mapper.modelsToDtos(entityManagerRepository.retrieveRole());
    }

    /**
     * Retourne les éléments en limitant les résultats
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<RoleDto> retrieveByLimit(int firstResult, int maxResults) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveRoleLimited(firstResult, maxResults));
    }

    /**
     * Enregistrement -- Role
     *
     * @param roleDto
     * @return
     */
    public RoleDto create(RoleDto roleDto) {

        Role role = mapper.dtoToModel(roleDto);
        UtilsMethods.createID(role);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, role, Boolean.TRUE);
        role = roleRepository.save(role);
        return mapper.modelToDto(role);
    }

    /**
     * Modification -- Role
     *
     * @param roleDto
     * @return
     */
    public RoleDto update(RoleDto roleDto) {

        Role role = mapper.dtoToModel(roleDto);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, role, Boolean.TRUE);
        role = roleRepository.save(role);
        return mapper.modelToDto(role);
    }

    /**
     * Delete Role from database
     *
     * @param roleId
     * @return
     */
    public RoleDto delete(UUID roleId) {
        Role role = utilsComponent.findRoleById(roleId);
        roleRepository.delete(role);
        return mapper.modelToDto(role);
    }

    /**
     * Retourne les Role mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    public List<RoleDto> retrieveRoleUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveRoleUpdated(lastDateUpdate));
    }

}
