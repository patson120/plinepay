package com.plinepay.core.repositories;

import com.plinepay.core.entities.MobileAccount;
import com.plinepay.core.entities.MobileDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface MobileDepositRepository extends JpaRepository<MobileDeposit, UUID> {

    @Query(value = "SELECT count(m.id) FROM MobileDeposit m WHERE m.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberMobileDepositServiceUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT m FROM MobileDeposit m WHERE m.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<MobileDeposit> getListMobileDepositServiceUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    public List<MobileDeposit> findByMobileAccount(MobileAccount mobileAccount);
    
}
