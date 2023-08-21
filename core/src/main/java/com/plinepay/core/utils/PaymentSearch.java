package com.plinepay.core.utils;

import com.plinepay.core.entities.enums.PaymentOperationType;
import com.plinepay.core.entities.enums.StatusPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSearch implements Serializable {

    private Long startDate;
    private Long endDate;
    private PaymentOperationType operationType;
    private StatusPayment statusPayment;
    private String traderAccountId;
    private String applicationId;
    private String paymentMethodId;
    private Integer firstResult;
    private Integer maxResults;

    @Override
    public String toString() {
        return "PaymentSearch{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", operationType=" + operationType +
                ", statusPayment=" + statusPayment +
                ", traderAccountId='" + traderAccountId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", paymentMethodId='" + paymentMethodId + '\'' +
                ", firstResult=" + firstResult +
                ", maxResults=" + maxResults +
                '}';
    }
}
