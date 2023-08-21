/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.authservice.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.plinepay.core.utils.ErrorService;
import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.ResourceDto;
import com.plinepay.core.services.implementation.ResourceService;
import com.plinepay.core.utils.HeaderParameter;
import com.plinepay.core.utils.ServiceMessage;
import com.plinepay.core.utils.UtilsMethods;
import com.plinepay.core.utils.WebData;

/**
 * @author Patrick KENNE
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth/resources/")
public class ResourceApi {

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private ResourceService resourceService;

    /**
     * Retourner la liste de tous les resources
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
        webData.getResourceDtos().addAll(resourceService.retrieveAll());
        webData.setNumberElement(Long.valueOf(webData.getPrivilegeDtos().size()));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste des resources limités par pagination (Limit des
     * résultats)
     *
     * @param headerParameter
     * @return
     */
    @PostMapping(value = "/retrieve/limit")
    public ResponseEntity<WebData> retrieveResourceLimited(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        webData.getResourceDtos().addAll(resourceService.retrieveByLimit(headerParameter.getFirstResult(), headerParameter.getNumberElement()));
        webData.setNumberElement(Long.valueOf(webData.getPrivilegeDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner le nombre de resources
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
        webData.setNumberElement(resourceService.getNumber());

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Ajout de resource
     *
     * @param resourceDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> add(@RequestBody ResourceDto resourceDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, resourceDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible d'enregistrer votre réaction, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ResourceDto resource = resourceService.create(resourceDto);
        webData.getResourceDtos().add(resource);
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.CREATED);
    }

    /**
     * Modification de resource
     *
     * @param resourceDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> update(@RequestBody ResourceDto resourceDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        if (resourceDto.getId() == null) {
             ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible d'enregistrer vos données, informations manquantes");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ResourceDto resource = resourceService.findResourceById(resourceDto.getId());
        if (resource == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cette ressource sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, resourceDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible d'enregistrer vos données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ResourceDto updateResource = resourceService.update(resourceDto);
        webData.getResourceDtos().add(updateResource);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Suppression de resource
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> delete(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        ResourceDto resource = resourceService.findResourceById(headerParameter.getEntityId());
        if (resource == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cette réaction sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ResourceDto resourceDto = resourceService.delete(resource.getId());
        webData.getResourceDtos().add(resourceDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.NO_CONTENT.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }
}
