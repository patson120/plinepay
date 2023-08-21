/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_privilege")
public class Privilege extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "status", nullable = false)
    private boolean status;

    @JoinColumn(name = "resource", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Resource resource;

    @JoinColumn(name = "_role", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Role role;

    @Override
    public String toString() {
        return "Privilege{" + "status=" + status + ", resource=" + resource + ", role=" + role + '}';
    }

}
