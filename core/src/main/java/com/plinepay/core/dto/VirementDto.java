package com.plinepay.core.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VirementDto extends PaymentDto{

    private String nameBank;
    private String codeBank;
    private String accountNumber;
    private UUID transactionId;
    private String appTransactionRef;
    private String transactionStatus;
    private String message;
    private String name;
    private String email;

    @Override
    public String toString() {
        return "VirementDto{" +
                "nameBank='" + nameBank + '\'' +
                ", codeBank='" + codeBank + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", appTransactionRef='" + appTransactionRef + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
