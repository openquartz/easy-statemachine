package com.openquartz.easystatemachine.builder;

import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.impl.StateHelper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StateMachineBuilderFactory
 * @author svnee
 */
public class StateMachineBuilderFactory<S, E, C> {

    private StateMachineBuilderFactory(Class<S> stateIdClass) {
        this.stateIdClass = stateIdClass;
    }

    private final Class<S> stateIdClass;

    /**
     * end state
     */
    private S[] endStateIds;

    public static <S, E, C> StateMachineBuilderFactory<S, E, C> declare(Class<S> stateClass) {
        return new StateMachineBuilderFactory<>(stateClass);
    }

    public StateMachineBuilderFactory<S, E, C> end(S... endStateIds) {
        this.endStateIds = endStateIds;
        return this;
    }

    public StateMachineBuilder<S, E, C> create() {

        Map<S, State<S, E, C>> stateMap = new ConcurrentHashMap<>();
        StateHelper.getEndStates(stateMap, endStateIds);
        return new StateMachineBuilderImpl<>(stateMap, stateIdClass);
    }
}
