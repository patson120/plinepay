/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.payementservice.apis;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.plinepay.core.dto.ApplicationDto;
import com.plinepay.core.dto.OnlinePaymentDto;
import com.plinepay.core.dto.PaymentDto;
import com.plinepay.core.dto.PaymentMethodDto;
import com.plinepay.core.dto.TraderAccountDto;
import com.plinepay.core.dto.TransferDto;
import com.plinepay.core.entities.enums.PaymentOperationType;
import com.plinepay.core.entities.enums.StatusApplication;
import com.plinepay.core.entities.enums.TransferType;
import com.plinepay.core.services.implementation.ApplicationService;
import com.plinepay.core.services.implementation.OnlinePaymentService;
import com.plinepay.core.services.implementation.PaymentMethodService;
import com.plinepay.core.services.implementation.PaymentService;
import com.plinepay.core.services.implementation.TraderAccountService;
import com.plinepay.core.services.implementation.TransferService;
import com.plinepay.core.utils.ApiParam;
import com.plinepay.core.utils.DataUtils;
import com.plinepay.core.utils.HeaderPayment;
import com.plinepay.core.utils.OperatorData;
import com.plinepay.core.utils.PaymentSearch;
import com.plinepay.core.utils.PaymentUrlData;
import com.plinepay.core.utils.RequestPaymentData;
import com.plinepay.core.utils.ResultData;
import com.plinepay.core.utils.ServiceMessage;
import com.plinepay.core.utils.TransferData;
import com.plinepay.core.utils.UtilsMethods;
import com.plinepay.core.utils.WebData;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Patrick KENNE
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/payments/")
public class PaymentApi {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OnlinePaymentService onlinePaymentService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private TraderAccountService traderAccountService;

    @Autowired
    private TransferService transferService;

    /**
     * Rechercher les paiements dans le système
     *
     * @param paymentSearch
     * @return
     */
    @PostMapping(value = "/retrieve")
    public ResponseEntity<WebData> retrieve(@RequestBody PaymentSearch paymentSearch) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        //.. A IMPLEMENTER

        // webData.getPaymentDtos().addAll(paymentService.);

        webData.setNumberElement(Long.valueOf(webData.getUserDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Génération de l'url pour le paiement en ligne
     *
     * @param requestPaymentData
     * @return
     */
    @RequestMapping(value = "/{publicKey}/init/online", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<PaymentUrlData> initOnlinePayment(
            @RequestBody RequestPaymentData requestPaymentData,
            @PathVariable(name = "publicKey", required = true) String publicKey
    ) {

        PaymentUrlData paymentUrlData = new PaymentUrlData();
        paymentUrlData.setOperationDate(System.currentTimeMillis());

        if (Boolean.TRUE.equals(UtilsMethods.isNullOrEmpty(publicKey))) {
            //La clé public n'a pas été trouvée
            ServiceMessage message = new ServiceMessage(2, "Bien-vouloir renseigner la clé publique");
            paymentUrlData.setMessage(message);
            return new ResponseEntity<>(paymentUrlData, HttpStatus.CREATED);
        }

        ApplicationDto applicationDto = applicationService.findApplicationByPublicKey(publicKey);

        if (applicationDto == null) {
            //La clé public n'a pas été trouvée
            ServiceMessage message = new ServiceMessage(2, "Impossible de trouver cette application dans le système");
            paymentUrlData.setMessage(message);
            return new ResponseEntity<>(paymentUrlData, HttpStatus.CREATED);
        }

        if (applicationDto.getStatusApplication() != StatusApplication.ACTIVATED && applicationDto.getStatusApplication() != StatusApplication.APPROVED) {
            //La clé public n'a pas été trouvée
            ServiceMessage message = new ServiceMessage(2, "Compte inactif, veuillez-contacter l'administrateur");
            paymentUrlData.setMessage(message);
            return new ResponseEntity<>(paymentUrlData, HttpStatus.CREATED);
        }

        //On contrôle aussi l'objet requestPaymentData
        if (Boolean.TRUE.equals(UtilsMethods.isNullOrEmpty(requestPaymentData.getAppTransactionRef().toString()))){
            //Veuillez-présicer l'identifiant unique de la transaction au niveau de votre système
            ServiceMessage message = new ServiceMessage(2, "Veuillez-présicer l'identifiant unique de la transaction au niveau de votre système");
            paymentUrlData.setMessage(message);
            return new ResponseEntity<>(paymentUrlData, HttpStatus.CREATED);
        }

        //... Autres contrôles

        OnlinePaymentDto onlinePaymentDto = new OnlinePaymentDto();
        DataUtils.transferData(requestPaymentData, onlinePaymentDto);

        //On initialise le paiement
        onlinePaymentDto = onlinePaymentService.initOperation(onlinePaymentDto);

        //Génération du lien de paiement
        String paymentUrl = paymentService.initOnlinePayment(onlinePaymentDto.getId());

        paymentUrlData.setPaymentUrl(paymentUrl);
        paymentUrlData.setTransactionId(onlinePaymentDto.getId());
        ServiceMessage message = new ServiceMessage(0, "Opération effectuée avec succès");
        paymentUrlData.setStatus(onlinePaymentDto.getStatus().toString().toLowerCase());
        paymentUrlData.setMessage(message);
        return new ResponseEntity<>(paymentUrlData, HttpStatus.CREATED);
    }


    /**
     * Réception des paiements en ligne venant de l'opérateur
     * API CallBack pour les opérateurs -- Paiement
     *
     * @param operatorData
     * @return
     */
    @RequestMapping(value = "/online/handle", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ServiceMessage> handleOnlinePayment(
            @RequestBody OperatorData operatorData,
            HttpServletRequest request
    ) {

        //Contrôle des données reçues des opérateurs
        ResultData resultData = paymentService.checkOperatotData(operatorData, PaymentOperationType.ONLINE_PAYMENT, request);
        if ( resultData.getMessage().getCode() != 0 ){
            return new ResponseEntity<>(resultData.getMessage(), HttpStatus.OK);
        }

        DataUtils.transferData(PaymentOperationType.ONLINE_PAYMENT, operatorData, resultData.getPaymentDto());

        //On effectue le traitement après le paiement
        paymentService.handleFromOperator(resultData.getPaymentDto());

        ServiceMessage message = new ServiceMessage(0, "Opération effectuée avec succès");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    /**
     * Réception des dépôts Mobile Money venant de l'opérateur
     * API CallBack pour les opérateurs -- Dépôt
     *
     * @param operatorData
     * @return
     */
    @RequestMapping(value = "/deposit/handle", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ServiceMessage> handleDepositPayment(
            @RequestBody OperatorData operatorData,
            HttpServletRequest request
    ) {

        //Contrôle des données reçues des opérateurs
        ResultData resultData = paymentService.checkOperatotData(operatorData, PaymentOperationType.WITHDRAWAL, request);
        if ( resultData.getMessage().getCode() != HttpStatus.OK.value() ){
            return new ResponseEntity<>(resultData.getMessage(), HttpStatus.OK);
        }

        DataUtils.transferData(PaymentOperationType.WITHDRAWAL, operatorData, resultData.getPaymentDto());

        //On effectue le traitement après le paiement
        paymentService.handleFromOperator(resultData.getPaymentDto());

        ServiceMessage message = new ServiceMessage(0, "Opération effectuée avec succès");
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


    /**
     * Réception des dépôts Mobile Money venant de l'opérateur
     * API CallBack pour les opérateurs -- Dépôt
     *
     * @param operatorData
     * @return
     */
    @RequestMapping(value = "/virement/handle", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ServiceMessage> handleVirementPayment(
            @RequestBody OperatorData operatorData,
            HttpServletRequest request
    ) {

        //Contrôle des données reçues des opérateurs
        ResultData resultData = paymentService.checkOperatotData(operatorData, PaymentOperationType.VIREMENT, request);
        if ( resultData.getMessage().getCode() != 0 ){
            return new ResponseEntity<>(resultData.getMessage(), HttpStatus.OK);
        }

        DataUtils.transferData(PaymentOperationType.WITHDRAWAL, operatorData, resultData.getPaymentDto());

        //On effectue le traitement après le paiement
        paymentService.handleFromOperator(resultData.getPaymentDto());

        ServiceMessage message = new ServiceMessage(0, "Opération effectuée avec succès");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     * Transfert compte à compte
     *
     * @param transferData
     * @return
     */
    @RequestMapping(value = "/{transmitterId}/proceed/transfer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ServiceMessage> proceedTransfer(
            @RequestBody TransferData transferData,
            @PathVariable(name = "transmitterId", required = true) UUID transmitterId,
            HttpServletRequest request
    ) {

        //Contrôle des données
        if ( Boolean.TRUE.equals(UtilsMethods.isNullOrEmpty(transferData.getRecipient().toString())) ){
            return new ResponseEntity<>(new ServiceMessage(2, "Veuillez-renseigner un destinataire"), HttpStatus.OK);
        }

        TraderAccountDto transmitter = traderAccountService.findTraderAccountById(transmitterId);
        if ( transmitter == null ){
            return new ResponseEntity<>(new ServiceMessage(2, "Compte inconnu, impossible d'éffectuer l'opération"), HttpStatus.OK);
        }

        if ( transmitter.getBalance() < transferData.getAmount() ){
            return new ResponseEntity<>(new ServiceMessage(2, "Solde insuffisant, impossible d'éffectuer l'opération"), HttpStatus.OK);
        }

        if ( Boolean.TRUE.equals(UtilsMethods.isNullOrEmpty(transferData.getRecipient().toString())) ){
            return new ResponseEntity<>(new ServiceMessage(2, "Veuillez-renseigner un destinataire"), HttpStatus.OK);
        }

        TraderAccountDto recipient = traderAccountService.findTraderAccountById(transferData.getRecipient());
        if ( recipient == null ){
            return new ResponseEntity<>(new ServiceMessage(2, "Destinataire inconnu, impossible d'éffectuer l'opération"), HttpStatus.OK);
        }

        TransferDto transferDto = new TransferDto();
        transferDto.setTransferType(TransferType.OUTGOING);
        DataUtils.transferData(transmitterId, transferData, transferDto);

        //On effectue le traitement après le paiement
        transferDto = transferService.initOperation(transferDto);

        //On effectue le transfert
        transferDto = transferService.makeTransfer(transferDto);

        ServiceMessage message = new ServiceMessage(0, "Opération effectuée avec succès");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    /**
     * Retrouver les informations d'un paiement
     *
     * @param headerPayment
     * @return (WebData)
     */
    @RequestMapping(value = "/retrieve/one", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveOne(@RequestBody HeaderPayment headerPayment) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        if (headerPayment.getTransactionId() != null && !UtilsMethods.isNullOrEmpty(headerPayment.getTransactionId().toString())) {
            PaymentDto payment = paymentService.retrieveById(headerPayment.getTransactionId(), headerPayment.getOperationType());
            if (payment != null) {
                webData.getPaymentDtos().add(payment);
                ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
                webData.setMessage(message);
            } else {
                ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver un paiement dans la base");
                webData.setMessage(message);
            }
        } else {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Bien vouloir renseigner l'identifiant de du paiement");
            webData.setMessage(message);
        }
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Appel des Apis des opérateurs ou banques ....
     *
     * @param apiParam
     * @return
     */
    @RequestMapping(value = "/proceed/online/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<ServiceMessage> proceedApiCall(
            @RequestBody ApiParam apiParam
    ) {

        //**** Cette Api est à implémenter totalement *****

        //On éffectue les contrôles

        PaymentDto paymentDto = paymentService.retrieveById(apiParam.getTransactionId(), PaymentOperationType.ONLINE_PAYMENT);
        PaymentMethodDto paymentMethodDto = paymentMethodService.findPaymentMethodByCode(apiParam.getPaymentMethodCode());
        paymentService.callOperatorAppi(paymentDto, paymentMethodDto, apiParam.getPhoneNumber());

        ServiceMessage message = new ServiceMessage(0, "Opération effectuée avec succès");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
