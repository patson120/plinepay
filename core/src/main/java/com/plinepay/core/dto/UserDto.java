/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.dto;

import java.util.UUID;

import com.plinepay.core.entities.enums.UserType;
import lombok.Data;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
public class UserDto extends EntityDto {

    private String name;
    private String surname;
    private String phone;
    private String email;
    private String username;
    private String password;
    private Boolean enable;
    private String profilImage;
    private String firebaseToken;
    private UserType userType;
    private UUID roleId;
    private String roleName;
    private UUID traderAccountId;
    private String traderAccountCode;
    private String traderAccountName;

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                ", profilImage='" + profilImage + '\'' +
                ", firebaseToken='" + firebaseToken + '\'' +
                ", userType=" + userType +
                ", roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", traderAccountId='" + traderAccountId + '\'' +
                ", traderAccountCode='" + traderAccountCode + '\'' +
                ", traderAccountName='" + traderAccountName + '\'' +
                '}';
    }
}
