package com.plinepay.core.entities;

import com.plinepay.core.entities.enums.PaymentOperationType;
import com.plinepay.core.entities.enums.StatusPayment;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 * Payments effectués dans la plateforme
 * L'Id de la transaction est l'Id par défaut générer
 * //Id généré automatiquement est utilisé pour effectuer le paiement.
 * Exemple d'url (https://plinepay.com/api/payments/proceed/eeb0b0b3-25cc-4840-a761-a92354a810d5)
 * Id ici est : eeb0b0b3-25cc-4840-a761-a92354a810d5Setting
 */
@Data
@MappedSuperclass
public class Payment extends AbstractEntity{

    @NotNull
    @Column(name = "pay_date", nullable = true)
    private Long payDate;//Date de paiement

    @Enumerated(EnumType.STRING)
    @Column(name = "status_payment", nullable = false)
    private StatusPayment status;// Statut du paiement

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private PaymentOperationType operationType;// Type de paiement effectué

    @Column(name = "_language", nullable = true, length = 500)
    private String language;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "currency", nullable = false, length = 20)
    private String currency; // Devise 

    @Column(name = "reason", nullable = true, length = 190)
    private String reason; //Raison de la transantion

    @Column(name = "additionnal_information", nullable = true, length = 500)
    private String additionnalInformation; //Informations supplémentaires

    @Column(name = "amount", nullable = false)
    private Float amount;

    @Column(name = "fees", nullable = false)
    private Float fees;

    @JoinColumn(name = "application", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Application application;

    @Override
    public String toString() {
        return "Payment{" +
                "payDate=" + payDate +
                ", status=" + status +
                ", operationType=" + operationType +
                ", language='" + language + '\'' +
                ", currency='" + currency + '\'' +
                ", reason='" + reason + '\'' +
                ", additionnalInformation='" + additionnalInformation + '\'' +
                ", amount=" + amount +
                ", fees=" + fees +
                ", application=" + application +
                '}';
    }
}
