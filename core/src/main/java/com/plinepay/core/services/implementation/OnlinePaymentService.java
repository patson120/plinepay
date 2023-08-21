/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.OnlinePaymentDto;
import com.plinepay.core.entities.OnlinePayment;
import com.plinepay.core.entities.enums.StatusPayment;
import com.plinepay.core.mappers.OnlinePaymentMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.OnlinePaymentRepository;
import com.plinepay.core.services.declaration.IOnlinePaymentService;
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
public class OnlinePaymentService implements IOnlinePaymentService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private OnlinePaymentRepository onlinePaymentRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private OnlinePaymentMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    @Override
    public OnlinePaymentDto findOnlinePaymentById(UUID id) {
        return mapper.modelToDto(onlinePaymentRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities OnlinePayment
     *
     * @return
     */
    @Override
    public Long getNumber() {
        return onlinePaymentRepository.count();
    }

    /**
     * @return
     */
    @Override
    public List<OnlinePaymentDto> retrieve(PaymentSearch paymentSearch) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveOnlinePayment());
    }

    /**
     * Enregistrement -- OnlinePayment
     *
     * @param onlinePaymentDto
     * @return
     */
    @Override
    public OnlinePaymentDto initOperation(OnlinePaymentDto onlinePaymentDto) {

        OnlinePayment onlinePayment = mapper.dtoToModel(onlinePaymentDto);
        UtilsMethods.createID(onlinePayment);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, onlinePayment, Boolean.TRUE);

        onlinePayment.setApplication(utilsComponent.findApplicationById(onlinePaymentDto.getApplicationId()));
        onlinePayment.setStatus(StatusPayment.PENDING);

        onlinePayment = onlinePaymentRepository.save(onlinePayment);

        return mapper.modelToDto(onlinePayment);
    }

    /**
     * Delete OnlinePayment from database
     *
     * @param onlinePaymentId
     * @return
     */
    @Override
    public OnlinePaymentDto delete(UUID onlinePaymentId) {
        OnlinePayment onlinePayment = utilsComponent.findOnlinePaymentById(onlinePaymentId);
        onlinePaymentRepository.delete(onlinePayment);
        return mapper.modelToDto(onlinePayment);
    }

    /**
     * Retourne les OnlinePayment mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    @Override
    public List<OnlinePaymentDto> retrieveOnlinePaymentUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveOnlinePaymentUpdated(lastDateUpdate));
    }

}
