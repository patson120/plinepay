package com.plinepay.core.repositories;

import com.plinepay.core.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    @Query(value = "SELECT count(t.id) FROM Transfer t WHERE t.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberTransferUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT t FROM Transfer t WHERE t.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<Transfer> getListTransferUpdated(@Param("lastDateUpdate") long lastDateUpdate);
    
}
