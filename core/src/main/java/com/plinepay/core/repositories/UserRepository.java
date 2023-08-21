/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.repositories;

import com.plinepay.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT count(u.id) FROM User u WHERE u.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberUserUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT u FROM User u WHERE u.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<User> getListUserUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    public Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);

    public User findByPhone(String phone);

    @Query(value = "SELECT u FROM User u WHERE u.username = :usernameOrPhoneOrEmail OR u.phone = :usernameOrPhoneOrEmail OR u.email = :usernameOrPhoneOrEmail ", nativeQuery = false)
    public User findByUsernameOrPhoneOrEmail(@Param("usernameOrPhoneOrEmail") String usernameOrPhoneOrEmail);

    @Query(value = "SELECT u FROM User u WHERE ( u.username = :usernameOrPhoneOrEmail OR u.phone = :usernameOrPhoneOrEmail OR u.email = :usernameOrPhoneOrEmail ) AND u.password = :password", nativeQuery = false)
    public User login(@Param("usernameOrPhoneOrEmail") String usernameOrPhoneOrEmail, @Param("password") String password);

}
