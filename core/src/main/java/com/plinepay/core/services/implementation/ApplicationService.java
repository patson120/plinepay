/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.ApplicationDto;
import com.plinepay.core.entities.Application;
import com.plinepay.core.entities.TraderAccount;
import com.plinepay.core.entities.enums.StatusApplication;
import com.plinepay.core.exceptions.EntityNoneExistException;
import com.plinepay.core.mappers.ApplicationMapper;
import com.plinepay.core.repositories.ApplicationRepository;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.services.declaration.IApplicationService;
import com.plinepay.core.utils.UtilsMethods;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class ApplicationService implements IApplicationService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private ApplicationMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    @Override
    public ApplicationDto findApplicationById(UUID id) {
        return mapper.modelToDto(applicationRepository.findById(id).orElse(null));
    }

    @Override
    public ApplicationDto findApplicationByPublicKey(String publicKey) {
        return mapper.modelToDto(applicationRepository.findByPublicKey(publicKey));
    }

    /**
     * Return number of entities Application
     *
     * @return
     */
    @Override
    public Long getNumber() {
        return applicationRepository.count();
    }

    /**
     * @return
     */
    @Override
    public List<ApplicationDto> retrieveAll() {
        return mapper.modelsToDtos(entityManagerRepository.retrieveApplication());
    }

    /**
     * Retourne les éléments en limitant les résultats
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Override
    public List<ApplicationDto> retrieveByLimit(int firstResult, int maxResults) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveApplicationLimited(firstResult, maxResults));
    }

    /**
     * Recherche des application par compte marchand
     * 
     * @param tradeAccountId
     * @return
     */
    @Override
    public List<ApplicationDto> retrieveByTradeAccount(UUID tradeAccountId) {
        TraderAccount traderAccount = utilsComponent.findTraderAccountById(tradeAccountId);
        return mapper.modelsToDtos(applicationRepository.findByTraderAccount(traderAccount));
    }

    /**
     * Enregistrement -- Application
     *
     * @param applicationDto
     * @return
     */
    @Override
    public ApplicationDto create(ApplicationDto applicationDto) {

        Application application = mapper.dtoToModel(applicationDto);
        UtilsMethods.createID(application);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, application, Boolean.TRUE);

        if (applicationDto.getTraderAccountId() != null) {
            application.setTraderAccount(utilsComponent.findTraderAccountById(applicationDto.getTraderAccountId()));
        } else {
            application.setTraderAccount(null);
        }

        application.setStatusApplication(StatusApplication.UNDER_REVIEW);

        try {
            application = applicationRepository.save(application);
        } catch (Exception e) {
        }
        return mapper.modelToDto(application);
    }

    /**
     * Modification -- Application
     *
     * @param applicationDto
     * @return
     */
    @Override
    public ApplicationDto update(ApplicationDto applicationDto) {

        Application application = mapper.dtoToModel(applicationDto);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, application, Boolean.TRUE);

        if (applicationDto.getTraderAccountId() != null) {
            application.setTraderAccount(utilsComponent.findTraderAccountById(applicationDto.getTraderAccountId()));
        } else {
            application.setTraderAccount(null);
        }
        application = applicationRepository.save(application);
        return mapper.modelToDto(application);
    }

    /**
     * Delete Application from database
     *
     * @param applicationId
     * @return
     */
    @Override
    public ApplicationDto delete(UUID applicationId) {
        Application application = utilsComponent.findApplicationById(applicationId);
        applicationRepository.delete(application);
        return mapper.modelToDto(application);
    }

    /**
     * Retourne les Application mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    @Override
    public List<ApplicationDto> retrieveApplicationUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveApplicationUpdated(lastDateUpdate));
    }

    /**
     * Désactivation d'une application
     *
     * @param applicationId
     * @return
     */
    @Override
    public ApplicationDto approve(UUID applicationId) {
        Application application = utilsComponent.findApplicationById(applicationId);
        if (application == null) {
            throw new EntityNoneExistException("L'application que vous avez renseigné n'existe pas dans le système");
        }
        application.setStatusApplication(StatusApplication.APPROVED);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, application, Boolean.TRUE);
        application = applicationRepository.save(application);
        return mapper.modelToDto(application);
    }

    /**
     * Désactivation d'un application
     *
     * @param applicationId
     * @return
     */
    @Override
    public ApplicationDto disable(UUID applicationId) {
        Application application = utilsComponent.findApplicationById(applicationId);
        if (application == null) {
            throw new EntityNoneExistException("L'application que vous avez renseigné n'existe pas dans le système");
        }
        application.setStatusApplication(StatusApplication.DESACTIVATED);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, application, Boolean.TRUE);
        application = applicationRepository.save(application);
        return mapper.modelToDto(application);
    }

    /**
     * Activation d'un application
     *
     * @param applicationId
     * @return
     */
    @Override
    public ApplicationDto enable(UUID applicationId) {
        Application application = utilsComponent.findApplicationById(applicationId);
        if (application == null) {
            throw new EntityNoneExistException("L'application que vous avez renseigné n'existe pas dans le système");
        }
        application.setStatusApplication(StatusApplication.ACTIVATED);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, application, Boolean.TRUE);
        application = applicationRepository.save(application);
        return mapper.modelToDto(application);
    }

}
