/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.authservice.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.plinepay.authservice.utils.CustomUserDetails;
import com.plinepay.authservice.utils.Sha1PasswordEncoder;
import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.UserDto;
import com.plinepay.core.entities.User;
import com.plinepay.core.exceptions.EntityNoneExistException;
import com.plinepay.core.mappers.UserMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.UserRepository;
import com.plinepay.core.services.declaration.IUserService;
import com.plinepay.core.services.mails.EmailSenderService;
import com.plinepay.core.services.mails.EmailService;
import com.plinepay.core.utils.UtilsMethods;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
	private EmailSenderService emailSenderService;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private Sha1PasswordEncoder sha1PasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }


    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    @Override
    public UserDto findUserById(UUID id) {
        return mapper.modelToDto(userRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities User
     *
     * @return
     */
    @Override
    public Long getNumber() {
        return userRepository.count();
    }

    /**
     * @return
     */
    @Override
    public List<UserDto> retrieveAll() {
        return mapper.modelsToDtos(entityManagerRepository.retrieveUser());
    }

    /**
     * Retourne les éléments en limitant les résultats
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    @Override
    public List<UserDto> retrieveByLimit(int firstResult, int maxResults) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveUserLimited(firstResult, maxResults));
    }

    /**
     * Enregistrement -- User
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto create(UserDto userDto) {

        //On fabrique un utilisateur par défaut avec le rôle (SUPER ADMIN)
        // String password = UtilsMethods.generatePassword();
        //System.out.println("PASSWORD GENERATED : " + password);

        userDto.setPassword(sha1PasswordEncoder.encode(userDto.getPassword()));

        User user = mapper.dtoToModel(userDto);
        UtilsMethods.createID(user);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, user, Boolean.TRUE);

        // user.setRole(utilsComponent.findRoleById(userDto.getRoleId()));
        if (userDto.getTraderAccountId() != null) {
            user.setTraderAccount(utilsComponent.findTraderAccountById(userDto.getTraderAccountId()));
        } else {
            user.setTraderAccount(null);
        }
        
        // A la création d'un user mettre son enable à FALSE et changer plutard
        user.setEnable(Boolean.FALSE);

        user = userRepository.save(user);

        String fullName = user.getName() + (Boolean.FALSE.equals(UtilsMethods.isNullOrEmpty(user.getUsername())) ? user.getUsername() : "");

        //On balance les mail et SMS 
        try {
            //emailService.sendHtml(user.getEmail(), "Trésor public - NOREPLY", "Hello " + user.getName() + ", votre compte a été enregistré avec succès. Vos paramètres de connexion sont : <br/>Identifiant : " + user.getEmail() + "<br/>Mot de passe : " + password);
            UtilsMethods.sendMailThroughCAMOO(user.getName(), user.getUsername(), user.getEmail(), "Trésor public - NOREPLY", "Hello " + fullName + ", votre compte a été enregistré avec succès. Vos paramètres de connexion sont : <br/>Identifiant : " + user.getEmail() + "<br/>Mot de passe : " + userDto.getPassword());
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mapper.modelToDto(user);
    }

    /**
     * Modification -- User
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto update(UserDto userDto) {

        User user = mapper.dtoToModel(userDto);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, user, Boolean.TRUE);

        user.setRole(utilsComponent.findRoleById(userDto.getRoleId()));
        if (userDto.getTraderAccountId() != null) {
            user.setTraderAccount(utilsComponent.findTraderAccountById(userDto.getTraderAccountId()));
        } else {
            user.setTraderAccount(null);
        }

        if (Boolean.FALSE.equals(UtilsMethods.isNullOrEmpty(userDto.getPassword()))){
            user.setPassword(sha1PasswordEncoder.encode(userDto.getPassword()));
        }

        user = userRepository.save(user);
        return mapper.modelToDto(user);
    }

    /**
     * Delete User from database
     *
     * @param userId
     * @return
     */
    @Override
    public UserDto delete(UUID userId) {
        User user = utilsComponent.findUserById(userId);
        // Ne pas supprimer les utilisateur dans le système 
        // Dans le cas d'une suppression comme celle-ci il faut juste le désactiver dans le système en mettant enable = false
        // userRepository.delete(user);

        // Mise à jour du champ enable à false
        // NB: Il faut s'assurer que lors du listing des utilisateurs on ne prend que ceux qui ne sont pas désactiver
        user.setEnable(Boolean.FALSE);
        user = userRepository.save(user);
        return mapper.modelToDto(user);
    }

    /**
     * Retourne les User mis à jour
     *
     * @param lastDateUpdate
     * @return
     */
    @Override
    public List<UserDto> retrieveUserUpdated(long lastDateUpdate) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveUserUpdated(lastDateUpdate));
    }

    /**
     * Désactivation d'un utilisateur
     *
     * @param userId
     * @return
     */
    @Override
    public UserDto disable(UUID userId) {
        User user = utilsComponent.findUserById(userId);
        if (user == null) {
            throw new EntityNoneExistException("L'utilisateur que vous avez renseigné n'existe pas dans le système");
        }
        user.setEnable(Boolean.FALSE);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, user, Boolean.TRUE);
        user = userRepository.save(user);
        return mapper.modelToDto(user);
    }

    /**
     * Activation d'un utilisateur
     *
     * @param userId
     * @return
     */
    @Override
    public UserDto enable(UUID userId) {
        User user = utilsComponent.findUserById(userId);
        if (user == null) {
            throw new EntityNoneExistException("L'utilisateur que vous avez renseigné n'existe pas dans le système");
        }
        user.setEnable(Boolean.TRUE);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, user, Boolean.TRUE);
        user = userRepository.save(user);
        return mapper.modelToDto(user);
    }

    /**
     * Mise-à-jour du mot de passe d'un utilisateur
     *
     * @param userId
     * @param password
     * @return
     */
    @Override
    public UserDto updatePassword(UUID userId, String password) {
        User user = utilsComponent.findUserById(userId);
        //Setting a new password
        user.setPassword(new Sha1PasswordEncoder().encode(password));
        user = userRepository.save(user);

        String fullName = user.getName() + (!UtilsMethods.isNullOrEmpty(user.getUsername()) ? user.getUsername() : "");

        //On balance les mail et SMS 
        try {
            //emailService.sendHtml(user.getEmail(), "Trésor public - NOREPLY", "Hello " + user.getName() + ", votre mot de passe a été modifié avec succès. Vos paramètres de connexion sont : <br/>Identifiant : " + user.getEmail() + "<br/>Mot de passe : " + password);
            UtilsMethods.sendMailThroughCAMOO(user.getName(), user.getUsername(), user.getEmail(), "PlinePay - NOREPLY", "Hello " + fullName + ", votre mot de passe a été modifié avec succès. Vos paramètres de connexion sont : <br/>Identifiant : " + user.getEmail() + "<br/>Mot de passe : " + password);
        } catch (Exception ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mapper.modelToDto(user);
    }
}
