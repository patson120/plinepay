/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.repositories;

import com.plinepay.core.entities.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project PlinePay
 */
public interface SettingRepository extends JpaRepository<Setting, UUID> {

    @Query(value = "SELECT count(s.id) FROM Setting s WHERE s.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberSettingUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT s FROM Setting s WHERE s.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<Setting> getListSettingUpdated(@Param("lastDateUpdate") long lastDateUpdate);

}
