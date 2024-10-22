package com.openquartz.easystatemechine;

/**
 * Generic strategy interface used by a state machine to respond
 * events by executing an {@code Action} with a {@link StateContext}.
 */
public interface Action<S, E, C> {

    void execute(S from, S to, E event, C context);

}
