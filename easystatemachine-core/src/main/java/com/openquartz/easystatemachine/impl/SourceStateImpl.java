package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.Transition;
import com.openquartz.easystatemachine.Visitor;
import java.util.Collection;
import java.util.List;

public class SourceStateImpl<S, E, C> implements State<S, E, C> {

    private final EventTransitions<S, E, C> eventTransitions = new EventTransitions<>();

    private final Class<S> stateIdClass;

    protected SourceStateImpl(Class<S> stateIdClass) {
        this.stateIdClass = stateIdClass;
    }

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
    public List<Transition<S, E, C>> getEventTransitions(E event) {
        return eventTransitions.get(event);
    }

    @Override
    public Collection<Transition<S, E, C>> getAllTransitions() {
        return eventTransitions.allTransitions();
    }

    @Override
    public S getId() {
        return null;
    }

    @Override
    public String accept(Visitor visitor) {
        String entry = visitor.visitOnEntry(this);
        String exit = visitor.visitOnExit(this);
        return entry + exit;
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject instanceof SourceStateImpl) {
            SourceStateImpl<?, ?, ?> other = (SourceStateImpl<?, ?, ?>) anObject;
            return this.stateIdClass.equals(other.stateIdClass);
        }
        return false;
    }

    @Override
    public String toString() {
        return "*";
    }
}
