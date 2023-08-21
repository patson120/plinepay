/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.TransferDto;
import com.plinepay.core.entities.TraderAccount;
import com.plinepay.core.entities.Transfer;
import com.plinepay.core.entities.enums.StatusPayment;
import com.plinepay.core.mappers.TransferMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.TraderAccountRepository;
import com.plinepay.core.repositories.TransferRepository;
import com.plinepay.core.services.declaration.ITransferService;
import com.plinepay.core.services.mails.EmailService;
import com.plinepay.core.utils.PaymentSearch;
import com.plinepay.core.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.plinepay.core.Main;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class TransferService implements ITransferService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TraderAccountRepository traderAccountRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private TransferMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    @Override
    public TransferDto findTransferById(UUID id) {
        return mapper.modelToDto(transferRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities Transfer
     *
     * @return
     */
    @Override
    public Long getNumber() {
        return transferRepository.count();
    }

    /**
     * @return
     */
    @Override
    public List<TransferDto> retrieve(PaymentSearch paymentSearch) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveTransfer());
    }

    /**
     * Enregistrement -- Transfer
     *
     * @param transferDto
     * @return
     */
    @Override
    public TransferDto initOperation(TransferDto transferDto) {

        Transfer transfer = mapper.dtoToModel(transferDto);
        UtilsMethods.createID(transfer);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, transfer, Boolean.TRUE);

        transfer.setApplication(utilsComponent.findApplicationById(transferDto.getApplicationId()));
        transfer.setStatus(StatusPayment.PENDING);

        if (Boolean.FALSE.equals(UtilsMethods.isNullOrEmpty(transferDto.getTransmitterId().toString()))){
            transfer.setTransmitter(utilsComponent.findTraderAccountById(transferDto.getTransmitterId()));
        }else{
            transfer.setTransmitter(null);
        }

        if (Boolean.FALSE.equals(UtilsMethods.isNullOrEmpty(transferDto.getRecipientId().toString()))){
            transfer.setRecipient(utilsComponent.findTraderAccountById(transferDto.getRecipientId()));
        }else{
            transfer.setRecipient(null);
        }

        transfer = transferRepository.save(transfer);

        return mapper.modelToDto(transfer);
    }


    /**
     * Application -- Transfer
     *
     * @param transferDto
     * @return
     */
    @Override
    public TransferDto makeTransfer(TransferDto transferDto) {

        synchronized (Main.transferSemaphore) {
            Transfer transfer = mapper.dtoToModel(transferDto);
            UtilsMethods.loadCreationAttributes(Boolean.FALSE, transfer, Boolean.TRUE);
            transfer.setStatus(StatusPayment.SUCCESS);

            TraderAccount transmitter = utilsComponent.findTraderAccountById(transferDto.getTransmitterId());
            TraderAccount recipient = utilsComponent.findTraderAccountById(transferDto.getRecipientId());

            transmitter.setBalance(transmitter.getBalance() - transfer.getAmount());
            UtilsMethods.loadCreationAttributes(Boolean.FALSE, transmitter, Boolean.TRUE);

            recipient.setBalance(recipient.getBalance() + transfer.getAmount());
            UtilsMethods.loadCreationAttributes(Boolean.FALSE, recipient, Boolean.TRUE);

            transfer = transferRepository.save(transfer);
            traderAccountRepository.save(transmitter);
            traderAccountRepository.save(recipient);

            //NOTIFICATIONS
            //....

            return mapper.modelToDto(transfer);
        }
    }

    /**
     * Delete Transfer from database
     *
     * @param transferId
     * @return
     */
    @Override
    public TransferDto delete(UUID transferId) {
        Transfer transfer = utilsComponent.findTransferById(transferId);
        transferRepository.delete(transfer);
        return mapper.modelToDto(transfer);
    }

    /**
     * Retourne les Transfer mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    @Override
    public List<TransferDto> retrieveTransferUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveTransferUpdated(lastDateUpdate));
    }

}
