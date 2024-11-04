package com.openquartz.easystatemachine;

import com.openquartz.easystatemachine.impl.PlantUMLVisitor;
import com.openquartz.easystatemachine.impl.SysOutVisitor;
import java.util.List;

/**
 * Visitor
 */
public interface Visitor {

    char LF = '\n';

    Visitor SYSTEM_OUT = new SysOutVisitor();
    Visitor PLANT_UML = new PlantUMLVisitor();

    /**
     * @param visitable the element to be visited.
     */
    String visitOnEntry(StateMachine<?, ?, ?> visitable, List<State<?, ?, ?>> startStateList);

    /**
     * @param visitable the element to be visited.
     */
    String visitOnExit(StateMachine<?, ?, ?> visitable, List<State<?, ?, ?>> endStateList);

    /**
     * @param visitable the element to be visited.
     */
    String visitOnEntry(State<?, ?, ?> visitable);

    /**
     * @param visitable the element to be visited.
     */
    String visitOnExit(State<?, ?, ?> visitable);
}
