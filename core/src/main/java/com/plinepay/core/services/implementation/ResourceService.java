/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.ResourceDto;
import com.plinepay.core.entities.Resource;
import com.plinepay.core.mappers.ResourceMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.ResourceRepository;
import com.plinepay.core.services.declaration.IResourceService;
import com.plinepay.core.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class ResourceService implements IResourceService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private ResourceRepository resourceRepository;
    
    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private ResourceMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    public ResourceDto findResourceById(UUID id) {
        return mapper.modelToDto(resourceRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities
     *
     * @return
     */
    public Long getNumber() {
        return resourceRepository.count();
    }

    /**
     * @return
     */
    public List<ResourceDto> retrieveAll() {
        return mapper.modelsToDtos(entityManagerRepository.retrieveResource());
    }

    /**
     * Retourne les éléments en limitant les résultats
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<ResourceDto> retrieveByLimit(int firstResult, int maxResults) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveResourceLimited(firstResult, maxResults));
    }

    /**
     * Enregistrement
     *
     * @param resourceDto
     * @return
     */
    public ResourceDto create(ResourceDto resourceDto) {

        Resource resource = mapper.dtoToModel(resourceDto);
        UtilsMethods.createID(resource);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, resource, Boolean.TRUE);
        resource = resourceRepository.save(resource);
        return mapper.modelToDto(resource);
    }

    /**
     * Modification
     *
     * @param resourceDto
     * @return
     */
    public ResourceDto update(ResourceDto resourceDto) {

        Resource resource = mapper.dtoToModel(resourceDto);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, resource, Boolean.TRUE);
        resource = resourceRepository.save(resource);
        return mapper.modelToDto(resource);
    }

    /**
     * Delete Resource from database
     *
     * @param resourceId
     * @return 
     */
    public ResourceDto delete(UUID resourceId) {
        Resource resource = utilsComponent.findResourceById(resourceId);
        resourceRepository.delete(resource);
        return mapper.modelToDto(resource);
    }

}
