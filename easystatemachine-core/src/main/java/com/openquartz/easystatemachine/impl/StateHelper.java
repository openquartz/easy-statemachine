package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * StateHelper
 */
public class StateHelper {

    private static final Map<Class<?>, State<?, ?, ?>> SOURCE_STATE_MAP = new ConcurrentHashMap<>();

    public static <S, E, C> State<S, E, C> getState(Map<S, State<S, E, C>> stateMap, S stateId) {
        return stateMap.computeIfAbsent(stateId, StateImpl::new);
    }

    @SuppressWarnings("unchecked")
    public static <S, E, C> State<S, E, C> getSourceState(Class<?> stateIdClass) {
        return  (SourceStateImpl<S, E, C>) SOURCE_STATE_MAP.computeIfAbsent(
            stateIdClass, k -> new SourceStateImpl<>(stateIdClass));
    }

    public static <S, E, C> List<State<S, E, C>> getStates(Map<S, State<S, E, C>> stateMap, S... stateId) {

        if (Objects.isNull(stateId) || stateId.length == 0) {
            return Collections.emptyList();
        }

        List<State<S, E, C>> list = new ArrayList<>();
        for (S s : stateId) {
            list.add(getState(stateMap, s));
        }
        return list;
    }

    public static <S, E, C> State<S, E, C> getEndState(Map<S, State<S, E, C>> stateMap, S stateId) {
        return stateMap.put(stateId, new EndStateImpl<>(stateMap.computeIfAbsent(stateId, StateImpl::new)));
    }

    public static <S, E, C> List<State<S, E, C>> getEndStates(Map<S, State<S, E, C>> stateMap, S... stateId) {

        if (Objects.isNull(stateId) || stateId.length == 0) {
            return Collections.emptyList();
        }

        List<State<S, E, C>> list = new ArrayList<>();
        for (S s : stateId) {
            list.add(getEndState(stateMap, s));
        }
        return list;
    }
}
