package com.openquartz.easystatemachine;

/**
 * Condition
 */
public interface Guard<C> {

    /**
     * @param context context object
     * @return whether the context satisfied current condition
     */
    boolean isSatisfied(C context);

    default String name(){
        return this.getClass().getSimpleName();
    }
}