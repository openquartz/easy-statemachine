package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.AbstractState;
import com.openquartz.easystatemachine.State;

/**
 * StateImpl
 */
public class StateImpl<S, E, C> extends AbstractState<S, E, C> {

    protected final S stateId;

    StateImpl(S stateId) {
        this.stateId = stateId;
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject instanceof State) {
            State<?, ?, ?> other = (State<?, ?, ?>) anObject;
            return this.stateId.equals(other.getId());
        }
        return false;
    }

    @Override
    public S getId() {
        return stateId;
    }

    @Override
    public String toString() {
        return stateId.toString();
    }
}
