/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.repositories;

import com.plinepay.core.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface ResourceRepository extends JpaRepository<Resource, UUID> {

    @Query(value = "SELECT count(r.id) FROM Resource r WHERE r.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberResourceUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT r FROM Resource r WHERE r.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<Resource> getListResourceUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    public Resource findByCode(String code);

}
