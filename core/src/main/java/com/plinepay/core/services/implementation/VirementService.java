/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.VirementDto;
import com.plinepay.core.entities.Virement;
import com.plinepay.core.entities.enums.StatusPayment;
import com.plinepay.core.mappers.VirementMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.VirementRepository;
import com.plinepay.core.services.declaration.IVirementService;
import com.plinepay.core.services.mails.EmailService;
import com.plinepay.core.utils.PaymentSearch;
import com.plinepay.core.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class VirementService implements IVirementService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private VirementMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    @Override
    public VirementDto findVirementById(UUID id) {
        return mapper.modelToDto(virementRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities Virement
     *
     * @return
     */
    @Override
    public Long getNumber() {
        return virementRepository.count();
    }

    /**
     * @return
     */
    @Override
    public List<VirementDto> retrieve(PaymentSearch paymentSearch) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveVirement());
    }

    /**
     * Enregistrement -- Virement
     *
     * @param virementDto
     * @return
     */
    @Override
    public VirementDto initOperation(VirementDto virementDto) {

        Virement virement = mapper.dtoToModel(virementDto);
        UtilsMethods.createID(virement);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, virement, Boolean.TRUE);

        virement.setApplication(utilsComponent.findApplicationById(virementDto.getApplicationId()));
        virement.setStatus(StatusPayment.PENDING);

        virement = virementRepository.save(virement);

        return mapper.modelToDto(virement);
    }

    /**
     * Delete Virement from database
     *
     * @param virementId
     * @return
     */
    @Override
    public VirementDto delete(UUID virementId) {
        Virement virement = utilsComponent.findVirementById(virementId);
        virementRepository.delete(virement);
        return mapper.modelToDto(virement);
    }

    /**
     * Retourne les Virement mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    @Override
    public List<VirementDto> retrieveVirementUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveVirementUpdated(lastDateUpdate));
    }

}
