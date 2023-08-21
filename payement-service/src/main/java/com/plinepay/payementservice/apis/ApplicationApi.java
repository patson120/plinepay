package com.plinepay.payementservice.apis;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.ApplicationDto;
import com.plinepay.core.entities.Application;
import com.plinepay.core.services.implementation.ApplicationService;
import com.plinepay.core.utils.*;
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
@RequestMapping("/api/applications/")
public class ApplicationApi {

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private ApplicationService applicationService;

    /**
     * Retourner la liste de tous les applications
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
        webData.getApplicationDtos().addAll(applicationService.retrieveAll());
        webData.setNumberElement(Long.valueOf(webData.getApplicationDtos().size()));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste de tous les applications par compte marchand
     *
     * @param headerParameter
     * @return
     */
    @RequestMapping(value = "/retrieve/trader", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveByTrader(@RequestBody HeaderParameter headerParameter) {

        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setMessage(message);
        webData.setOperationDate(System.currentTimeMillis());
        webData.getApplicationDtos().addAll(applicationService.retrieveByTradeAccount(headerParameter.getEntityId()));
        webData.setNumberElement(Long.valueOf(webData.getApplicationDtos().size()));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste des applications limités par pagination (Limit des
     * résultats)
     *
     * @param headerParameter
     * @return
     */
    @PostMapping(value = "/retrieve/limit")
    public ResponseEntity<WebData> retrieveApplicationLimited(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        webData.getApplicationDtos().addAll(applicationService.retrieveByLimit(headerParameter.getFirstResult(),
                headerParameter.getNumberElement()));
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner le nombre de applications
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
        webData.setNumberElement(applicationService.getNumber());

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Ajout de application
     *
     * @param applicationDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> add(@RequestBody ApplicationDto applicationDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, applicationDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible d'enregistrer ses données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        applicationDto = applicationService.create(applicationDto);
        webData.getApplicationDtos().add(applicationDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Modification de application
     *
     * @param applicationDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> update(@RequestBody ApplicationDto applicationDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        ApplicationDto applicationDto1 = applicationService.findApplicationById(applicationDto.getId());
        if (applicationDto1 == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cet application dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, applicationDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible d'enregistrer vos données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        applicationDto = applicationService.update(applicationDto);
        webData.getApplicationDtos().add(applicationDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Suprression de application
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> delete(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        Application application = utilsComponent.findApplicationById(headerParameter.getEntityId());
        if (application == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cette donnée sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ApplicationDto applicationDto = applicationService.delete(headerParameter.getEntityId());
        webData.getApplicationDtos().add(applicationDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Téléchargement des informations d'une application
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/retrieve/one", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveOne(@RequestBody HeaderParameter headerParameter) {
        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        if (headerParameter.getEntityId() != null) {
            ApplicationDto application = applicationService.findApplicationById(headerParameter.getEntityId());
            if (application != null) {
                webData.getApplicationDtos().add(application);
                ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
                webData.setMessage(message);
                return new ResponseEntity<>(webData, HttpStatus.OK);
            } else {
                ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                        "Impossible de trouver une application avec cet identifiant");
                webData.setMessage(message);
                return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
            }
        } else {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Bien vouloir renseigner l'identifiant de l'application");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Activation d'une application
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/enable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> active(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        Application u = utilsComponent.findApplicationById(headerParameter.getEntityId());
        if (u == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "L'application renseignée n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ApplicationDto applicationDto = applicationService.enable(headerParameter.getEntityId());
        webData.getApplicationDtos().add(applicationDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Désctivation d'une application
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/disable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> desactive(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        Application u = utilsComponent.findApplicationById(headerParameter.getEntityId());
        if (u == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "L'application renseignée n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ApplicationDto applicationDto = applicationService.disable(headerParameter.getEntityId());
        webData.getApplicationDtos().add(applicationDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

}
