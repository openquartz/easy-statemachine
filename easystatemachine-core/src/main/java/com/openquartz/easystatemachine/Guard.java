package com.openquartz.easystatemachine;

/**
 * Guard
 */
public interface Guard<C> {

    /**
     * Check whether the context satisfied current condition
     * satisfied condition will transit to next state.
     * @param context context object
     * @return whether the context satisfied current condition
     */
    boolean isSatisfied(C context);

    default String name(){
        return this.getClass().getSimpleName();
    }
}