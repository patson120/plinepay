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
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 * Administeurs et utilisateurs généraux de la plateforme
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_user")
public class User extends AbstractEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 190)
    @Column(name = "name", nullable = false, length = 190)
    private String name;

    @Size(max = 190)
    @Column(name = "surname", nullable = true, length = 190)
    private String surname;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 190)
    @Column(name = "username", length = 190)
    private String username;

    @Size(max = 190)
    @Column(name = "password", length = 190)
    private String password;

    @Column(name = "profil_image", length = 1000, nullable = true)
    private String profilImage;

    @Column(name = "firebase_token", length = 190, nullable = true)
    private String firebaseToken;

    // @Basic(optional = false)
    @NotNull
    @Column(name = "enable", nullable = true)
    private Boolean enable;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = true)
    private UserType userType;// Type d'utilisateur, Administrateur de la plateforme PlinePay ou utilisateur lié à un compte marchand

    @JoinColumn(name = "user_role", referencedColumnName = "id", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Role role;

    @JoinColumn(name = "trader_account", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private TraderAccount traderAccount;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profilImage='" + profilImage + '\'' +
                ", firebaseToken='" + firebaseToken + '\'' +
                ", enable=" + enable +
                ", userType=" + userType +
                ", role=" + role +
                ", traderAccount=" + traderAccount +
                '}';
    }
}
