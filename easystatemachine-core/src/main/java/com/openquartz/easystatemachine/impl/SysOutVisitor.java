package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.StateMachine;
import com.openquartz.easystatemachine.Transition;
import com.openquartz.easystatemachine.Visitor;
import java.util.List;
import java.util.Optional;

/**
 * SysOutVisitor
 */
public class SysOutVisitor implements Visitor {

    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> stateMachine, List<State<?, ?, ?>> startStateList) {
        String entry = "-----StateMachine:" + stateMachine.getMachineId() + "-------";
        System.out.println(entry);
        return entry;
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> visitable, List<State<?, ?, ?>> endStateList) {
        String exit = "------------------------";
        System.out.println(exit);
        return exit;
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        String stateStr = "State:" + Optional.ofNullable(state.getId()).map(Object::toString).orElse("*");
        sb.append(stateStr).append(LF);
        System.out.println(stateStr);
        for (Transition<?, ?, ?> transition : state.getAllTransitions()) {
            String transitionStr = "    Transition:" + transition;
            sb.append(transitionStr).append(LF);
            System.out.println(transitionStr);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> visitable) {
        return "";
    }
}
