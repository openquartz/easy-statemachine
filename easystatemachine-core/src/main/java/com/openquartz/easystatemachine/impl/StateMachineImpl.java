package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.StateMachine;
import com.openquartz.easystatemachine.Transition;
import com.openquartz.easystatemachine.Visitor;
import com.openquartz.easystatemachine.builder.FailCallback;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 出于性能考虑，状态机是故意设为 “无状态” 的。一旦构建完成，就可以被多线程共享
 <p>一个副作用是，由于状态机是无状态的，我们无法从 State Machine 获取当前状态。
 */
@Slf4j
public class StateMachineImpl<S, E, C> implements StateMachine<S, E, C> {

    private String machineId;

    private final Map<S, State<S, E, C>> stateMap;

    private boolean ready;

    private FailCallback<S, E, C> failCallback;

    public StateMachineImpl(Map<S, State<S, E, C>> stateMap) {
        this.stateMap = stateMap;
    }

    @Override
    public boolean verify(S sourceStateId, E event) {
        isReady();

        State<S, E, C> sourceState = getState(sourceStateId);

        List<Transition<S, E, C>> transitions = sourceState.getEventTransitions(event);

        return transitions != null && !transitions.isEmpty();
    }

    @Override
    public S fireEvent(S sourceStateId, E event, C ctx) {
        isReady();
        Transition<S, E, C> transition = routeTransition(sourceStateId, event, ctx);

        if (transition == null) {
            Debugger.debug("There is no Transition for " + event);
            failCallback.onFail(sourceStateId, event, ctx);
            return sourceStateId;
        }

        return transition.transit(ctx, false).getId();
    }

    private Transition<S, E, C> routeTransition(S sourceStateId, E event, C ctx) {
        State<S, E, C> sourceState = getState(sourceStateId);

        List<Transition<S, E, C>> transitions = sourceState.getEventTransitions(event);

        if (transitions == null || transitions.isEmpty()) {
            return null;
        }

        Transition<S, E, C> transit = null;
        for (Transition<S, E, C> transition : transitions) {
            if (transition.getCondition() == null) {
                transit = transition;
            } else if (transition.getCondition().isSatisfied(ctx)) {
                transit = transition;
                break;
            }
        }

        return transit;
    }

    private State<S, E, C> getState(S currentStateId) {
        State<S, E, C> state = StateHelper.getState(stateMap, currentStateId);
        if (state == null) {
            show(Visitor.SYSTEM_OUT);
            throw new StateMachineException(currentStateId + " is not found, please check state machine");
        }
        return state;
    }

    private void isReady() {
        if (!ready) {
            throw new StateMachineException("State machine is not built yet, can not work");
        }
    }

    @Override
    public String accept(Visitor visitor) {

        List<State<?, ?, ?>> startStateList = stateMap.values()
            .stream()
            .filter(e -> e instanceof StartStateImpl)
            .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append(visitor.visitOnEntry(this, startStateList));

        for (State<S, E, C> state : stateMap.values()) {
            sb.append(state.accept(visitor));
        }

        List<State<?, ?, ?>> endStateList = stateMap.values()
            .stream()
            .filter(e -> e instanceof EndStateImpl)
            .collect(Collectors.toList());

        sb.append(visitor.visitOnExit(this, endStateList));
        return sb.toString();
    }

    @Override
    public void show(Visitor visitor) {
        String accept = accept(visitor);
        log.info(">>>>>>>>>>>>>>>>>---------StateMachine({}) Start--------->>>>>>>>>>>>>>>>>\n{}", machineId, accept);
        log.info(">>>>>>>>>>>>>>>>>---------StateMachine({}) End--------->>>>>>>>>>>>>>>>>", machineId);
    }

    @Override
    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setFailCallback(FailCallback<S, E, C> failCallback) {
        this.failCallback = failCallback;
    }
}
