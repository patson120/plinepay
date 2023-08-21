package com.plinepay.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 * Virement vers une banque
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_virement")
public class Virement extends Payment{

    @Size(min = 1, max = 50)
    @Column(name = "bank_name", nullable = false, length = 50)
    private String nameBank;

    @Size(min = 1, max = 50)
    @Column(name = "bank_code", nullable = false, length = 50)
    private String codeBank;

    @Size(min = 1, max = 50)
    @Column(name = "account_number", nullable = false, length = 50)
    private String accountNumber;//Numéro de compte de la personne qui reçoit le virement

    //Element de retour de paiement
    @Column(name = "transaction_id", length = 190, nullable = true)
    private String transactionId;//Transaction généré au niveau de l'opérateur

    @Column(name = "app_transaction_ref", length = 190, nullable = true)
    private String appTransactionRef;//Identifiant de la Transaction généré au niveau du compte marchand (Nécessaire pour le call back)

    @Column(name = "transaction_status", length = 190, nullable = true)
    private String transactionStatus;// Statut du paiement effectué au niveau de la banque

    @Column(name = "message", length = 190, nullable = true)
    private String message;// Message envoyé par la banque

    @Column(name = "name", nullable = true, length = 190)
    private String name; //Customer Name

    @Column(name = "email", nullable = true, length = 190)
    private String email; //Customer Email

    @Override
    public String toString() {
        return "Virement{" +
                "nameBank='" + nameBank + '\'' +
                ", codeBank='" + codeBank + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", appTransactionRef='" + appTransactionRef + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
