package com.openquartz.easystatemachine;

import com.openquartz.easystatemachine.annotation.Nullable;

/**
 *状态机使用的通用策略接口，通过使用 {@link StateContext} 执行 {@code Action} 来响应事件。
 */
@FunctionalInterface
public interface Action<S, E, C> {

    /**
     * execute action
     * @param from from state id
     * @param to to state id
     * @param event happen event
     * @param context context
     */
    void execute(@Nullable S from, S to, E event, C context);

}
