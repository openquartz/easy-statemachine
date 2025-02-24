package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.StateMachine;
import com.openquartz.easystatemachine.Transition;
import com.openquartz.easystatemachine.Visitor;
import java.util.List;
import java.util.Optional;

/**
 * PlantUMLVisitor
 */
public class PlantUMLVisitor implements Visitor {

    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> visitable, List<State<?, ?, ?>> startStateList) {
        StringBuilder sb = new StringBuilder("@startuml" + LF);
        for (State<?, ?, ?> state : startStateList) {
            sb.append(visitOnEntry(state));
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> visitable, List<State<?, ?, ?>> endStateList) {

        StringBuilder sb = new StringBuilder();

        for (State<?, ?, ?> state : endStateList) {
            sb.append(state.getId())
                .append(" --> ")
                .append("[*]")
                .append(LF);
        }

        sb.append("@enduml");
        return sb.toString();
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        for (Transition<?, ?, ?> transition : state.getAllTransitions()) {
            sb.append(Optional.ofNullable(transition.getSource().getId()).map(Object::toString).orElse("[*]"))
                .append(" --> ")
                .append(Optional.ofNullable(transition.getTarget().getId()).map(Object::toString).orElse("[*]"))
                .append(" : ")
                .append(transition.getEvent())
                .append(LF);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> state) {
        return "";
    }
}
