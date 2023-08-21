package com.plinepay.core.repositories;

import com.plinepay.core.entities.TraderAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface TraderAccountRepository extends JpaRepository<TraderAccount, UUID> {

    @Query(value = "SELECT count(t.id) FROM TraderAccount t WHERE t.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberTraderAccountUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT t FROM TraderAccount t WHERE t.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<TraderAccount> getListTraderAccountUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    public TraderAccount findByPublicKey(String publicKey);
}
