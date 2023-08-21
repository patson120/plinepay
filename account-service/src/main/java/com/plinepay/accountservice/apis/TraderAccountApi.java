package com.plinepay.accountservice.apis;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.TraderAccountDto;
import com.plinepay.core.entities.TraderAccount;
import com.plinepay.core.services.implementation.TraderAccountService;
import com.plinepay.core.utils.ErrorService;
import com.plinepay.core.utils.HeaderParameter;
import com.plinepay.core.utils.ServiceMessage;
import com.plinepay.core.utils.WebData;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/traders")
public class TraderAccountApi {
    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private TraderAccountService traderAccountService;

    /**
     * Retourner la liste de tous les traderAccounts
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
        webData.getTraderAccountDtos().addAll(traderAccountService.retrieveAll());
        webData.setNumberElement(Long.valueOf(webData.getTraderAccountDtos().size() ));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste des traderAccounts limités par pagination (Limit des
     * résultats)
     *
     * @param headerParameter
     * @return
     */
    @PostMapping(value = "/retrieve/limit")
    public ResponseEntity<WebData> retrieveTraderAccountLimited(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        webData.getTraderAccountDtos().addAll(traderAccountService.retrieveByLimit(headerParameter.getFirstResult(),
                headerParameter.getNumberElement()));
        webData.setNumberElement(Long.valueOf(webData.getTraderAccountDtos().size() > headerParameter.getNumberElement()
                ? headerParameter.getNumberElement()
                : webData.getTraderAccountDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner le nombre de traderAccounts
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
        webData.setNumberElement(traderAccountService.getNumber());

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Ajout de traderAccount
     *
     * @param traderAccountDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> add(@RequestBody TraderAccountDto traderAccountDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setNumberElement(0L);

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, traderAccountDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible d'enregistrer ses données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        traderAccountDto = traderAccountService.create(traderAccountDto);
        webData.getTraderAccountDtos().add(traderAccountDto);
        webData.setNumberElement(Long.valueOf(webData.getTraderAccountDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.CREATED);
    }

    /**
     * Modification d'un compte marchand
     *
     * @param traderAccountDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> update(@RequestBody TraderAccountDto traderAccountDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        TraderAccountDto traderAccountDto1 = traderAccountService.findTraderAccountById(traderAccountDto.getId());
        if (traderAccountDto1 == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cet utilisateur dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, traderAccountDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible d'enregistrer vos données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        traderAccountDto = traderAccountService.update(traderAccountDto);
        webData.getTraderAccountDtos().add(traderAccountDto);
        webData.setNumberElement(Long.valueOf(webData.getTraderAccountDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Suppression d'un compte marchand
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> delete(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        TraderAccount traderAccount = utilsComponent.findTraderAccountById(headerParameter.getEntityId());
        if (traderAccount == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Impossible de trouver cette donnée sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        TraderAccountDto traderAccountDto = traderAccountService.delete(headerParameter.getEntityId());
        webData.getTraderAccountDtos().add(traderAccountDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.NO_CONTENT.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Téléchargement des informations d'un compte marchand
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/retrieve/one", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebData> retrieveOne(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setNumberElement(0L);

        if (headerParameter.getEntityId() != null) {
            TraderAccountDto traderAccount = traderAccountService.findTraderAccountById(headerParameter.getEntityId());
            if (traderAccount != null) {
                webData.getTraderAccountDtos().add(traderAccount);
                ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
                webData.setMessage(message);
                webData.setNumberElement(1L);
                return new ResponseEntity<>(webData, HttpStatus.OK);
            } else {
                ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                        "Impossible de trouver un compte marchand avec cet identifiant");
                webData.setMessage(message);
                return new ResponseEntity<>(webData, HttpStatus.OK);
            }
        } else {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Bien vouloir renseigner l'identifiant de compte");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Activation d'un marchand marchand
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/enable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> enable(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        TraderAccount u = utilsComponent.findTraderAccountById(headerParameter.getEntityId());
        if (u == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Le compte marchand renseigné n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        TraderAccountDto traderAccountDto = traderAccountService.enable(headerParameter.getEntityId());
        webData.getTraderAccountDtos().add(traderAccountDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Désctivation d'un compte marchand
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/disable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> disable(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        TraderAccount u = utilsComponent.findTraderAccountById(headerParameter.getEntityId());
        if (u == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Le compte marchand renseigné n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        TraderAccountDto traderAccountDto = traderAccountService.disable(headerParameter.getEntityId());
        webData.getTraderAccountDtos().add(traderAccountDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

}
