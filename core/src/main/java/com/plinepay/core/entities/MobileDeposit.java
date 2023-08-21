package com.plinepay.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 * Dépôt mobile sur un compte mobile money ou un portefeuille électronique
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_transfer")
public class MobileDeposit extends Payment{

    @Column(name = "transaction_id", length = 190, nullable = true)
    private String transactionId; // Transaction généré au niveau de l'opérateur

    @Column(name = "app_transaction_ref", length = 190, nullable = true)
    private String appTransactionRef; // Identifiant de la Transaction généré au niveau du compte marchand (Nécessaire pour le call back)

    @Size(min = 1, max = 20)
    @Column(name = "mobile_account_number", nullable = true, length = 20)
    private String mobileAccountNumber; // Numéro de compte de la personne qui reçoit le dépôt

    @Column(name = "opcomment", length = 190, nullable = true)
    private String opcomment; // Commentaire envoyé par l'opérateur

    @Column(name = "transaction_status", length = 190, nullable = true)
    private String transactionStatus; // Statut du paiement effectué au niveau de l'opérateur

    @Column(name = "message", length = 190, nullable = true)
    private String message; // Message envoyé par l'opérateur

    @Column(name = "name", nullable = true, length = 190)
    private String name; // Customer Name

    @Column(name = "email", nullable = true, length = 190)
    private String email; // Customer Email

    @JoinColumn(name = "mobile_account", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private MobileAccount mobileAccount;

    @JoinColumn(name = "payment_method", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private PaymentMethod paymentMethod; // Moyen de paiement de la personne qui doit recevoir le dépôt

    @Override
    public String toString() {
        return "MobileDeposit{" +
                "transactionId='" + transactionId + '\'' +
                ", appTransactionRef='" + appTransactionRef + '\'' +
                ", mobileAccountNumber='" + mobileAccountNumber + '\'' +
                ", opcomment='" + opcomment + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileAccount=" + mobileAccount +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
