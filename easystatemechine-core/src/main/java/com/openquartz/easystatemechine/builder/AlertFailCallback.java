package com.openquartz.easystatemechine.builder;

import com.openquartz.easystatemechine.exception.TransitionFailException;

/**
 * Alert fail callback, throw an {@code TransitionFailException}
 */
public class AlertFailCallback<S, E, C> implements FailCallback<S, E, C> {

    @Override
    public void onFail(S sourceState, E event, C context) {
        throw new TransitionFailException(
            "Cannot fire event [" + event + "] on current state [" + sourceState + "] with context [" + context + "]"
        );
    }
}
