package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.Action;
import com.openquartz.easystatemachine.Guard;
import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.Transition;
import java.util.Objects;

/**
 * TransitionImpl。
 *
 * This should be designed to be immutable, so that there is no thread-safe risk
 */
public class TransitionImpl<S, E, C> implements Transition<S, E, C> {

    private State<S, E, C> source;

    private State<S, E, C> target;

    private E event;

    private Guard<C> guard;

    private Action<S, E, C> action;

    private TransitionType type = TransitionType.EXTERNAL;

    @Override
    public State<S, E, C> getSource() {
        return source;
    }

    @Override
    public void setSource(State<S, E, C> state) {
        this.source = state;
    }

    @Override
    public E getEvent() {
        return this.event;
    }

    @Override
    public void setEvent(E event) {
        this.event = event;
    }

    @Override
    public void setType(TransitionType type) {
        this.type = type;
    }

    @Override
    public State<S, E, C> getTarget() {
        return this.target;
    }

    @Override
    public void setTarget(State<S, E, C> target) {
        this.target = target;
    }

    @Override
    public Guard<C> getGuard() {
        return this.guard;
    }

    @Override
    public void setGuard(Guard<C> guard) {
        this.guard = guard;
    }

    @Override
    public Action<S, E, C> getAction() {
        return this.action;
    }

    @Override
    public void setAction(Action<S, E, C> action) {
        this.action = action;
    }

    @Override
    public State<S, E, C> transit(C ctx, boolean checkCondition) {
        Debugger.debug("Do transition: " + this);
        this.verify();
        if (!checkCondition || guard == null || guard.isSatisfied(ctx)) {
            if (action != null) {
                action.execute(source.getId(), target.getId(), event, ctx);
            }
            return target;
        }

        Debugger.debug("Condition is not satisfied, stay at the " + source + " state ");
        return source;
    }

    @Override
    public final String toString() {
        return source + "-[" + event.toString() + ", " + type + "]->" + target;
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject instanceof Transition) {
            Transition<?, ?, ?> other = (Transition<?, ?, ?>) anObject;
            return Objects.equals(this.event, other.getEvent())
                && Objects.equals(this.source, other.getSource())
                && Objects.equals(this.target, other.getTarget());
        }
        return false;
    }

    @Override
    public void verify() {
        if (type == TransitionType.INTERNAL && source != target) {
            throw new StateMachineException(String.format("Internal transition source state '%s' " +
                "and target state '%s' must be same.", source, target));
        }

        if (type == TransitionType.INIT && !(source instanceof SourceStateImpl)) {
            throw new StateMachineException(
                String.format("Init transition source state '%s' must be %s.", source,
                    SourceStateImpl.class.getName()));
        }
    }
}
