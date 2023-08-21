/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.utils;

import com.plinepay.core.dto.PrivilegeDto;
import com.plinepay.core.dto.UserDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private String jwttoken;
    private UserDto userDto;
    private List<PrivilegeDto> privilegeDtos = new ArrayList<>();
    private ServiceMessage message;

    public JwtResponse() {
    }

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public ServiceMessage getMessage() {
        return message;
    }

    public void setMessage(ServiceMessage message) {
        this.message = message;
    }

    public List<PrivilegeDto> getPrivilegeDtos() {
        return privilegeDtos;
    }

    public void setPrivilegeDtos(List<PrivilegeDto> privilegeDtos) {
        this.privilegeDtos = privilegeDtos;
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "jwttoken='" + jwttoken + '\'' +
                ", userDto=" + userDto +
                ", privilegeDtos=" + privilegeDtos +
                ", message=" + message +
                '}';
    }
}
