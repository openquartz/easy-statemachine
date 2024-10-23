package com.openquartz.easystatemachine;

/**
 * Visitable
 */
public interface Visitable {
    String accept(final Visitor visitor);
}
