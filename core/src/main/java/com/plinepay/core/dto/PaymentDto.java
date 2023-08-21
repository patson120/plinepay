package com.plinepay.core.dto;

import java.util.UUID;

import com.plinepay.core.entities.enums.PaymentOperationType;
import com.plinepay.core.entities.enums.StatusPayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto extends EntityDto{

    private Long payDate;
    private StatusPayment status;
    private PaymentOperationType operationType;
    private String language;
    private String currency;
    private String reason;
    private String additionnalInformation;
    private Float amount;
    private Float fees;
    private UUID applicationId;
    private String applicationName;

    @Override
    public String toString() {
        return "PaymentDto{" +
                "payDate=" + payDate +
                ", status=" + status +
                ", operationType=" + operationType +
                ", language='" + language + '\'' +
                ", currency='" + currency + '\'' +
                ", reason='" + reason + '\'' +
                ", additionnalInformation='" + additionnalInformation + '\'' +
                ", amount=" + amount +
                ", fees=" + fees +
                ", applicationId='" + applicationId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                '}';
    }
}
