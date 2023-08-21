/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.dto;

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
public class RoleDto extends EntityDto {

    private String code;
    private String name;
    private String description;

    @Override
    public String toString() {
        return "RoleDto{" + "code=" + code + ", name=" + name + ", description=" + description + '}';
    }

}
