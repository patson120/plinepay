package com.plinepay.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDto extends EntityDto{

    private String name;
    private String code;
    private String logo;
    private Float percentFees;

    @Override
    public String toString() {
        return "PaymentMethodDto{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", logo='" + logo + '\'' +
                ", percentFees=" + percentFees +
                '}';
    }
}
