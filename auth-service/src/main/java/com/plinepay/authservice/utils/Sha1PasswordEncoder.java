/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.authservice.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.plinepay.core.utils.UtilsMethods;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Service
public class Sha1PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence cs) {
        return UtilsMethods.buildSha1("PLINE-PAY" + UtilsMethods.buildMd5(String.valueOf(cs)) + "JESUS#2023");
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return encode(cs).equals(string);
    }

}
