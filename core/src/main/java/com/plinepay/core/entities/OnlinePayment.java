package com.plinepay.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 * Payments effectués dans la plateforme
 * Paiement en ligne
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_online_payment")
public class OnlinePayment extends Payment{

    @Column(name = "transaction_id", length = 190, nullable = true)
    private String transactionId;//Transaction généré au niveau de l'opérateur

    @Column(name = "app_transaction_ref", length = 190, nullable = true)
    private String appTransactionRef;//Identifiant de la Transaction généré au niveau du compte marchand (Nécessaire pour le call back)

    @Column(name = "processing_number", length = 190, nullable = true)
    private String processingNumber; //Numéro de téléphone utilisé pour effectuer le paiement en ligne

    @Column(name = "opcomment", length = 190, nullable = true)
    private String opcomment;//Commentaire envoyé par l'opérateur

    @Column(name = "transaction_status", length = 190, nullable = true)
    private String transactionStatus;// Statut du paiement effectué au niveau de l'opérateur

    @Column(name = "message", length = 190, nullable = true)
    private String message;// Message envoyé par l'opérateur

    @Column(name = "url_redirection", nullable = true, length = 500)
    private String urlRedirection;

    @Column(name = "name", nullable = true, length = 190)
    private String name; //Customer Name

    @Column(name = "email", nullable = true, length = 190)
    private String email; //Customer Email

    @Override
    public String toString() {
        return "OnlinePayment{" +
                "transactionId='" + transactionId + '\'' +
                ", appTransactionRef='" + appTransactionRef + '\'' +
                ", processingNumber='" + processingNumber + '\'' +
                ", opcomment='" + opcomment + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", message='" + message + '\'' +
                ", urlRedirection='" + urlRedirection + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
