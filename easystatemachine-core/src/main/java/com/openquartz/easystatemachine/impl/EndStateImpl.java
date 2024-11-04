package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.Transition;
import com.openquartz.easystatemachine.Visitor;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class EndStateImpl<S, E, C> implements State<S, E, C> {

    private final State<S, E, C> state;

    public EndStateImpl(State<S, E, C> state) {
        this.state = state;
    }

    @Override
    public S getId() {
        return state.getId();
    }

    @Override
    public Transition<S, E, C> addTransition(E event, State<S, E, C> target, TransitionType transitionType) {
        return state.addTransition(event, target, transitionType);
    }

    @Override
    public List<Transition<S, E, C>> addTransitions(E event, List<State<S, E, C>> targets,
        TransitionType transitionType) {
        return state.addTransitions(event, targets, transitionType);
    }

    @Override
    public List<Transition<S, E, C>> getEventTransitions(E event) {
        return state.getEventTransitions(event);
    }

    @Override
    public Collection<Transition<S, E, C>> getAllTransitions() {
        return state.getAllTransitions();
    }

    @Override
    public String accept(Visitor visitor) {
        return state.accept(visitor);
    }

    @Override
    public String toString() {
        return Optional.ofNullable(getId()).map(Object::toString).orElse("*");
    }
}
