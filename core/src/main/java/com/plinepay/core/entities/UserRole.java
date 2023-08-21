/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.entities;

import com.plinepay.core.entities.enums.StatusApplication;
import com.plinepay.core.entities.enums.UserType;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Patrick Kenne
 * @Project Pline Pay
 * @Since 17/08/2023 - 13:45
 *        T
 */
// @Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
// @Table(name = "pline_pay_table_user_role")
public class UserRole extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = true)
    private UserType userType;// Type d'utilisateur, Administrateur de la plateforme PlinePay ou utilisateur
                              // lié à un compte marchand

    @JoinColumn(name = "user_role", referencedColumnName = "id", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Role role;

    @JoinColumn(name = "trader_account", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private TraderAccount traderAccount;

    @Override
    public String toString() {
        return "UserRole [userType=" + userType + ", role=" + role + ", traderAccount=" + traderAccount + "]";
    }

}
