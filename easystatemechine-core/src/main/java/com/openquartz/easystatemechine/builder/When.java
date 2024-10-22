package com.openquartz.easystatemechine.builder;

import com.openquartz.easystatemechine.Action;

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
