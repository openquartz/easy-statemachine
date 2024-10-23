package com.openquartz.easystatemachine.builder;

/**
 * ExternalTransitionsBuilder
 *
 * This builder is for multiple transitions, currently only support multiple sources <----> one target
 */
public interface ExternalTransitionsBuilder<S, E, C> {
    From<S, E, C> fromAmong(S... stateIds);
}
