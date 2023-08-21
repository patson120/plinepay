/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.utils;

import java.io.Serializable;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public class ServiceMessage implements Serializable {

    private int code;
    private String content;

    public ServiceMessage() {
    }

    public ServiceMessage(int code) {
        this.code = code;
    }

    public ServiceMessage(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ServiceMessage{" + "code=" + code + ", content=" + content + '}';
    }

}
