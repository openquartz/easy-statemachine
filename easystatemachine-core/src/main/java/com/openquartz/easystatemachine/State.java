package com.openquartz.easystatemachine;

import com.openquartz.easystatemachine.impl.TransitionType;
import java.util.Collection;
import java.util.List;

/**
 * State
 *
 * @param <S> the type of state
 * @param <E> the type of event
 */
public interface State<S, E, C> extends Visitable {

    /**
     * Gets the state identifier.
     *
     * @return the state identifiers
     */
    S getId();

    /**
     * Add transition to the state
     * @param event the event of the Transition
     * @param target the target of the transition
     * @return the transition
     */
    Transition<S,E,C> addTransition(E event, State<S, E, C> target, TransitionType transitionType);

    /**
     * add transitions to the state
     * @param event event
     * @param targets target states
     * @param transitionType transition type
     * @return transitions
     */
    List<Transition<S,E,C>> addTransitions(E event, List<State<S, E, C>> targets, TransitionType transitionType);

    /**
     * get transitions by event
     * @param event event
     * @return transitions
     */
    List<Transition<S,E,C>> getEventTransitions(E event);

    /**
     * get all transitions
     * @return transitions
     */
    Collection<Transition<S,E,C>> getAllTransitions();

}
