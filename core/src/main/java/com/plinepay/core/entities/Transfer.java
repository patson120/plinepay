package com.plinepay.core.entities;

import com.plinepay.core.entities.enums.MobileAccountStatus;
import com.plinepay.core.entities.enums.TransferType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 * Transferts compte à compte
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_transfer_")
public class Transfer extends Payment{

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type", nullable = false)
    private TransferType transferType;//Détermine le type de transfert

    @JoinColumn(name = "transmitter", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TraderAccount transmitter;

    @JoinColumn(name = "recipient", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TraderAccount recipient;

    @Override
    public String toString() {
        return "Transfer{" +
                "transmitter=" + transmitter +
                ", recipient=" + recipient +
                '}';
    }
}
