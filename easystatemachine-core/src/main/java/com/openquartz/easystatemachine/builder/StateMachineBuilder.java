package com.openquartz.easystatemachine.builder;

import com.openquartz.easystatemachine.StateMachine;

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
     * Builder for parallel transitions
     *
     * @return External transition builder
     */
    ExternalParallelTransitionBuilder<S, E, C> externalParallelTransition();

    /**
     * Start to build internal transition
     *
     * @return Internal transition builder
     */
    InternalTransitionBuilder<S, E, C> internalTransition();

    /**
     * Start to build init transition
     * @return Init transition builder
     */
    InitTransitionBuilder<S, E, C> initTransition();

    /**
     * set up fail callback, default do nothing {@code NumbFailCallbackImpl}
     *
     * @param callback callback
     */
    void setFailCallback(FailCallback<S, E, C> callback);

    /**
     * build state machine entry
     * @param machineId machineId
     * @return state machine
     */
    StateMachine<S, E, C> build(String machineId);

}
