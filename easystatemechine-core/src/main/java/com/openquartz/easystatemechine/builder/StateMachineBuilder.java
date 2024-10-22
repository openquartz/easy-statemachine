package com.openquartz.easystatemechine.builder;

import com.openquartz.easystatemechine.StateMachine;

/**
 * StateMachineBuilder
 */
public interface StateMachineBuilder<S, E, C> {

    /**
     * Builder for one transition
     *
     * @return External transition builder
     */
    ExternalTransitionBuilder<S, E, C> externalTransition();

    /**
     * Builder for multiple transitions
     *
     * @return External transition builder
     */
    ExternalTransitionsBuilder<S, E, C> externalTransitions();

    /**
     * Start to build internal transition
     *
     * @return Internal transition builder
     */
    InternalTransitionBuilder<S, E, C> internalTransition();

    /**
     * set up fail callback, default do nothing {@code NumbFailCallbackImpl}
     *
     * @param callback
     */
    void setFailCallback(FailCallback<S, E, C> callback);


    StateMachine<S, E, C> build(String machineId);

}
