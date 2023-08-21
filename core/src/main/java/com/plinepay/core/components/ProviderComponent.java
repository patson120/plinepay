package com.plinepay.core.components;

import com.plinepay.core.dto.PaymentDto;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class ProviderComponent {

    public void handleOnlinePayment(PaymentDto paymentDto){

    }

    public void handleMobileDepositPayment(PaymentDto paymentDto){

    }

    public void handleVirementPayment(PaymentDto paymentDto){

    }

    public void callMTNOnlinePayment(PaymentDto paymentDto, String phoneNumber, String apiKey){//A compléter

    }

    public void callMTNMobileDeposit(PaymentDto paymentDto, String phoneNumber, String apiKey){//A compléter

    }

}
