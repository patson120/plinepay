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
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto extends EntityDto {

    private String libelle;
    private String typeRes;
    private String code;

    @Override
    public String toString() {
        return "ResourceDto{" + "libelle=" + libelle + ", typeRes=" + typeRes + ", code=" + code + '}';
    }

}
