package com.plinepay.core.dto;

import java.util.UUID;

import com.plinepay.core.entities.enums.StatusApplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto extends EntityDto{

    private String name;
    private String privateKey;
    private String publicKey;
    private Boolean addFees;
    private Float percent;
    private String logoURL;
    private String callBackSuccessURL;
    private String callBackCancelURL;
    private String callBackErrorURL;
    private Float balance;
    private StatusApplication statusApplication;
    private UUID traderAccountId;
    private String traderAccountCode;
    private String traderAccountName;

    @Override
    public String toString() {
        return "ApplicationServiceDto{" +
                "name='" + name + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", addFees=" + addFees +
                ", percent=" + percent +
                ", logoURL='" + logoURL + '\'' +
                ", callBackSuccessURL='" + callBackSuccessURL + '\'' +
                ", callBackCancelURL='" + callBackCancelURL + '\'' +
                ", callBackErrorURL='" + callBackErrorURL + '\'' +
                ", balance=" + balance +
                ", statusApplication=" + statusApplication +
                ", traderAccountId='" + traderAccountId + '\'' +
                ", traderAccountCode='" + traderAccountCode + '\'' +
                ", traderAccountName='" + traderAccountName + '\'' +
                '}';
    }
}
