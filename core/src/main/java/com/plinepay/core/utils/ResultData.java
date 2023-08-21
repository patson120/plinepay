package com.plinepay.core.utils;

import java.io.Serializable;

import com.plinepay.core.dto.PaymentDto;

import lombok.Data;

@Data
public class ResultData implements Serializable {

    private PaymentDto paymentDto;
    private ServiceMessage message;

    public ResultData() {
    }

    public ResultData(ServiceMessage message) {
        this.message = message;
    }

    public ResultData(PaymentDto paymentDto, ServiceMessage message) {
        this.paymentDto = paymentDto;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "paymentDto=" + paymentDto +
                ", message=" + message +
                '}';
    }
}
