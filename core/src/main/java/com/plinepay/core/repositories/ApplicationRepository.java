package com.plinepay.core.repositories;

import com.plinepay.core.entities.Application;
import com.plinepay.core.entities.TraderAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    @Query(value = "SELECT count(a.id) FROM Application a WHERE a.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberApplicationServiceUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT a FROM Application a WHERE a.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<Application> getListApplicationServiceUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    public List<Application> findByTraderAccount(TraderAccount traderAccount);

    public Application findByPublicKey(String publicKey);
    
}
