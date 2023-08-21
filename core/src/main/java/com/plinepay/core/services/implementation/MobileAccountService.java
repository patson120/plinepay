/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.MobileAccountDto;
import com.plinepay.core.entities.MobileAccount;
import com.plinepay.core.entities.TraderAccount;
import com.plinepay.core.entities.enums.MobileAccountStatus;
import com.plinepay.core.mappers.MobileAccountMapper;
import com.plinepay.core.repositories.MobileAccountRepository;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.services.declaration.IMobileAccountService;
import com.plinepay.core.services.mails.EmailService;
import com.plinepay.core.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class MobileAccountService implements IMobileAccountService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private MobileAccountRepository mobileAccountRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private MobileAccountMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    @Override
    public MobileAccountDto findMobileAccountById(UUID id) {
        return mapper.modelToDto(mobileAccountRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities MobileAccount
     *
     * @return
     */
    @Override
    public Long getNumber() {
        return mobileAccountRepository.count();
    }

    /**
     * @return
     */
    @Override
    public List<MobileAccountDto> retrieveAll() {
        return mapper.modelsToDtos(entityManagerRepository.retrieveMobileAccount());
    }

    /**
     * Retourne les éléments en limitant les résultats
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Override
    public List<MobileAccountDto> retrieveByLimit(int firstResult, int maxResults) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveMobileAccountLimited(firstResult, maxResults));
    }

    /**
     * Recherche des mobileAccount par compte marchand
     * @param tradeAccountId
     * @return
     */
    @Override
    public List<MobileAccountDto> retrieveByTradeAccount(UUID tradeAccountId) {
        TraderAccount traderAccount = utilsComponent.findTraderAccountById(tradeAccountId);
        return mapper.modelsToDtos(mobileAccountRepository.findByTraderAccount(traderAccount));
    }

    /**
     * Enregistrement -- MobileAccount
     *
     * @param mobileAccountDto
     * @return
     */
    @Override
    public MobileAccountDto create(MobileAccountDto mobileAccountDto) {

        MobileAccount mobileAccount = mapper.dtoToModel(mobileAccountDto);
        UtilsMethods.createID(mobileAccount);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, mobileAccount, Boolean.TRUE);

        if (mobileAccountDto.getPaymentMethodId() != null) {
            mobileAccount.setPaymentMethod(utilsComponent.findPaymentMethodById(mobileAccountDto.getPaymentMethodId()));
        } else {
            mobileAccount.setPaymentMethod(null);
        }

        if (mobileAccountDto.getTraderAccountId() != null) {
            mobileAccount.setTraderAccount(utilsComponent.findTraderAccountById(mobileAccountDto.getTraderAccountId()));
        } else {
            mobileAccount.setTraderAccount(null);
        }

        mobileAccount.setMobileAccountStatus(MobileAccountStatus.ACTIVE);

        mobileAccount = mobileAccountRepository.save(mobileAccount);

        return mapper.modelToDto(mobileAccount);
    }

    /**
     * Modification -- MobileAccount
     *
     * @param mobileAccountDto
     * @return
     */
    @Override
    public MobileAccountDto update(MobileAccountDto mobileAccountDto) {

        MobileAccount mobileAccount = mapper.dtoToModel(mobileAccountDto);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, mobileAccount, Boolean.TRUE);

        if (mobileAccountDto.getPaymentMethodId() != null) {
            mobileAccount.setPaymentMethod(utilsComponent.findPaymentMethodById(mobileAccountDto.getPaymentMethodId()));
        } else {
            mobileAccount.setPaymentMethod(null);
        }

        if (mobileAccountDto.getTraderAccountId() != null) {
            mobileAccount.setTraderAccount(utilsComponent.findTraderAccountById(mobileAccountDto.getTraderAccountId()));
        } else {
            mobileAccount.setTraderAccount(null);
        }

        mobileAccount = mobileAccountRepository.save(mobileAccount);
        return mapper.modelToDto(mobileAccount);
    }

    /**
     * Delete MobileAccount from database
     *
     * @param mobileAccountId
     * @return
     */
    @Override
    public MobileAccountDto delete(UUID mobileAccountId) {
        MobileAccount mobileAccount = utilsComponent.findMobileAccountById(mobileAccountId);
        mobileAccountRepository.delete(mobileAccount);
        return mapper.modelToDto(mobileAccount);
    }

    /**
     * Retourne les MobileAccount mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    @Override
    public List<MobileAccountDto> retrieveMobileAccountUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveMobileAccountUpdated(lastDateUpdate));
    }

}
