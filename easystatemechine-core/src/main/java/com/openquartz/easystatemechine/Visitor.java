package com.openquartz.easystatemechine;

import com.openquartz.easystatemechine.impl.PlantUMLVisitor;
import com.openquartz.easystatemechine.impl.SysOutVisitor;

/**
 * Visitor
 */
public interface Visitor {

    char LF = '\n';

    Visitor SYSTEM_OUT = new SysOutVisitor();
    Visitor PLANT_UML = new PlantUMLVisitor();

    /**
     * @param visitable the element to be visited.
     * @return
     */
    String visitOnEntry(StateMachine<?, ?, ?> visitable);

    /**
     * @param visitable the element to be visited.
     * @return
     */
    String visitOnExit(StateMachine<?, ?, ?> visitable);

    /**
     * @param visitable the element to be visited.
     * @return
     */
    String visitOnEntry(State<?, ?, ?> visitable);

    /**
     * @param visitable the element to be visited.
     * @return
     */
    String visitOnExit(State<?, ?, ?> visitable);
}
