package com.plinepay.core.utils;

import java.util.UUID;

import lombok.Data;

@Data
public class PrivilegeParam {

    private UUID resourceId;
    private UUID roleId;

    @Override
    public String toString() {
        return "PrivilegeParam{" +
                "resourceId='" + resourceId + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
