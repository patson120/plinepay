/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public class Encryption implements Serializable {

    private final SecretKey secretKey;
    private final Logger logger;
    private final Base64.Encoder encoder;
    private final Base64.Decoder decoder;

    public Encryption(String myKey) throws UnsupportedEncodingException {
        this.secretKey = new SecretKeySpec(myKey.getBytes("UTF-8"), "AES");
        this.logger = LoggerFactory.getLogger(getClass());
        this.encoder = Base64.getEncoder().withoutPadding();
        this.decoder = Base64.getDecoder();
    }

    public String encryptText(String plainText) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedByte = cipher.doFinal(plainText.getBytes());

            String encodeData = encoder.encodeToString(encryptedByte);
            return encodeData;

        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            java.util.logging.Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String decryptText(String encrypted) {
        try {
            byte[] encryptedByte = decoder.decode(encrypted);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedByte = cipher.doFinal(encryptedByte);
            return new String(decryptedByte);

        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            java.util.logging.Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
