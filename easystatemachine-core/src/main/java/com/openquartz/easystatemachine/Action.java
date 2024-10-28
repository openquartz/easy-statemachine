package com.openquartz.easystatemachine;

/**
 *状态机使用的通用策略接口，通过使用 {@link StateContext} 执行 {@code Action} 来响应事件。
 */
public interface Action<S, E, C> {

    void execute(S from, S to, E event, C context);

}
