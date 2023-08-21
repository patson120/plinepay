package com.plinepay.core.utils;

import java.util.UUID;

import com.plinepay.core.dto.MobileDepositDto;
import com.plinepay.core.dto.OnlinePaymentDto;
import com.plinepay.core.dto.PaymentDto;
import com.plinepay.core.dto.TransferDto;
import com.plinepay.core.entities.enums.PaymentOperationType;
import com.plinepay.core.entities.enums.TransferType;

public class DataUtils {

    /**
     *
     * @param requestPaymentData
     * @param onlinePaymentDto
     */
    public static void transferData(RequestPaymentData requestPaymentData, OnlinePaymentDto onlinePaymentDto){
        onlinePaymentDto.setAmount(requestPaymentData.getTransactionAmount());
        onlinePaymentDto.setCurrency(requestPaymentData.getTransactionCurrency());
        onlinePaymentDto.setReason(requestPaymentData.getTransactionReason());
        onlinePaymentDto.setLanguage(requestPaymentData.getCustomerLang());
        onlinePaymentDto.setProcessingNumber(requestPaymentData.getCustomerPhoneNumber());
        onlinePaymentDto.setAppTransactionRef(requestPaymentData.getAppTransactionRef());
        onlinePaymentDto.setName(requestPaymentData.getCustomerName());
        onlinePaymentDto.setEmail(requestPaymentData.getCustomerEmail());
        onlinePaymentDto.setOperationType(PaymentOperationType.ONLINE_PAYMENT);
    }

    public static void transferData(PaymentOperationType paymentOperationType, OperatorData operatorData, PaymentDto paymentDto){

        switch (paymentOperationType){
            case ONLINE_PAYMENT:
                OnlinePaymentDto onlinePaymentDto = (OnlinePaymentDto) paymentDto;
                onlinePaymentDto.setTransactionId(operatorData.getTranscationId());
                onlinePaymentDto.setAppTransactionRef(operatorData.getAppTranscationId());
                onlinePaymentDto.setProcessingNumber(operatorData.getProcessingNumber());
                onlinePaymentDto.setOpcomment(operatorData.getOpcomment());
                onlinePaymentDto.setOperationType(paymentOperationType);
                onlinePaymentDto.setTransactionStatus(operatorData.getTransactionStatus());
                onlinePaymentDto.setMessage(operatorData.getMessage());
                break;

            case WITHDRAWAL:
                MobileDepositDto mobileDepositDto = (MobileDepositDto) paymentDto;
                mobileDepositDto.setTransactionId(operatorData.getTranscationId());
                mobileDepositDto.setAppTransactionRef(operatorData.getAppTranscationId());
                mobileDepositDto.setMobileAccountNumber(operatorData.getProcessingNumber());
                mobileDepositDto.setOpcomment(operatorData.getOpcomment());
                mobileDepositDto.setOperationType(paymentOperationType);
                mobileDepositDto.setTransactionStatus(operatorData.getTransactionStatus());
                mobileDepositDto.setMessage(operatorData.getMessage());
                break;
        }
    }

    /**
     *
     * @param transmitter
     * @param transferData
     * @param paymentDto
     */
    public static void transferData(UUID transmitter, TransferData transferData, PaymentDto paymentDto){

        if ( paymentDto instanceof TransferDto){
            TransferDto transferDto = (TransferDto) paymentDto;
            transferDto.setTransmitterId(transmitter);
            transferDto.setTransferType(TransferType.OUTGOING);
            transferDto.setAmount(transferData.getAmount());
            transferDto.setCurrency(transferData.getCurrency());
            transferDto.setReason(transferData.getReason());
            transferDto.setOperationType(PaymentOperationType.TRANSFER);

        }
    }

}
