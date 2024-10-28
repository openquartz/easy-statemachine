package com.openquartz.easystatemachine.impl;

/**
 * TransitionType
 */
public enum TransitionType {
    /**
     表示 Transition（如果触发）在不退出或进入源 State 的情况下发生（即，它不会导致 State 更改）。
     这意味着不会调用源 State 的 entry 或 exit 条件。
     即使 SateMachine 位于嵌套在关联 State 内的一个或多个 Region 中，也可以采用内部 Transition。
     */
    INTERNAL,

    /**
     * 意味着 Transition（如果触发）不会退出复合（源）State，但会退出并重新进入复合 State 中当前 state 配置中的任何状态。
     */
    LOCAL,

    /**
     * 表示 Transition（如果触发）将退出复合（源）State。
     */
    EXTERNAL
}
