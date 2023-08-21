package com.plinepay.core.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ApiParam implements Serializable {


    private UUID transactionId;

    //Api opérateur
    private String phoneNumber;
    private String paymentMethodCode;

    //Carte VISA ou MasterCard
    private String accountNumber;
    private Integer month;
    private Integer year;
    private String fullName;
    private String digitCode;

    @Override
    public String toString() {
        return "ApiParam{" +
                "transactionId='" + transactionId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", paymentMethodCode='" + paymentMethodCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", fullName='" + fullName + '\'' +
                ", digitCode='" + digitCode + '\'' +
                '}';
    }
}
