/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.utils;

import java.util.UUID;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public class PwdData {

    private UUID userId;
    private String userPassword;

    public PwdData() {
    }

    public PwdData(UUID userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "PwdData{" + "userId=" + userId + ", userPassword=" + userPassword + '}';
    }

}
