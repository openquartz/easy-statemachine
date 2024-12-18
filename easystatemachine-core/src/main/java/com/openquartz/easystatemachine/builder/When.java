package com.openquartz.easystatemachine.builder;

import com.openquartz.easystatemachine.Action;

/**
 * When
 */
public interface When<S, E, C>{

    /**
     * Define action to be performed during transition
     *
     * @param action performed action
     */
    void perform(Action<S, E, C> action);
}
