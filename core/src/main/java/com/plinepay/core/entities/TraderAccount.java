package com.plinepay.core.entities;

import com.plinepay.core.entities.enums.TradeAccountStatus;
import com.plinepay.core.entities.enums.TraderAccountType;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 * Comptes marchand disposant des plateformes utilisant les API pour le paiement
 * Un compte Marchand peut avoir des utilisateurs avec plusieurs profils
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_trader_account")
public class TraderAccount extends AbstractEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "name", nullable = false, length = 190)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private TraderAccountType accountType;// Type de compte

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_account_status", nullable = false)
    private TradeAccountStatus tradeAccountStatus;//Détermine l'état actuel du compte

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "code", nullable = false, length = 190, unique = true)
    private String code; //Code généré automatiquement

    @Column(name = "balance", nullable = false, precision = 12, columnDefinition = "real default '0.00'")
    private Float balance;//Solde en cours

    //Elements à présenter pour valider le compte

    @Column(name = "privacy_policy", nullable = false, columnDefinition = "boolean default false")
    private boolean privacyPolicy;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "public_key", nullable = false, length = 250, unique = true)
    private String publicKey; // Générer après approbation de l'application

    @Column(name = "anti_money_laundering_policy", nullable = false, columnDefinition = "boolean default false")
    private boolean antiMoneyLaunderingPolicy;

    @Column(name = "id_number_card_recto_url", length = 500, nullable = true)
    private String idNumberCardRectoUrl;//URL recto d'une pièce d'identité du manager

    @Column(name = "id_number_card_verso_url", length = 500, nullable = true)
    private String idNumberCardVersoUrl;//URL recto d'une pièce d'identité du manager

    @Size(min = 1, max = 190)
    @Column(name = "commercial_register", nullable = true, length = 190)
    private String commercialRegister;

    @Size(min = 1, max = 190)
    @Column(name = "company_registration_date", nullable = true, length = 190)
    private String companyRegistrationDate;//Format dd/MM/yyyy

    //.... Informations à compléter

     @Size(min = 1, max = 190)
    @Column(name = "currency", nullable = true, length = 30)
    private String currency; // Devise

    @Override
    public String toString() {
        return "TraderAccount{" +
                "name='" + name + '\'' +
                ", accountType=" + accountType +
                ", tradeAccountStatus=" + tradeAccountStatus +
                ", code='" + code + '\'' +
                ", balance=" + balance +
                ", privacyPolicy=" + privacyPolicy +
                ", antiMoneyLaunderingPolicy=" + antiMoneyLaunderingPolicy +
                ", idNumberCardRectoUrl='" + idNumberCardRectoUrl + '\'' +
                ", idNumberCardVersoUrl='" + idNumberCardVersoUrl + '\'' +
                ", commercialRegister='" + commercialRegister + '\'' +
                ", companyRegistrationDate='" + companyRegistrationDate + '\'' + 
                ", currency='" + currency + '\'' +
                '}';
    }
}
