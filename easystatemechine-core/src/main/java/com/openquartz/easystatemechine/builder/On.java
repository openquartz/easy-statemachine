package com.openquartz.easystatemechine.builder;

import com.openquartz.easystatemechine.Condition;

/**
 * On
 */
public interface On<S, E, C> extends When<S, E, C>{
    /**
     * Add condition for the transition
     * @param condition transition condition
     * @return When clause builder
     */
    When<S, E, C> when(Condition<C> condition);
}
