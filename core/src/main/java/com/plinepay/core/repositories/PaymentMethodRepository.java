/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.repositories;

import com.plinepay.core.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID> {

    @Query(value = "SELECT count(p.id) FROM PaymentMethod p WHERE p.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public Long getNumberPaymentMethodUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    @Query(value = "SELECT p FROM PaymentMethod p WHERE p.lastDateUpdate > :lastDateUpdate", nativeQuery = false)
    public List<PaymentMethod> getListPaymentMethodUpdated(@Param("lastDateUpdate") long lastDateUpdate);

    public PaymentMethod findByCode(String code);

}
