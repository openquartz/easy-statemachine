package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * StateHelper
 */
public class StateHelper {

    public static <S, E, C> State<S, E, C> getState(Map<S, State<S, E, C>> stateMap, S stateId) {
        return stateMap.computeIfAbsent(stateId, StateImpl::new);
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

    public static <S, E, C> State<S, E, C> getStartState(Map<S, State<S, E, C>> stateMap, S stateId) {
        return stateMap.put(stateId, new StartStateImpl<>(stateMap.computeIfAbsent(stateId, StateImpl::new)));
    }

    public static <S, E, C> List<State<S, E, C>> getStartStates(Map<S, State<S, E, C>> stateMap, S... stateId) {

        if (Objects.isNull(stateId) || stateId.length == 0) {
            return Collections.emptyList();
        }

        List<State<S, E, C>> list = new ArrayList<>();
        for (S s : stateId) {
            list.add(getStartState(stateMap, s));
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
