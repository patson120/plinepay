package com.plinepay.core.dto;

import java.util.UUID;

import com.plinepay.core.entities.enums.TransferType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto extends PaymentDto{

    private UUID transmitterId;
    private String transmitterCode;
    private String transmitterName;
    private UUID recipientId;
    private String recipientCode;
    private String recipientName;
    private TransferType transferType;

    @Override
    public String toString() {
        return "TransferDto{" +
                "transmitterId='" + transmitterId + '\'' +
                ", transmitterCode='" + transmitterCode + '\'' +
                ", transmitterName='" + transmitterName + '\'' +
                ", recipientId='" + recipientId + '\'' +
                ", recipientCode='" + recipientCode + '\'' +
                ", recipientName='" + recipientName + '\'' +
                ", transferType=" + transferType +
                '}';
    }
}
