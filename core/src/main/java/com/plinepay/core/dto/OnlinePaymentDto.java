package com.plinepay.core.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlinePaymentDto extends PaymentDto{

    private UUID transactionId;
    private UUID appTransactionRef;
    private String processingNumber;
    private String opcomment;
    private String transactionStatus;
    private String message;
    private String urlRedirection;
    private String name;
    private String email;

    @Override
    public String toString() {
        return "OnlinePaymentDto{" +
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
