package com.plinepay.core.utils;

import com.plinepay.core.entities.enums.PaymentOperationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderPayment implements Serializable {

    private UUID transactionId;
    private PaymentOperationType operationType;

    @Override
    public String toString() {
        return "HeaderPayment{" +
                "transactionId='" + transactionId + '\'' +
                ", operationType=" + operationType +
                '}';
    }
}
