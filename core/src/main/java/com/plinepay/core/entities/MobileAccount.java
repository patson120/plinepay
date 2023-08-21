package com.plinepay.core.entities;

import com.plinepay.core.entities.enums.MobileAccountStatus;
import com.plinepay.core.entities.enums.TradeAccountStatus;

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
 * @Since 09/08/2023 - 14:45
 * Compte Mobile, MOMO, Orange Money...
 * Ses comptes sont générés automatiquement soit l'enregistrement d'un nouveau compte marchand, soit à l'ajout d'un moyen de paiement
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_application_service")
public class MobileAccount extends AbstractEntity{

    @Column(name = "balance", nullable = false, precision = 12, columnDefinition = "real default '0.00'")
    private Float balance;//Solde en cours

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_account_status", nullable = false)
    private MobileAccountStatus mobileAccountStatus;//Détermine l'état actuel du compte

    @JoinColumn(name = "payment_method", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private PaymentMethod paymentMethod;

    @JoinColumn(name = "trader_account", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TraderAccount traderAccount;

    @Override
    public String toString() {
        return "MobileAccount{" +
                "balance=" + balance +
                ", mobileAccountStatus=" + mobileAccountStatus +
                ", paymentMethod=" + paymentMethod +
                ", traderAccount=" + traderAccount +
                '}';
    }
}
