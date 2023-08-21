package com.plinepay.core.repositories;

import com.plinepay.core.entities.Virement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface VirementRepository extends JpaRepository<Virement, UUID> {

    @Query(value = "SELECT count(v.id) FROM Virement v WHERE v.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberVirementUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT v FROM Virement v WHERE v.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<Virement> getListVirementUpdated(@Param("lastDateUpdate") long lastDateUpdate);
    
}
