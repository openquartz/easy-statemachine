package com.openquartz.easystatemechine.exception;

/**
 * TransitionFailException
 */
public class TransitionFailException extends RuntimeException {

    public TransitionFailException(String errMsg) {
        super(errMsg);
    }
}
