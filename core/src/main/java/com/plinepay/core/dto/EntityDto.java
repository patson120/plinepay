/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityDto implements Serializable {

    private UUID id;
    private Long dateCreation;
    private Long lastDateUpdate;
    private String userCreation;
    private String lastUserUpdate;
    private Boolean entityState;
    private Boolean flag;

}
