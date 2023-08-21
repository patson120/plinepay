package com.plinepay.core.repositories;

import com.plinepay.core.entities.MobileAccount;
import com.plinepay.core.entities.TraderAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface MobileAccountRepository extends JpaRepository<MobileAccount, UUID> {

    @Query(value = "SELECT count(m.id) FROM MobileAccount m WHERE m.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberMobileAccountUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT m FROM MobileAccount m WHERE m.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<MobileAccount> getListMobileAccountUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    public List<MobileAccount> findByTraderAccount(TraderAccount traderAccount);
    
}
