/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_role")
public class Role extends AbstractEntity {

    @Size(min = 1, max = 190)
    @Column(name = "code", nullable = false, length = 190)
    private String code;

    @Size(min = 1, max = 190)
    @Column(name = "name", nullable = false, length = 190)
    private String name;

    @Size(min = 1, max = 190)
    @Column(name = "description", nullable = false, length = 190)
    private String description;

    @Override
    public String toString() {
        return "Role{" + "id=" + this.getId() + ", dateCreation=" + this.getDateCreation() + ", lastDateUpdate=" + this.getLastDateUpdate() + ", userCreation=" + this.getUserCreation() + ", lastUserUpdate=" + this.getLastUserUpdate() + ", entityState=" + this.getEntityState() + ", flag=" + this.getFlag() + ", code=" + code + ", name=" + name + ", description=" + description + '}';
    }

}
