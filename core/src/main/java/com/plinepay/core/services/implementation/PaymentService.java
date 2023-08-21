/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.plinepay.core.components.ProviderComponent;
import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.PaymentDto;
import com.plinepay.core.dto.PaymentMethodDto;
import com.plinepay.core.entities.enums.PaymentOperationType;
import com.plinepay.core.entities.enums.StatusPayment;
import com.plinepay.core.mappers.PaymentMethodMapper;
import com.plinepay.core.repositories.VirementRepository;
import com.plinepay.core.services.declaration.IPaymentService;
import com.plinepay.core.services.mails.EmailService;
import com.plinepay.core.utils.HttpUtils;
import com.plinepay.core.utils.OperatorData;
import com.plinepay.core.utils.ResultData;
import com.plinepay.core.utils.ServiceMessage;

import jakarta.servlet.http.HttpServletRequest;


/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private ProviderComponent providerComponent;

    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private TransferService transferService;

    @Autowired
    private OnlinePaymentService onlinePaymentService;

    @Autowired
    private MobileDepositService mobileDepositService;

    @Autowired
    private VirementService virementService;


    @Autowired
    private PaymentMethodMapper mapper;


    @Value("base.url.api.payment")
    private String baseUrlApiPayment;



    /**
     * Enregistrement -- Payment
     *
     * @param paymentId
     * @return
     */
    @Override
    public String initOnlinePayment(UUID paymentId) {
        return baseUrlApiPayment + paymentId;
    }

    /**
     * Service pour Call-Back
     *
     * @param paymentDto
     * @return
     */
    @Override
    public void handleFromOperator(PaymentDto paymentDto) {

        //ON EFFECTUE LE TRAITEMENT INDEPENDAMMENT DU TYPE DE PAIEMENT
        switch (paymentDto.getOperationType()){
            case ONLINE_PAYMENT:
                providerComponent.handleOnlinePayment(paymentDto);
                break;

            case WITHDRAWAL:
                providerComponent.handleMobileDepositPayment(paymentDto);
                break;

            case VIREMENT:
                providerComponent.handleVirementPayment(paymentDto);
                break;
        }

    }

    /**
     * Appel des Api des opérateur
     *
     * @param paymentDto
     * @return
     */
    @Override
    public void callOperatorAppi(PaymentDto paymentDto, PaymentMethodDto paymentMethodDto, String phoneNumber) {

        switch (paymentMethodDto.getCode()){
            case "MOMO":
                //... Récupérer les clés de MOMO dans la base où dans le fichier de configs et d'autres information
                String momoApiKey = "";

                if ( paymentDto.getOperationType() == PaymentOperationType.ONLINE_PAYMENT) {
                    providerComponent.callMTNOnlinePayment(paymentDto, phoneNumber, momoApiKey);
                }else if (paymentDto.getOperationType() == PaymentOperationType.WITHDRAWAL){
                    providerComponent.callMTNMobileDeposit(paymentDto, phoneNumber, momoApiKey);
                }
                break;

            //... ON fait de même pour les opérateurs
        }


    }

    public PaymentDto retrieveById(UUID paymentId, PaymentOperationType operationType){
        
        switch (operationType){
            case TRANSFER:
                return transferService.findTransferById(paymentId);

            case ONLINE_PAYMENT:
                return onlinePaymentService.findOnlinePaymentById(paymentId);

            case WITHDRAWAL:
                return mobileDepositService.findMobileDepositById(paymentId);

            case VIREMENT:
                return virementService.findVirementById(paymentId);
        }

        return null;

    }

    /**
     *
     * @param operatorData
     * @param paymentOperationType
     * @param request
     * @return
     */
    @Override
    public ResultData checkOperatotData(OperatorData operatorData, PaymentOperationType paymentOperationType, HttpServletRequest request){

        if (operatorData == null) {
            return new ResultData(new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Données non conformes, impossible de recevoir vos données"));
        }

        //On vérifie la signature et les adresses IP
        String addressIp = HttpUtils.getRequestIP(request);
        if (addressIp == null || "127.0.0.1".equals(addressIp.trim())) {
            return new ResultData(new ServiceMessage(HttpStatus.UNAUTHORIZED.value(), "Erreur : vous n'êtes pas autorisé à accéder à cette plateforme"));
        }

        PaymentDto paymentDto = retrieveById(operatorData.getAppTranscationId(), paymentOperationType);
        if (paymentDto == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver ce paiement dans le système");
            return new ResultData(message);
        }

        if ( paymentDto.getStatus() != StatusPayment.PENDING){
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Ce paiement ne peut plus être pris en compte");
            return new ResultData(message);
        }

        return new ResultData(paymentDto, new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès"));

    }
}
