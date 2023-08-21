package com.plinepay.core.dto;

import java.util.UUID;

import com.plinepay.core.entities.enums.MobileAccountStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileAccountDto extends EntityDto{

    private Float balance;
    private MobileAccountStatus mobileAccountStatus;
    private UUID paymentMethodId;
    private String paymentMethodCode;
    private String paymentMethodName;
    private String paymentMethodLogo;
    private UUID traderAccountId;
    private String traderAccountCode;
    private String traderAccountName;

    @Override
    public String toString() {
        return "MobileAccountDto{" +
                "balance=" + balance +
                ", mobileAccountStatus=" + mobileAccountStatus +
                ", paymentMethodId='" + paymentMethodId + '\'' +
                ", paymentMethodCode='" + paymentMethodCode + '\'' +
                ", paymentMethodName='" + paymentMethodName + '\'' +
                ", paymentMethodLogo='" + paymentMethodLogo + '\'' +
                ", traderAccountId='" + traderAccountId + '\'' +
                ", traderAccountCode='" + traderAccountCode + '\'' +
                ", traderAccountName='" + traderAccountName + '\'' +
                '}';
    }
}
