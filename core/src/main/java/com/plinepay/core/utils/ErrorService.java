/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public class ErrorService implements Serializable {

    protected HashMap<String, String> errors = new HashMap<>();

    public void reject(String attribute, String message) {
        errors.put(attribute, message);
    }

    public Boolean hasError() {
        return !errors.keySet().isEmpty();
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        
        String value = "";
        Set<String> keys = errors.keySet();
        for (String key : keys ){
            value += "KEY : " + key + ", VALUE : " + errors.get(key) + "\n";
        }
        return value;
    }
    
    

}
