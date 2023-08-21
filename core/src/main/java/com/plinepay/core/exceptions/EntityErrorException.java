/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.exceptions;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public class EntityErrorException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5861310537366287163L;

    /**
     * default c
     */
    public EntityErrorException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public EntityErrorException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public EntityErrorException(final String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public EntityErrorException(final Throwable cause) {
        super(cause);
    }
}
