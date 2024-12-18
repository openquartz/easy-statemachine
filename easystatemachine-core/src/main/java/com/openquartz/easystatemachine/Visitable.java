package com.openquartz.easystatemachine;

/**
 * Visitable
 */
public interface Visitable {

    /***
     * Accept visitor
     * @param visitor visitor
     * @return accept result
     */
    String accept(final Visitor visitor);

}
