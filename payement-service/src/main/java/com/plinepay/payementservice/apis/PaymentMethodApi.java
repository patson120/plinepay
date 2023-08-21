/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.payementservice.apis;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.PaymentMethodDto;
import com.plinepay.core.entities.PaymentMethod;
import com.plinepay.core.services.implementation.PaymentMethodService;
import com.plinepay.core.utils.ErrorService;
import com.plinepay.core.utils.HeaderParameter;
import com.plinepay.core.utils.ServiceMessage;
import com.plinepay.core.utils.WebData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author Patrick KENNE
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/paymentmethods/")
public class PaymentMethodApi {

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private PaymentMethodService paymentMethodService;

    /**
     * Retourner la liste de tous les paymentmethods
     *
     * @param headerParameter
     * @return
     */
    @RequestMapping(value = "/retrieve/all", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveAll(@RequestBody HeaderParameter headerParameter) {

        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setMessage(message);
        webData.setOperationDate(System.currentTimeMillis());
        webData.getPaymentMethodDtos().addAll(paymentMethodService.retrieveAll());
        webData.setNumberElement(Long.valueOf(webData.getPaymentMethodDtos().size()));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste des paymentmethods limités par pagination (Limit des résultats)
     *
     * @param headerParameter
     * @return
     */
    @PostMapping(value = "/retrieve/limit")
    public ResponseEntity<WebData> retrievePaymentMethodLimited(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        webData.getPaymentMethodDtos().addAll(paymentMethodService.retrieveByLimit(headerParameter.getFirstResult(), headerParameter.getNumberElement()));
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner le nombre de paymentmethods
     *
     * @param headerParameter
     * @return
     */
    @PostMapping(value = "/count")
    public ResponseEntity<WebData> countNumber(@RequestBody HeaderParameter headerParameter) {

        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setMessage(message);
        webData.setNumberElement(paymentMethodService.getNumber());

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Ajout de paymentMethod
     *
     * @param paymentMethodDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> add(@RequestBody PaymentMethodDto paymentMethodDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, paymentMethodDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible d'enregistrer ses données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        paymentMethodDto = paymentMethodService.create(paymentMethodDto);
        webData.getPaymentMethodDtos().add(paymentMethodDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.CREATED);
    }

    /**
     * Modification de paymentMethod
     *
     * @param paymentMethodDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> update(@RequestBody PaymentMethodDto paymentMethodDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        PaymentMethod paymentMethod = utilsComponent.findPaymentMethodById(paymentMethodDto.getId());
        if (paymentMethod == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cette donnée sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, paymentMethodDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible d'enregistrer vos données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        paymentMethodDto = paymentMethodService.update(paymentMethodDto);
        webData.getPaymentMethodDtos().add(paymentMethodDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Suppression de paymentMethod
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> delete(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        PaymentMethod paymentMethod = utilsComponent.findPaymentMethodById(headerParameter.getEntityId());
        if (paymentMethod == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cette donnée sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        PaymentMethodDto paymentMethodDto = paymentMethodService.delete(headerParameter.getEntityId());
        webData.getPaymentMethodDtos().add(paymentMethodDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.NO_CONTENT.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Téléchargement des informations d'une méthode de paiement
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/retrieve/one", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveOne(@RequestBody HeaderParameter headerParameter) {
        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        if (headerParameter.getEntityId() != null) {
            PaymentMethodDto paymentMethod = paymentMethodService.findPaymentMethodById(headerParameter.getEntityId());
            if (paymentMethod != null) {
                webData.getPaymentMethodDtos().add(paymentMethod);
                ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
                webData.setMessage(message);
                return new ResponseEntity<>(webData, HttpStatus.OK);
            } else {
                ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver une méthode de paiement avec cet identifiant");
                webData.setMessage(message);
                return new ResponseEntity<>(webData, HttpStatus.OK);
            }
        } else {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Bien vouloir renseigner l'identifiant du rôle");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }
    }

}
