package com.openquartz.easystatemechine;

/**
 * Visitable
 */
public interface Visitable {
    String accept(final Visitor visitor);
}
