package com.openquartz.easystatemachine.builder;

import com.openquartz.easystatemachine.Action;
import com.openquartz.easystatemachine.Guard;
import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.Transition;
import com.openquartz.easystatemachine.impl.StateHelper;
import com.openquartz.easystatemachine.impl.TransitionType;
import java.util.Map;

/**
 * TransitionBuilderImpl
 */
class TransitionBuilderImpl<S, E, C>
    implements ExternalTransitionBuilder<S, E, C>, InternalTransitionBuilder<S, E, C>, InitTransitionBuilder<S, E, C>,
    From<S, E, C>, On<S, E, C>, To<S, E, C> {

    final Map<S, State<S, E, C>> stateMap;

    private State<S, E, C> source;

    protected State<S, E, C> target;

    private Transition<S, E, C> transition;

    final TransitionType transitionType;

    public TransitionBuilderImpl(Map<S, State<S, E, C>> stateMap, TransitionType transitionType) {
        this.stateMap = stateMap;
        this.transitionType = transitionType;
    }

    @Override
    public From<S, E, C> from(S stateId) {
        source = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public To<S, E, C> to(S stateId) {
        target = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public To<S, E, C> within(S stateId) {
        source = target = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public To<S, E, C> init(S stateId) {
        source = StateHelper.getSourceState(stateId.getClass());
        target = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public When<S, E, C> when(Guard<C> guard) {
        transition.setGuard(guard);
        return this;
    }

    @Override
    public On<S, E, C> on(E event) {
        transition = source.addTransition(event, target, transitionType);
        return this;
    }

    @Override
    public void perform(Action<S, E, C> action) {
        transition.setAction(action);
    }

}
