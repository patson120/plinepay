/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.repositories;

import com.plinepay.core.entities.Privilege;
import com.plinepay.core.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, UUID> {

    @Query(value = "SELECT count(p.id) FROM Privilege p WHERE p.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberPrivilegeUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT p FROM Privilege p WHERE p.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<Privilege> getListPrivilegeUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    public List<Privilege> findByRole(Role role);
}
