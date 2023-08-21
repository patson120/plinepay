package com.plinepay.core.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PaymentUrlData implements Serializable {

    private Long operationDate;
    private UUID transactionId;
    private String targetPayment;
    private String status;
    private String paymentUrl;
    private ServiceMessage message;

    @Override
    public String toString() {
        return "PaymentUrlData{" +
                "operationDate=" + operationDate +
                ", transactionId='" + transactionId + '\'' +
                ", targetPayment='" + targetPayment + '\'' +
                ", status='" + status + '\'' +
                ", paymentUrl='" + paymentUrl + '\'' +
                ", message=" + message +
                '}';
    }
}
