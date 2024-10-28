package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.Transition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EventTransitions
 *
 * 同一个Event可以触发多个Transitions
 */
public class EventTransitions<S, E, C> {

    private final Map<E, List<Transition<S, E, C>>> eventTransitionMap;

    public EventTransitions() {
        eventTransitionMap = new HashMap<>();
    }

    public void put(E event, Transition<S, E, C> transition) {
        if (eventTransitionMap.get(event) == null) {
            List<Transition<S, E, C>> transitions = new ArrayList<>();
            transitions.add(transition);
            eventTransitionMap.put(event, transitions);
        } else {
            List<Transition<S, E, C>> existingTransitions = eventTransitionMap.get(event);
            verify(existingTransitions, transition);
            existingTransitions.add(transition);
        }
    }

    /**
     * Per one source and target state, there is only one transition is allowed
     * @param existingTransitions
     * @param newTransition
     */
    private void verify(List<Transition<S, E, C>> existingTransitions, Transition<S, E, C> newTransition) {
        for (Transition<S, E, C> transition : existingTransitions) {
            if (transition.equals(newTransition)) {
                throw new StateMachineException(transition + " already Exist, you can not add another one");
            }
        }
    }

    public List<Transition<S, E, C>> get(E event) {
        return eventTransitionMap.get(event);
    }

    public List<Transition<S, E, C>> allTransitions() {
        List<Transition<S, E, C>> allTransitions = new ArrayList<>();
        for (List<Transition<S, E, C>> transitions : eventTransitionMap.values()) {
            allTransitions.addAll(transitions);
        }
        return allTransitions;
    }
}
