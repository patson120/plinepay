package com.plinepay.core.utils;

import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

@Data
public class TransferData implements Serializable {

    private Float amount;
    private String currency = "XAF";
    private String reason;
    private String customerPhoneNumber;
    private UUID recipient;

    @Override
    public String toString() {
        return "TransferData{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                ", reason='" + reason + '\'' +
                ", customerPhoneNumber='" + customerPhoneNumber + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
