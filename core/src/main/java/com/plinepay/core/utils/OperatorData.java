package com.plinepay.core.utils;

import lombok.Data;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
public class OperatorData implements Serializable {

    private String providerCode;
    private UUID transcationId;
    private UUID appTranscationId;
    private String receiverNumber;
    private String senderNumber;
    private String processingNumber;
    private String opcomment;
    private String transactionStatus;
    private String message;
    private Float amount;
    private String signature;

    @Override
    public String toString() {
        return "OperatorData{" +
                "transcationId='" + transcationId + '\'' +
                ", appTranscationId='" + appTranscationId + '\'' +
                ", receiverNumber='" + receiverNumber + '\'' +
                ", senderNumber='" + senderNumber + '\'' +
                ", processingNumber='" + processingNumber + '\'' +
                ", opcomment='" + opcomment + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", message='" + message + '\'' +
                ", amount=" + amount +
                ", signature=" + signature +
                '}';
    }
}
