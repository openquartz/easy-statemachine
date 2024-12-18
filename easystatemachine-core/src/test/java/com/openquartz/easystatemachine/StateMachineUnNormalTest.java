package com.openquartz.easystatemachine;

import com.openquartz.easystatemachine.StateMachineTest.Context;
import com.openquartz.easystatemachine.StateMachineTest.Events;
import com.openquartz.easystatemachine.StateMachineTest.States;
import com.openquartz.easystatemachine.builder.StateMachineBuilder;
import com.openquartz.easystatemachine.builder.StateMachineBuilderFactory;
import com.openquartz.easystatemachine.impl.StateMachineException;
import org.junit.Assert;
import org.junit.Test;

/**
 * StateMachineUnNormalTest
 */
public class StateMachineUnNormalTest {

    @Test
    public void testConditionNotMeet() {

        StateMachineBuilderFactory<States, Events, Context> builderFactory =
            StateMachineBuilderFactory.declare(States.class);

        StateMachineBuilder<StateMachineTest.States, StateMachineTest.Events, StateMachineTest.Context> builder =
            builderFactory.end(States.STATE2).create();

        builder.externalTransition()
            .from(StateMachineTest.States.STATE1)
            .to(StateMachineTest.States.STATE2)
            .on(StateMachineTest.Events.EVENT1)
            .when(checkConditionFalse())
            .perform(doAction());

        StateMachine<StateMachineTest.States, StateMachineTest.Events, StateMachineTest.Context> stateMachine =
            builder.build("NotMeetConditionMachine");
        StateMachineTest.States target = stateMachine.fireEvent(StateMachineTest.States.STATE1,
            StateMachineTest.Events.EVENT1, new StateMachineTest.Context());
        Assert.assertEquals(StateMachineTest.States.STATE1, target);
    }


    @Test(expected = StateMachineException.class)
    public void testDuplicatedTransition() {

        StateMachineBuilderFactory<States, Events, Context> builderFactory =
            StateMachineBuilderFactory.declare(States.class);
        StateMachineBuilder<StateMachineTest.States, StateMachineTest.Events, StateMachineTest.Context> builder =
            builderFactory.end(States.STATE2).create();

        builder.externalTransition()
            .from(StateMachineTest.States.STATE1)
            .to(StateMachineTest.States.STATE2)
            .on(StateMachineTest.Events.EVENT1)
            .when(checkCondition())
            .perform(doAction());

        builder.externalTransition()
            .from(StateMachineTest.States.STATE1)
            .to(StateMachineTest.States.STATE2)
            .on(StateMachineTest.Events.EVENT1)
            .when(checkCondition())
            .perform(doAction());
    }

    @Test(expected = StateMachineException.class)
    public void testDuplicateMachine() {

        StateMachineBuilderFactory<States, Events, Context> builderFactory =
            StateMachineBuilderFactory.declare(States.class);

        StateMachineBuilder<States, Events, Context> builder = builderFactory.create();

        builder.externalTransition()
            .from(StateMachineTest.States.STATE1)
            .to(StateMachineTest.States.STATE2)
            .on(StateMachineTest.Events.EVENT1)
            .when(checkCondition())
            .perform(doAction());

        builder.build("DuplicatedMachine");
        builder.build("DuplicatedMachine");
    }

    private Guard<Context> checkCondition() {
        return (ctx) -> true;
    }

    private Guard<Context> checkConditionFalse() {
        return (ctx) -> false;
    }

    private Action<StateMachineTest.States, StateMachineTest.Events, StateMachineTest.Context> doAction() {
        return (from, to, event, ctx) -> System.out.println(ctx.operator + " is operating " + ctx.entityId + "from:" + from + " to:" + to + " on:" + event);
    }
}
