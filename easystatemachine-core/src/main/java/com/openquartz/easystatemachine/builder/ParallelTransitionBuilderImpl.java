package com.openquartz.easystatemachine.builder;

import com.openquartz.easystatemachine.Action;
import com.openquartz.easystatemachine.Condition;
import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.Transition;
import com.openquartz.easystatemachine.impl.StateHelper;
import com.openquartz.easystatemachine.impl.TransitionType;
import java.util.List;
import java.util.Map;

class ParallelTransitionBuilderImpl<S, E, C>
    extends AbstractParallelTransitionBuilder<S, E, C>
    implements ExternalParallelTransitionBuilder<S, E, C> {

    private State<S, E, C> source;
    private List<Transition<S, E, C>> transitions;

    public ParallelTransitionBuilderImpl(Map<S, State<S, E, C>> stateMap, TransitionType transitionType) {
        super(stateMap, transitionType);
    }

    @Override
    public ParallelFrom<S, E, C> from(S stateId) {
        source = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public When<S, E, C> when(Condition<C> condition) {
        for (Transition<S, E, C> transition : transitions) {
            transition.setCondition(condition);
        }
        return this;
    }

    @Override
    public On<S, E, C> on(E event) {
        transitions = source.addTransitions(event, targets, transitionType);
        return this;
    }

    @Override
    public void perform(Action<S, E, C> action) {
        for (Transition<S, E, C> transition : transitions) {
            transition.setAction(action);
        }
    }


}
