package com.openquartz.easystatemachine.builder;

import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.StateMachine;
import com.openquartz.easystatemachine.StateMachineFactory;
import com.openquartz.easystatemachine.impl.StateMachineImpl;
import com.openquartz.easystatemachine.impl.TransitionType;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StateMachineBuilderImpl
 */
public class StateMachineBuilderImpl<S, E, C> implements StateMachineBuilder<S, E, C> {

    /**
     * StateMap is the same with stateMachine, as the core of state machine is holding reference to states.
     */
    private final Map<S, State<S, E, C>> stateMap;
    private final StateMachineImpl<S, E, C> stateMachine;
    private FailCallback<S, E, C> failCallback = new NumbFailCallback<>();

    public StateMachineBuilderImpl(Map<S, State<S, E, C>> stateMap) {
        if (Objects.nonNull(stateMap)) {
            this.stateMap = stateMap;
        } else {
            this.stateMap = new ConcurrentHashMap<>();
        }

        this.stateMachine = new StateMachineImpl<>(this.stateMap);
    }

    @Override
    public ExternalTransitionBuilder<S, E, C> externalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public ExternalTransitionsBuilder<S, E, C> externalTransitions() {
        return new TransitionsBuilderImpl<>(stateMap, TransitionType.EXTERNAL);
    }

    @Override
    public InternalTransitionBuilder<S, E, C> internalTransition() {
        return new TransitionBuilderImpl<>(stateMap, TransitionType.INTERNAL);
    }

    @Override
    public void setFailCallback(FailCallback<S, E, C> callback) {
        this.failCallback = callback;
    }

    @Override
    public StateMachine<S, E, C> build(String machineId) {
        stateMachine.setMachineId(machineId);
        stateMachine.setReady(true);
        stateMachine.setFailCallback(failCallback);
        StateMachineFactory.register(stateMachine);
        return stateMachine;
    }

}
