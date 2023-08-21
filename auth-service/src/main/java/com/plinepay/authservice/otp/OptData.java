package com.plinepay.authservice.otp;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;


@RedisHash("OTP")
public class OptData implements Serializable {
    @Id
    private String userEmail;
    private String code;
    private String name;
    private long createdAt;

    public OptData(){}

    public OptData(String code, String userEmail, String name, long createdAt) {
        this.code = code;
        this.userEmail = userEmail;
        this.name = name;
        this.createdAt = createdAt;
    }


    @Override
    public String toString() {
        return "OptData [code=" + code + ", userEmail=" + userEmail + ", name=" + name + ", createdAt=" + createdAt
                + "]";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    
}
