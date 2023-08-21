/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.authservice.apis;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.RoleDto;
import com.plinepay.core.entities.Role;
import com.plinepay.core.services.implementation.RoleService;
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
@RequestMapping("/api/auth/roles/")
public class RoleApi {

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private RoleService roleService;

    /**
     * Retourner la liste de tous les roles
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
        webData.getRoleDtos().addAll(roleService.retrieveAll());
        webData.setNumberElement(Long.valueOf(webData.getRoleDtos().size()));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste des roles limités par pagination (Limit des résultats)
     *
     * @param headerParameter
     * @return
     */
    @PostMapping(value = "/retrieve/limit")
    public ResponseEntity<WebData> retrieveRoleLimited(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        webData.getRoleDtos().addAll(roleService.retrieveByLimit(headerParameter.getFirstResult(), headerParameter.getNumberElement()));
        webData.setNumberElement(Long.valueOf(webData.getRoleDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner le nombre de roles
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
        webData.setNumberElement(roleService.getNumber());

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Ajout de role
     *
     * @param roleDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> add(@RequestBody RoleDto roleDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, roleDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible d'enregistrer ses données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        roleDto = roleService.create(roleDto);
        webData.getRoleDtos().add(roleDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.CREATED);
    }

    /**
     * Modification de role
     *
     * @param roleDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> update(@RequestBody RoleDto roleDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        Role role = utilsComponent.findRoleById(roleDto.getId());
        if (role == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cette donnée sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, roleDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible d'enregistrer vos données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        roleDto = roleService.update(roleDto);
        webData.getRoleDtos().add(roleDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Suppression de role
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> delete(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        Role role = utilsComponent.findRoleById(headerParameter.getEntityId());
        if (role == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cette donnée sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        RoleDto roleDto = roleService.delete(headerParameter.getEntityId());
        webData.getRoleDtos().add(roleDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.NO_CONTENT.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Téléchargement des informations d'un role
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/retrieve/one", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveOne(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        if (headerParameter.getEntityId() != null) {
            RoleDto role = roleService.findRoleById(headerParameter.getEntityId());
            if (role != null) {
                webData.getRoleDtos().add(role);
                ServiceMessage message = new ServiceMessage(0, "Opération effectuée avec succès");
                webData.setMessage(message);
                return new ResponseEntity(webData, HttpStatus.OK);
            } else {
                ServiceMessage message = new ServiceMessage(2, "Impossible de trouver un rôle avec cet identifiant");
                webData.setMessage(message);
                return new ResponseEntity(webData, HttpStatus.OK);
            }
        } else {
            ServiceMessage message = new ServiceMessage(2, "Bien vouloir renseigner l'identifiant du rôle");
            webData.setMessage(message);
            return new ResponseEntity(webData, HttpStatus.OK);
        }
    }

}
