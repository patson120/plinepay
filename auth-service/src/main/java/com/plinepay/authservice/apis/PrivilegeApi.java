/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.authservice.apis;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.PrivilegeDto;
import com.plinepay.core.services.implementation.PrivilegeService;
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
@RequestMapping("/api/auth/privileges/")
public class PrivilegeApi {

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private PrivilegeService privilegeService;

    /**
     * Retourner la liste de tous les privileges
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
        webData.getPrivilegeDtos().addAll(privilegeService.retrieveAll());
        webData.setNumberElement(Long.valueOf(webData.getPrivilegeDtos().size()));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste des privileges limités par pagination (Limit des
     * résultats)
     *
     * @param headerParameter
     * @return
     */
    @PostMapping(value = "/retrieve/limit")
    public ResponseEntity<WebData> retrievePrivilegeLimited(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        webData.getPrivilegeDtos().addAll(
                privilegeService.retrieveByLimit(headerParameter.getFirstResult(), headerParameter.getNumberElement()));
        webData.setNumberElement(Long.valueOf(webData.getPrivilegeDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner le nombre de privileges
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
        webData.setNumberElement(privilegeService.getNumber());

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Ajout de privilege
     *
     * @param privilegeDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> add(@RequestBody PrivilegeDto privilegeDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, privilegeDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible d'enregistrer ce privilège, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        PrivilegeDto privilege = privilegeService.create(privilegeDto);
        webData.getPrivilegeDtos().add(privilege);
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.CREATED);
    }

    /**
     * Modification de privilege
     *
     * @param privilegeDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> update(@RequestBody PrivilegeDto privilegeDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        PrivilegeDto privilege = privilegeService.findPrivilegeById(privilegeDto.getId());
        if (privilege == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible de trouver ce privilège sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, privilegeDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible d'enregistrer vos données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        PrivilegeDto updatePrivilege = privilegeService.update(privilegeDto);
        webData.getPrivilegeDtos().add(updatePrivilege);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Suppression de privilege
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> delete(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        PrivilegeDto privilege = privilegeService.findPrivilegeById(headerParameter.getEntityId());
        if (privilege == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible de trouver ce privilège sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        PrivilegeDto privilegeDto = privilegeService.delete(privilege.getId());
        webData.getPrivilegeDtos().add(privilegeDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.NO_CONTENT.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Téléchargement des informations
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/retrieve/one", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveOne(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        PrivilegeDto privilegeDto = privilegeService.findPrivilegeById(headerParameter.getEntityId());
        if (privilegeDto != null) {
            webData.getPrivilegeDtos().add(privilegeDto);
            ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.OK);
        }
        ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                "Impossible de trouver ce privilège sur le serveur");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);

    }

    /**
     * Retourner la liste de tous les privileges d'un role
     *
     * @param headerParameter
     * @return
     */
    @RequestMapping(value = "/retrieve/role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveByRole(@RequestBody HeaderParameter headerParameter) {

        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setMessage(message);
        webData.getPrivilegeDtos().addAll(privilegeService.retrieveByRole(headerParameter.getEntityId()));
        webData.setNumberElement(Long.valueOf(webData.getPrivilegeDtos().size()));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste de tous les privileges d'une ressource
     *
     * @param headerParameter
     * @return
     */
    @RequestMapping(value = "/retrieve/resource", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveByResource(@RequestBody HeaderParameter headerParameter) {

        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setMessage(message);
        webData.getPrivilegeDtos().addAll(privilegeService.retrieveByResource(headerParameter.getEntityId()));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste de tous les privileges d'un role et d'une ressource
     *
     * @param privilegeParam
     * @return
     */
    @RequestMapping(value = "/retrieve/resource/role", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveByResourceAndRole(@RequestBody PrivilegeParam privilegeParam) {

        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setMessage(message);
        PrivilegeDto privilegeDto = privilegeService.retrieveByResourceAndRole(privilegeParam.getResourceId(),
                privilegeParam.getRoleId());
        if (privilegeDto != null) {
            webData.getPrivilegeDtos().add(privilegeDto);
        }
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Activation d'un privilège
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> check(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        PrivilegeDto p = privilegeService.findPrivilegeById(headerParameter.getEntityId());
        if (p == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Le privilège renseigné n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        PrivilegeDto privilegeDto = privilegeService.check(headerParameter.getEntityId());
        webData.getPrivilegeDtos().add(privilegeDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Désactivation d'un privilège
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/uncheck", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> uncheck(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        PrivilegeDto p = privilegeService.findPrivilegeById(headerParameter.getEntityId());
        if (p == null) {
            ServiceMessage message = new ServiceMessage(2, "Le privilège renseigné n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity(webData, HttpStatus.OK);
        }

        PrivilegeDto privilegeDto = privilegeService.uncheck(headerParameter.getEntityId());
        webData.getPrivilegeDtos().add(privilegeDto);
        ServiceMessage message = new ServiceMessage(0, "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity(webData, HttpStatus.CREATED);
    }

}
