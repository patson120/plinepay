/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.utils;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public class PasswordData implements Serializable {

    private UUID id;
    private String actualPassword;
    private String newPassword;

    public PasswordData() {
    }

    public PasswordData(UUID id, String actualPassword, String newPassword) {

        this.id = id;
        this.actualPassword = actualPassword;
        this.newPassword = newPassword;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public void setActualPassword(String actualPassword) {
        this.actualPassword = actualPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "PasswordData{" + "id=" + id + ", actualPassword=" + actualPassword + ", newPassword=" + newPassword + '}';
    }

}
