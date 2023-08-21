package com.plinepay.core.services.declaration;

import com.plinepay.core.utils.ResultData;

import java.util.UUID;

import com.plinepay.core.dto.PaymentDto;
import com.plinepay.core.dto.PaymentMethodDto;
import com.plinepay.core.entities.enums.PaymentOperationType;
import com.plinepay.core.utils.OperatorData;

import jakarta.servlet.http.HttpServletRequest;


/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface IPaymentService {

    public String initOnlinePayment(UUID paymentId);

    public void handleFromOperator(PaymentDto paymentDto);

    public void callOperatorAppi(PaymentDto paymentDto, PaymentMethodDto paymentMethodDto, String phoneNumber);

    public PaymentDto retrieveById(UUID paymentId, PaymentOperationType operationType);

    public ResultData checkOperatotData(OperatorData operatorData, PaymentOperationType paymentOperationType, HttpServletRequest request);

}
