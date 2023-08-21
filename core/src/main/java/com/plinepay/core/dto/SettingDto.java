/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.dto;

import lombok.Data;


/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Data
public class SettingDto extends EntityDto {

    private String facebookPageLink;
    private String twitterPageLink;
    private String googlePageLink;
    private String privacyPolicyLink;
    private String cguLink;
    private String helpLink;
    private String webSiteLink;
    private String appEmail;
    private String whatsApp1;
    private String whatsApp2;

    @Override
    public String toString() {
        return "SettingDto{" +
                "facebookPageLink='" + facebookPageLink + '\'' +
                ", twitterPageLink='" + twitterPageLink + '\'' +
                ", googlePageLink='" + googlePageLink + '\'' +
                ", privacyPolicyLink='" + privacyPolicyLink + '\'' +
                ", cguLink='" + cguLink + '\'' +
                ", helpLink='" + helpLink + '\'' +
                ", webSiteLink='" + webSiteLink + '\'' +
                ", appEmail='" + appEmail + '\'' +
                ", whatsApp1='" + whatsApp1 + '\'' +
                ", whatsApp2='" + whatsApp2 + '\'' +
                '}';
    }
}
