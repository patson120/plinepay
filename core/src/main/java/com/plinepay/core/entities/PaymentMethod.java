package com.plinepay.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 09/08/2023 - 14:45
 * Liste des moyen de paiement utilisé dans la plateforme
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_payment_method")
public class PaymentMethod extends AbstractEntity{

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "name", nullable = false, length = 190)
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "code", nullable = false, length = 190, unique = true)
    private String code;

    @Column(name = "logo", nullable = true)
    private String logo;

    @Column(name = "percent_fees", nullable = false,  precision = 12, columnDefinition = "real default '1.80'")
    private Float percentFees; //Pourcentage des frais de l'opérateur

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", logo='" + logo + '\'' +
                ", percentFees=" + percentFees +
                '}';
    }
}
