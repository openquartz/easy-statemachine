package com.openquartz.easystatemachine;

import com.openquartz.easystatemachine.impl.Debugger;
import com.openquartz.easystatemachine.impl.EventTransitions;
import com.openquartz.easystatemachine.impl.TransitionImpl;
import com.openquartz.easystatemachine.impl.TransitionType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractState<S,E,C> implements State<S,E,C> {

    private final EventTransitions<S, E, C> eventTransitions = new EventTransitions<>();

    @Override
    public Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType transitionType) {
        Transition<S, E, C> newTransition = new TransitionImpl<>();
        newTransition.setSource(this);
        newTransition.setTarget(target);
        newTransition.setEvent(event);
        newTransition.setType(transitionType);

        Debugger.debug("Begin to add new transition: " + newTransition);

        eventTransitions.put(event, newTransition);
        return newTransition;
    }

    @Override
    public List<Transition<S, E, C>> addTransitions(E event, List<State<S, E, C>> targets,
        TransitionType transitionType) {
        List<Transition<S, E, C>> result = new ArrayList<>();
        for (State<S, E, C> target : targets) {
            Transition<S, E, C> secTransition = addTransition(event, target, transitionType);
            result.add(secTransition);
        }

        return result;
    }

    @Override
    public List<Transition<S, E, C>> getEventTransitions(E event) {
        return eventTransitions.get(event);
    }

    @Override
    public Collection<Transition<S, E, C>> getAllTransitions() {
        return eventTransitions.allTransitions();
    }

    @Override
    public String accept(Visitor visitor) {
        String entry = visitor.visitOnEntry(this);
        String exit = visitor.visitOnExit(this);
        return entry + exit;
    }


}
