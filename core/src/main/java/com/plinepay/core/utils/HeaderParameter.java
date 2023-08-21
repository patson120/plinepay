/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderParameter implements Serializable {

    private UUID entityId;
    private int firstResult;
    private int numberElement;
    private int otp;

    @Override
    public String toString() {
        return "HeaderParameter{" +
                "entityId='" + entityId + '\'' +
                ", firstResult=" + firstResult + '\'' +
                ", numberElement=" + numberElement + '\'' +
                ", otp=" + otp + '\'' +
                '}';
    }
}
