package com.openquartz.easystatemachine.builder;

/**
 * FailCallback
 */
@FunctionalInterface
public interface FailCallback<S, E, C> {

    /**
     * Callback function to execute if failed to trigger an Event
     *
     * @param sourceState sourceState
     * @param event event
     * @param context context
     */
    void onFail(S sourceState, E event, C context);
}
