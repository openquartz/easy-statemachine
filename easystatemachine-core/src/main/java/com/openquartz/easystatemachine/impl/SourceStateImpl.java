package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.AbstractState;

public class SourceStateImpl<S, E, C> extends AbstractState<S, E, C> {
    private final Class<S> stateIdClass;

    protected SourceStateImpl(Class<S> stateIdClass) {
        this.stateIdClass = stateIdClass;
    }

    @Override
    public S getId() {
        return null;
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
