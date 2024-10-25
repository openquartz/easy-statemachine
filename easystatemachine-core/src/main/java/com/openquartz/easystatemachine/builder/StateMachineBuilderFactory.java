package com.openquartz.easystatemachine.builder;

import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.impl.StateHelper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StateMachineBuilderFactory
 */
public class StateMachineBuilderFactory<S, E, C> {

    private StateMachineBuilderFactory() {
    }

    private S[] startStateIds;

    private S[] endStateIds;

    public static <S, E, C> StateMachineBuilderFactory<S, E, C> declare() {
        return new StateMachineBuilderFactory<>();
    }

    public StateMachineBuilderFactory<S, E, C> start(S... startStateIds) {
        this.startStateIds = startStateIds;
        return this;
    }

    public StateMachineBuilderFactory<S, E, C> end(S... endStateIds) {
        this.endStateIds = endStateIds;
        return this;
    }

    public StateMachineBuilder<S, E, C> create() {

        Map<S, State<S, E, C>> stateMap = new ConcurrentHashMap<>();
        StateHelper.getStartStates(stateMap, startStateIds);
        StateHelper.getEndStates(stateMap, endStateIds);
        return new StateMachineBuilderImpl<>(stateMap);
    }


}
