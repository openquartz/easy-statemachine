package com.openquartz.easystatemachine.builder;

/**
 * Init transition builder
 * @param <S> S
 * @param <E> E
 * @param <C> C
 */
public interface InitTransitionBuilder<S, E, C> {

    /**
     * Build a init transition
     * @param stateId id of transition
     * @return To clause builder
     */
    To<S, E, C> init(S stateId);
}
