package com.openquartz.easystatemachine.builder;

import com.openquartz.easystatemachine.State;
import com.openquartz.easystatemachine.impl.TransitionType;
import java.util.Map;

/**
 * 以 TransitionBuilderImpl 和 TransitionsBuilderImpl 将变量和方法共享给该抽象类，该抽象类充当其父类，
 * 而不是让 TransitionsBuilderImpl 继承自 TransitionsBuilderImpl。我认为多流构建器（TransitionsBuilderImpl）和单流构建器（TransitionBuilderImpl）是相等的，
 * 不应该是父子关系，它们的 from、when 和 perform 方法并不相同，
 * 虽然它看起来只是一组循环，但逻辑上不应该继承 Override。（就像没有关系一样，我们为什么要互相交谈，说一个我们不适合）。
 * 用抽象类来说，multi-flow 和 single-flow 只用来关注各自的功能是 single-flow，或者说 multi-flow。符合单个职责。
 */
abstract class AbstractTransitionBuilder<S, E, C>  {

    final Map<S, State<S, E, C>> stateMap;

    protected State<S, E, C> target;

    final TransitionType transitionType;

    public AbstractTransitionBuilder(Map<S, State<S, E, C>> stateMap, TransitionType transitionType) {
        this.stateMap = stateMap;
        this.transitionType = transitionType;
    }

}
