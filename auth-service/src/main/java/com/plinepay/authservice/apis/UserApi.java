package com.plinepay.authservice.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.plinepay.authservice.otp.OptData;
import com.plinepay.authservice.services.OptDataService;
import com.plinepay.authservice.services.UserService;
import com.plinepay.authservice.utils.JWTUtility;
import com.plinepay.authservice.utils.Sha1PasswordEncoder;
import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.UserDto;
import com.plinepay.core.entities.User;
import com.plinepay.core.utils.ErrorService;
import com.plinepay.core.utils.HeaderParameter;
import com.plinepay.core.utils.JwtRequest;
import com.plinepay.core.utils.JwtResponse;
import com.plinepay.core.utils.PasswordData;
import com.plinepay.core.utils.PwdData;
import com.plinepay.core.utils.ServiceMessage;
import com.plinepay.core.utils.UtilsMethods;
import com.plinepay.core.utils.WebData;

/**
 * @author Patrick KENNE
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth/users")
@EnableCaching
public class UserApi {

    @Autowired
    private OptDataService optDataService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private UserService userService;

    // Authentification d'un nouvel utilisateur
    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        JwtResponse jwtResponse = new JwtResponse();
        ServiceMessage message = new ServiceMessage();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtRequest.getEmail(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            message.setCode(HttpStatus.BAD_REQUEST.value());
            message.setContent("INVALID_CREDENTIALS: Username or password is incorrect");
            jwtResponse.setMessage(message);
            return new ResponseEntity<>(jwtResponse, HttpStatus.BAD_REQUEST);
        }
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());
        final String token = jwtUtility.generateToken(userDetails);

        jwtResponse.setJwttoken(token);
        message = new ServiceMessage(HttpStatus.OK.value(), "Token généré avec succès");
        jwtResponse.setMessage(message);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    /**
     * Retourner la liste de tous les users
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
        webData.getUserDtos().addAll(userService.retrieveAll());

        webData.setNumberElement(Long.valueOf(webData.getUserDtos().size()));

        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner la liste des users limités par pagination (Limit des résultats)
     *
     * @param headerParameter
     * @return
     */
    @PostMapping(value = "/retrieve/limit")
    public ResponseEntity<WebData> retrieveUserLimited(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        webData.getUserDtos().addAll(
                userService.retrieveByLimit(headerParameter.getFirstResult(), headerParameter.getNumberElement()));
        webData.setNumberElement(Long.valueOf(webData.getUserDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Retourner le nombre de users
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
        webData.setNumberElement(userService.getNumber());
        return new ResponseEntity<>(webData, HttpStatus.OK);

    }

    /**
     * Ajout de user
     *
     * @param userDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> add(@RequestBody UserDto userDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, userDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible d'enregistrer ses données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }
        OptData otp = new OptData();
        otp.setCode(UtilsMethods.generateOtpCode());
        otp.setName(userDto.getName());
        otp.setUserEmail(userDto.getEmail());
        otp.setCreatedAt(System.currentTimeMillis());
        otp = optDataService.saveOptData(otp);

        // userDto = userService.create(userDto);
        webData.getUserDtos().add(userDto);
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(optDataService.findAllOptData(), HttpStatus.CREATED);
    }

    /**
     * Modification de user
     *
     * @param userDto
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> update(@RequestBody UserDto userDto) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        UserDto userDto1 = userService.findUserById(userDto.getId());
        if (userDto1 == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible de trouver cet utilisateur dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        ErrorService errorService = new ErrorService();
        utilsComponent.validate(errorService, userDto);
        if (Boolean.TRUE.equals(errorService.hasError())) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible d'enregistrer vos données, informations manquantes");
            webData.setMessage(message);
            webData.setErrorService(errorService);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        userDto = userService.update(userDto);
        webData.getUserDtos().add(userDto);
        webData.setNumberElement(Long.valueOf(webData.getUserDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.CREATED);
    }

    /**
     * Suppression de user
     * NB: Ce que nous faisont n'est pas le suppression de l'utilisateur proprement
     * dit, on le désactive juste
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> delete(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        User user = utilsComponent.findUserById(headerParameter.getEntityId());
        if (user == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Impossible de trouver cette donnée sur le serveur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = userService.delete(headerParameter.getEntityId());
        webData.getUserDtos().add(userDto);
        webData.setNumberElement(Long.valueOf(webData.getUserDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.NO_CONTENT.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Téléchargement des informations d'un utilisateur
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
            UserDto user = userService.findUserById(headerParameter.getEntityId());
            if (user != null) {
                webData.getUserDtos().add(user);
                webData.setNumberElement(Long.valueOf(webData.getUserDtos().size()));
                ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                        "Opération effectuée avec succès");
                webData.setMessage(message);
                return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
            } else {
                ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                        "Impossible de trouver un utilisateur avec cet identifiant");
                webData.setMessage(message);
                return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
            }
        } else {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Bien vouloir renseigner l'identifiant de l'utilisateur");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Activation d'un utilisateur
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/enable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> active(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setNumberElement(0L);

        User u = utilsComponent.findUserById(headerParameter.getEntityId());
        if (u == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "L'utilisateur renseigné n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }
        // Cas où l'OPT n'est pas renseigné
        if (Boolean.TRUE.equals(UtilsMethods.isNullOrEmpty(String.valueOf(headerParameter.getOtp())))) {

            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Le code OTP doit être renseigné");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        } else {
            OptData otp = optDataService.findOptDataByEmail(u.getEmail());

            // 5 minutes = 60 * 5 qui est notre durée maximale d'un code OTP
            if (otp == null) {
                ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Code OTP invalide");
                webData.setMessage(message);
                return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
            } else if ((otp.getCreatedAt() + 60 * 5) < System.currentTimeMillis()) {
                // Suppression de l'OTP expiré
                optDataService.deleteOptData(u.getEmail());
                System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.err.println("Je suis ici 1");
                System.err.println(otp.toString());
                System.err.println((otp.getCreatedAt() + 60 * 5) < System.currentTimeMillis());
                System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Code OTP invalide");
                webData.setMessage(message);
                return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
            }
            else {
                // Suppression de l'OTP
                System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.err.println("Je suis ici 2");
                System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                optDataService.deleteOptData(u.getEmail());
            }
        }

        UserDto userDto = userService.enable(headerParameter.getEntityId());
        webData.getUserDtos().add(userDto);
        webData.setNumberElement(Long.valueOf(webData.getUserDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.CREATED);
    }

    /**
     * Ré-envoyer le code OTP
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/sendOtp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> sendOtp(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setNumberElement(0L);

        User u = utilsComponent.findUserById(headerParameter.getEntityId());
        if (u == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "L'utilisateur renseigné n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }
        OptData otp = new OptData();
        otp.setCode(UtilsMethods.generateOtpCode());
        otp.setName(u.getName());
        otp.setUserEmail(u.getEmail());
        otp.setCreatedAt(System.currentTimeMillis());
        otp = optDataService.saveOptData(otp);

        UserDto userDto = userService.enable(headerParameter.getEntityId());
        webData.getUserDtos().add(userDto);
        webData.setNumberElement(Long.valueOf(webData.getUserDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(otp, HttpStatus.CREATED);
    }

    /**
     * Désactivation d'un utilisateur
     *
     * @param headerParameter
     * @return (WebData)
     */
    @RequestMapping(value = "/disable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> desactive(@RequestBody HeaderParameter headerParameter) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());
        webData.setNumberElement(0L);
        User u = utilsComponent.findUserById(headerParameter.getEntityId());
        if (u == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "L'utilisateur renseigné n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = userService.disable(headerParameter.getEntityId());
        webData.getUserDtos().add(userDto);
        webData.setNumberElement(Long.valueOf(webData.getUserDtos().size()));
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.CREATED);
    }

    /**
     * Modification du mot de passe d'un utilisateur
     *
     * @param pwdData
     * @return (WebData)
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> updateUserPassword(@RequestBody PwdData pwdData) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        UserDto u = userService.findUserById(pwdData.getUserId());
        if (u == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "L'utilisateur renseigné n'existe pas dans le système");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        UserDto user = userService.updatePassword(pwdData.getUserId(), pwdData.getUserPassword());
        webData.getUserDtos().add(user);
        ServiceMessage message = new ServiceMessage(HttpStatus.OK.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.OK);
    }

    /**
     * Modification du mot de passe
     *
     * @param passwordData
     * @return (WebData)
     */
    @RequestMapping(value = "/update/password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<WebData> updatePassword(@RequestBody PasswordData passwordData) {

        WebData webData = new WebData();
        webData.setOperationDate(System.currentTimeMillis());

        User userDb = utilsComponent.findUserById(passwordData.getId());

        if (userDb == null) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(), "Erreur : utilisateur inconnu");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        if (passwordData.getActualPassword() == null || passwordData.getActualPassword().isEmpty()) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Veuillez-renseigner le mot de passe actuel");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        String encryptdPassword = new Sha1PasswordEncoder().encode(passwordData.getActualPassword());

        if (!userDb.getPassword().equals(encryptdPassword)) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Le mot de passe actuel ne correspond pas");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        if (passwordData.getNewPassword() == null || passwordData.getNewPassword().isEmpty()) {
            ServiceMessage message = new ServiceMessage(HttpStatus.BAD_REQUEST.value(),
                    "Veuillez-renseigner le nouveau mot de passe");
            webData.setMessage(message);
            return new ResponseEntity<>(webData, HttpStatus.BAD_REQUEST);
        }

        UserDto user = userService.updatePassword(userDb.getId(), passwordData.getNewPassword());
        webData.getUserDtos().add(user);
        ServiceMessage message = new ServiceMessage(HttpStatus.CREATED.value(), "Opération effectuée avec succès");
        webData.setMessage(message);
        return new ResponseEntity<>(webData, HttpStatus.CREATED);
    }

}
