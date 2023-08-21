/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.utils;

import com.plinepay.core.dto.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebData implements Serializable {

    private Long operationDate;
    private Long numberElement;
    private ServiceMessage message;
    private ErrorService errorService;
    private List<PaymentDto> paymentDtos = new ArrayList<>();
    private List<PrivilegeDto> privilegeDtos = new ArrayList<>();
    private List<ResourceDto> resourceDtos = new ArrayList<>();
    private List<RoleDto> roleDtos = new ArrayList<>();
    private List<TraderAccountDto> traderAccountDtos = new ArrayList<>();
    private List<UserDto> userDtos = new ArrayList<>();
    private List<SettingDto> settingDtos = new ArrayList<>();
    private List<ApplicationDto> applicationDtos = new ArrayList<>();
    private List<MobileAccountDto> mobileAccountDtos = new ArrayList<>();
    private List<PaymentMethodDto> paymentMethodDtos = new ArrayList<>();

    @Override
    public String toString() {
        return "WebData{" +
                "operationDate=" + operationDate +
                ", numberElement=" + numberElement +
                ", message=" + message +
                ", errorService=" + errorService +
                ", paymentDtos=" + paymentDtos +
                ", privilegeDtos=" + privilegeDtos +
                ", resourceDtos=" + resourceDtos +
                ", roleDtos=" + roleDtos +
                ", traderAccountDtos=" + traderAccountDtos +
                ", userDtos=" + userDtos +
                ", settingDtos=" + settingDtos +
                ", applicationDtos=" + applicationDtos +
                ", mobileAccountDtos=" + mobileAccountDtos +
                ", paymentMethodDtos=" + paymentMethodDtos +
                '}';
    }
}
