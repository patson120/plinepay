/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.TraderAccountDto;
import com.plinepay.core.entities.TraderAccount;
import com.plinepay.core.entities.enums.TradeAccountStatus;
import com.plinepay.core.exceptions.EntityNoneExistException;
import com.plinepay.core.mappers.TraderAccountMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.TraderAccountRepository;
import com.plinepay.core.services.declaration.ITraderAccountService;
import com.plinepay.core.services.mails.EmailService;
import com.plinepay.core.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class TraderAccountService implements ITraderAccountService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private TraderAccountRepository traderAccountRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private TraderAccountMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    @Override
    public TraderAccountDto findTraderAccountById(UUID id) {
        return mapper.modelToDto(traderAccountRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities TraderAccount
     *
     * @return
     */
    @Override
    public Long getNumber() {
        return traderAccountRepository.count();
    }

    /**
     * @return
     */
    @Override
    public List<TraderAccountDto> retrieveAll() {
        return mapper.modelsToDtos(entityManagerRepository.retrieveTraderAccount());
    }

    /**
     * Retourne les éléments en limitant les résultats
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Override
    public List<TraderAccountDto> retrieveByLimit(int firstResult, int maxResults) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveTraderAccountLimited(firstResult, maxResults));
    }

    /**
     * Enregistrement -- TraderAccount
     *
     * @param traderAccountDto
     * @return
     */
    @Override
    public TraderAccountDto create(TraderAccountDto traderAccountDto) {

        TraderAccount traderAccount = mapper.dtoToModel(traderAccountDto);
        UtilsMethods.createID(traderAccount);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, traderAccount, Boolean.TRUE);

        traderAccount.setTradeAccountStatus(TradeAccountStatus.UNDER_REVIEW);

        // Générer la clé public qui est obligatoire pour cette création
        traderAccount.setPublicKey(UtilsMethods.generateUniqueCode(4));

        traderAccount = traderAccountRepository.save(traderAccount);

        return mapper.modelToDto(traderAccount);
    }

    /**
     * Modification -- TraderAccount
     *
     * @param traderAccountDto
     * @return
     */
    @Override
    public TraderAccountDto update(TraderAccountDto traderAccountDto) {

        TraderAccount traderAccount = mapper.dtoToModel(traderAccountDto);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, traderAccount, Boolean.TRUE);

        // Récupération de la clé publique du compte
        TraderAccount lastTraderAccount = utilsComponent.findTraderAccountById(traderAccountDto.getId());
        traderAccount.setPublicKey(lastTraderAccount.getPublicKey());

        traderAccount = traderAccountRepository.save(traderAccount);
        return mapper.modelToDto(traderAccount);
    }

    /**
     * Delete TraderAccount from database
     *
     * @param traderAccountId
     * @return
     */
    @Override
    public TraderAccountDto delete(UUID traderAccountId) {
        TraderAccount traderAccount = utilsComponent.findTraderAccountById(traderAccountId);
        traderAccountRepository.delete(traderAccount);
        return mapper.modelToDto(traderAccount);
    }

    /**
     * Retourne les TraderAccount mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    @Override
    public List<TraderAccountDto> retrieveTraderAccountUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveTraderAccountUpdated(lastDateUpdate));
    }

    /**
     * Désactivation d'un compte marchand
     *
     * @param traderAccountId
     * @return
     */
    @Override
    public TraderAccountDto approve(UUID traderAccountId) {
        TraderAccount traderAccount = utilsComponent.findTraderAccountById(traderAccountId);
        if (traderAccount == null) {
            throw new EntityNoneExistException(
                    "Le compte marchand que vous avez renseigné n'existe pas dans le système");
        }
        traderAccount.setTradeAccountStatus(TradeAccountStatus.APPROVED);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, traderAccount, Boolean.TRUE);
        traderAccount = traderAccountRepository.save(traderAccount);
        return mapper.modelToDto(traderAccount);
    }

    /**
     * Désactivation d'un compte marchand
     *
     * @param traderAccountId
     * @return
     */
    @Override
    public TraderAccountDto disable(UUID traderAccountId) {
        TraderAccount traderAccount = utilsComponent.findTraderAccountById(traderAccountId);
        if (traderAccount == null) {
            throw new EntityNoneExistException(
                    "Le compte marchand que vous avez renseigné n'existe pas dans le système");
        }
        traderAccount.setTradeAccountStatus(TradeAccountStatus.DESACTIVATED);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, traderAccount, Boolean.TRUE);
        traderAccount = traderAccountRepository.save(traderAccount);
        return mapper.modelToDto(traderAccount);
    }

    /**
     * Activation d'une compte marchand
     *
     * @param traderAccountId
     * @return
     */
    @Override
    public TraderAccountDto enable(UUID traderAccountId) {
        TraderAccount traderAccount = utilsComponent.findTraderAccountById(traderAccountId);
        if (traderAccount == null) {
            throw new EntityNoneExistException(
                    "Le compte marchand que vous avez renseigné n'existe pas dans le système");
        }
        traderAccount.setTradeAccountStatus(TradeAccountStatus.ACTIVATED);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, traderAccount, Boolean.TRUE);
        traderAccount = traderAccountRepository.save(traderAccount);
        return mapper.modelToDto(traderAccount);
    }

}
