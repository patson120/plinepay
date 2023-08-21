package com.plinepay.core.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
public class RequestPaymentData implements Serializable {

    private String transactionLabel;
    private Float transactionAmount;
    private String transactionCurrency = "XAF";
    private String transactionReason;
    private String customerLang = "fr";
    private String customerPhoneNumber;
    private UUID appTransactionRef;
    private String customerName;
    private String customerEmail;

    @Override
    public String toString() {
        return "RequestPaymentData{" +
                "transactionLabel=" + transactionLabel +
                ", transactionAmount=" + transactionAmount +
                ", transactionCurrency='" + transactionCurrency + '\'' +
                ", transactionReason='" + transactionReason + '\'' +
                ", customerLang='" + customerLang + '\'' +
                ", customerPhoneNumber='" + customerPhoneNumber + '\'' +
                ", appTransactionRef='" + appTransactionRef + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}
