/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 31/07/2023 - 15:45
 * Configuration
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pline_pay_table_setting")
public class Setting extends AbstractEntity {

    @Column(name = "facebook_page_link", nullable = true, length = 190)
    private String facebookPageLink;

    @Column(name = "twitter_page_link", nullable = true, length = 190)
    private String twitterPageLink;

    @Column(name = "google_page_link", nullable = true, length = 190)
    private String googlePageLink;

    @Column(name = "privacy_policy_link", nullable = true, length = 190)
    private String privacyPolicyLink;

    @Column(name = "cgu_link", nullable = true, length = 190)
    private String cguLink;

    @Column(name = "help_link", nullable = true, length = 190)
    private String helpLink;

    @Column(name = "web_site_link", nullable = true, length = 190)
    private String webSiteLink;

    @Column(name = "app_email", nullable = true, length = 190)
    private String appEmail;

    @Column(name = "whats_app1", nullable = true, length = 30)
    private String whatsApp1;

    @Column(name = "whats_app2", nullable = true, length = 30)
    private String whatsApp2;

    @Override
    public String toString() {
        return "Setting{" +
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
