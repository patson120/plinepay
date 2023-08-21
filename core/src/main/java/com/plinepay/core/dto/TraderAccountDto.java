package com.plinepay.core.dto;

import com.plinepay.core.entities.enums.TradeAccountStatus;
import com.plinepay.core.entities.enums.TraderAccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraderAccountDto extends EntityDto{

    private String name;
    private TraderAccountType accountType;
    private TradeAccountStatus tradeAccountStatus;
    private String code;
    private Float balance;
    private boolean privacyPolicy;
    private boolean antiMoneyLaunderingPolicy;
    private String idNumberCardRectoUrl;
    private String idNumberCardVersoUrl;
    private String commercialRegister;
    private String companyRegistrationDate;

    @Override
    public String toString() {
        return "TraderAccountDto{" +
                "name='" + name + '\'' +
                ", accountType=" + accountType +
                ", tradeAccountStatus=" + tradeAccountStatus +
                ", code='" + code + '\'' +
                ", balance=" + balance +
                ", privacyPolicy=" + privacyPolicy +
                ", antiMoneyLaunderingPolicy=" + antiMoneyLaunderingPolicy +
                ", idNumberCardRectoUrl='" + idNumberCardRectoUrl + '\'' +
                ", idNumberCardVersoUrl='" + idNumberCardVersoUrl + '\'' +
                ", commercialRegister='" + commercialRegister + '\'' +
                ", companyRegistrationDate='" + companyRegistrationDate + '\'' +
                '}';
    }
}
