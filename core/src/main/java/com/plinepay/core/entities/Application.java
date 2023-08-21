package com.plinepay.core.entities;

import com.plinepay.core.entities.enums.StatusApplication;

import jakarta.persistence.Basic;
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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 09/08/2023 - 14:45
 * Un compte marchand doit avoir une application, une application peut correspondre à un site
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_application")
public class Application extends AbstractEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "name", nullable = false, length = 190)
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "private_key", nullable = true, length = 500, unique = true)
    private String privateKey;//Générer après approbation de l'application

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "public_key", nullable = true, length = 250, unique = true)
    private String publicKey;///Générer après approbation de l'application

    @Basic(optional = false)
    @NotNull
    @Column(name = "add_fees")
    private Boolean addFees; // Indique si ce compte supporte les frais de transactions ou non

    @Column(name = "percent", nullable = false,  precision = 12, columnDefinition = "real default '100.00'")
    private Float percent;//Au cas où le client surpporte les frais, indiquer le pourcentage

    @Column(name = "logo_url",nullable = true)
    private String logoURL;//URL du logo à afficher dans la page de paiement

    //Elément liés au paiement en ligne
    @Column(name = "call_back_success_url", length = 500, nullable = true)
    private String callBackSuccessURL;//URL à appeler après validation de l'opération

    @Column(name = "call_back_cancel_url", length = 500, nullable = true)
    private String callBackCancelURL;//URL à appeler annulation de l'opération

    @Column(name = "call_back_error_url", length = 500, nullable = true)
    private String callBackErrorURL;//URL à appeler une erreur qui survient

    @Column(name = "balance", nullable = false,  precision = 12, columnDefinition = "real default '0.00'")
    private Float balance;//Solde en cours

    @Enumerated(EnumType.STRING)
    @Column(name = "status_application", nullable = false)
    private StatusApplication statusApplication;// Statut de l'application

    @JoinColumn(name = "trader_account", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TraderAccount traderAccount;

    @Override
    public String toString() {
        return "ApplicationService{" +
                "name='" + name + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", addFees=" + addFees +
                ", percent=" + percent +
                ", logoURL='" + logoURL + '\'' +
                ", callBackSuccessURL='" + callBackSuccessURL + '\'' +
                ", callBackCancelURL='" + callBackCancelURL + '\'' +
                ", callBackErrorURL='" + callBackErrorURL + '\'' +
                ", balance=" + balance +
                ", statusApplication=" + statusApplication +
                ", traderAccount=" + traderAccount +
                '}';
    }
}
