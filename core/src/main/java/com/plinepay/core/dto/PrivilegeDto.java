/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeDto extends EntityDto {

    private boolean status;
    private UUID resourceId;
    private String resourceCode;
    private String resourceLibelle;
    private UUID roleId;
    private String roleName;

    @Override
    public String toString() {
        return "PrivilegeDto{" + "status=" + status + ", resourceId=" + resourceId + ", resourceCode=" + resourceCode + ", resourceLibelle=" + resourceLibelle + ", roleId=" + roleId + ", roleName=" + roleName + '}';
    }

}
