package com.openquartz.easystatemachine.builder;

import com.openquartz.easystatemachine.Guard;

/**
 * On
 */
public interface On<S, E, C> extends When<S, E, C>{

    /**
     * Add guard for the transition
     * @param guard transition condition
     * @return When clause builder
     */
    When<S, E, C> when(Guard<C> guard);
}
