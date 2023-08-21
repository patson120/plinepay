/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.MobileDepositDto;
import com.plinepay.core.entities.MobileDeposit;
import com.plinepay.core.entities.enums.StatusPayment;
import com.plinepay.core.mappers.MobileDepositMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.MobileDepositRepository;
import com.plinepay.core.services.declaration.IMobileDepositService;
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
public class MobileDepositService implements IMobileDepositService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private MobileDepositRepository mobileDepositRepository;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private MobileDepositMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    @Override
    public MobileDepositDto findMobileDepositById(UUID id) {
        return mapper.modelToDto(mobileDepositRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities MobileDeposit
     *
     * @return
     */
    @Override
    public Long getNumber() {
        return mobileDepositRepository.count();
    }

    /**
     * @return
     */
    @Override
    public List<MobileDepositDto> retrieve(PaymentSearch paymentSearch) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveMobileDeposit());
    }

    /**
     * Enregistrement -- MobileDeposit
     *
     * @param mobileDepositDto
     * @return
     */
    @Override
    public MobileDepositDto initOperation(MobileDepositDto mobileDepositDto) {

        MobileDeposit mobileDeposit = mapper.dtoToModel(mobileDepositDto);
        UtilsMethods.createID(mobileDeposit);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, mobileDeposit, Boolean.TRUE);

        mobileDeposit.setApplication(utilsComponent.findApplicationById(mobileDepositDto.getApplicationId()));

        if (mobileDepositDto.getMobileAccountId() != null) {
            mobileDeposit.setMobileAccount(utilsComponent.findMobileAccountById(mobileDepositDto.getMobileAccountId()));
        } else {
            mobileDeposit.setMobileAccount(null);
        }

        if (mobileDepositDto.getPaymentMethodId() != null) {
            mobileDeposit.setPaymentMethod(utilsComponent.findPaymentMethodById(mobileDepositDto.getPaymentMethodId()));
        } else {
            mobileDeposit.setPaymentMethod(null);
        }

        mobileDeposit.setStatus(StatusPayment.PENDING);

        mobileDeposit = mobileDepositRepository.save(mobileDeposit);

        return mapper.modelToDto(mobileDeposit);
    }

    /**
     * Delete MobileDeposit from database
     *
     * @param mobileDepositId
     * @return
     */
    @Override
    public MobileDepositDto delete(UUID mobileDepositId) {
        MobileDeposit mobileDeposit = utilsComponent.findMobileDepositById(mobileDepositId);
        mobileDepositRepository.delete(mobileDeposit);
        return mapper.modelToDto(mobileDeposit);
    }

    /**
     * Retourne les MobileDeposit mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    @Override
    public List<MobileDepositDto> retrieveMobileDepositUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveMobileDepositUpdated(lastDateUpdate));
    }

}
