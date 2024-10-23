package com.openquartz.easystatemachine.exception;

/**
 * TransitionFailException
 */
public class TransitionFailException extends RuntimeException {

    public TransitionFailException(String errMsg) {
        super(errMsg);
    }
}
