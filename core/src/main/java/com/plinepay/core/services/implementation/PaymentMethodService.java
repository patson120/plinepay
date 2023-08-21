/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.PaymentMethodDto;
import com.plinepay.core.entities.PaymentMethod;
import com.plinepay.core.mappers.PaymentMethodMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.PaymentMethodRepository;
import com.plinepay.core.services.declaration.IPaymentMethodService;
import com.plinepay.core.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class PaymentMethodService implements IPaymentMethodService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private PaymentMethodMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    public PaymentMethodDto findPaymentMethodById(UUID id) {
        return mapper.modelToDto(paymentMethodRepository.findById(id).orElse(null));
    }

    /**
     * Find Entity By Code
     *
     * @param code
     * @return
     */
    public PaymentMethodDto findPaymentMethodByCode(String code) {
        return mapper.modelToDto(paymentMethodRepository.findByCode(code));
    }

    /**
     * Return number of entities PaymentMethod
     *
     * @return
     */
    public Long getNumber() {
        return paymentMethodRepository.count();
    }

    /**
     * @return
     */
    public List<PaymentMethodDto> retrieveAll() {
        return mapper.modelsToDtos(entityManagerRepository.retrievePaymentMethod());
    }

    /**
     * Retourne les éléments en limitant les résultats
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<PaymentMethodDto> retrieveByLimit(int firstResult, int maxResults) {
        return mapper.modelsToDtos(entityManagerRepository.retrievePaymentMethodLimited(firstResult, maxResults));
    }

    /**
     * Enregistrement -- PaymentMethod
     *
     * @param paymentMethodDto
     * @return
     */
    public PaymentMethodDto create(PaymentMethodDto paymentMethodDto) {

        PaymentMethod paymentMethod = mapper.dtoToModel(paymentMethodDto);
        UtilsMethods.createID(paymentMethod);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, paymentMethod, Boolean.TRUE);
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return mapper.modelToDto(paymentMethod);
    }

    /**
     * Modification -- PaymentMethod
     *
     * @param paymentMethodDto
     * @return
     */
    public PaymentMethodDto update(PaymentMethodDto paymentMethodDto) {

        PaymentMethod paymentMethod = mapper.dtoToModel(paymentMethodDto);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, paymentMethod, Boolean.TRUE);
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        return mapper.modelToDto(paymentMethod);
    }

    /**
     * Delete PaymentMethod from database
     *
     * @param paymentMethodId
     * @return
     */
    public PaymentMethodDto delete(UUID paymentMethodId) {
        PaymentMethod paymentMethod = utilsComponent.findPaymentMethodById(paymentMethodId);
        paymentMethodRepository.delete(paymentMethod);
        return mapper.modelToDto(paymentMethod);
    }

    /**
     * Retourne les PaymentMethod mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    public List<PaymentMethodDto> retrievePaymentMethodUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrievePaymentMethodUpdated(lastDateUpdate));
    }

}
