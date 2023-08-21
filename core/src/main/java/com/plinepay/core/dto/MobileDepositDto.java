package com.plinepay.core.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class MobileDepositDto extends PaymentDto{

    private UUID transactionId;
    private UUID appTransactionRef;
    private String mobileAccountNumber;
    private String opcomment;
    private String transactionStatus;
    private String message;
    private String name;
    private String email;
    private UUID mobileAccountId;
    private String mobileAccountPaymentMethodCod;
    private UUID paymentMethodId;
    private String paymentMethodCode;
    private String paymentMethodName;

    @Override
    public String toString() {
        return "MobileDepositDto{" +
                "transactionId='" + transactionId + '\'' +
                ", appTransactionRef='" + appTransactionRef + '\'' +
                ", mobileAccountNumber='" + mobileAccountNumber + '\'' +
                ", opcomment='" + opcomment + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileAccountId='" + mobileAccountId + '\'' +
                ", mobileAccountPaymentMethodCod='" + mobileAccountPaymentMethodCod + '\'' +
                ", paymentMethodId='" + paymentMethodId + '\'' +
                ", paymentMethodCode='" + paymentMethodCode + '\'' +
                ", paymentMethodName='" + paymentMethodName + '\'' +
                '}';
    }
}
