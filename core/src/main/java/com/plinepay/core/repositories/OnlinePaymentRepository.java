package com.plinepay.core.repositories;

import com.plinepay.core.entities.OnlinePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface OnlinePaymentRepository extends JpaRepository<OnlinePayment, UUID> {

    @Query(value = "SELECT count(o.id) FROM OnlinePayment o WHERE o.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberOnlinePaymentUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT o FROM OnlinePayment o WHERE o.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<OnlinePayment> getListOnlinePaymentUpdated(@Param("lastDateUpdate") long lastDateUpdate);
    
}
