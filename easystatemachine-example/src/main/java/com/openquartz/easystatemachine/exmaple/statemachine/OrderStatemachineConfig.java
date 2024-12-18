package com.openquartz.easystatemachine.exmaple.statemachine;

import static com.openquartz.easystatemachine.exmaple.model.OrderStatusEnum.CANCELED;
import static com.openquartz.easystatemachine.exmaple.model.OrderStatusEnum.DELIVERED;
import static com.openquartz.easystatemachine.exmaple.model.OrderStatusEnum.FINISHED;
import static com.openquartz.easystatemachine.exmaple.model.OrderStatusEnum.PAID;
import static com.openquartz.easystatemachine.exmaple.model.OrderStatusEnum.REFUNDED;
import static com.openquartz.easystatemachine.exmaple.model.OrderStatusEnum.UNPAID;

import com.openquartz.easystatemachine.Action;
import com.openquartz.easystatemachine.Guard;
import com.openquartz.easystatemachine.StateMachine;
import com.openquartz.easystatemachine.builder.AlertFailCallback;
import com.openquartz.easystatemachine.builder.StateMachineBuilder;
import com.openquartz.easystatemachine.builder.StateMachineBuilderFactory;
import com.openquartz.easystatemachine.exmaple.model.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class OrderStatemachineConfig {

    public static final String ORDER_STATEMACHINE_ID = "orderStateMachine";

    @Bean
    public StateMachine<OrderStatusEnum, OrderEventEnum, OrderStateContext> orderStateMachine() {

        StateMachineBuilderFactory<OrderStatusEnum, OrderEventEnum, OrderStateContext> builderFactory =
            StateMachineBuilderFactory.declare(OrderStatusEnum.class);
        StateMachineBuilder<OrderStatusEnum, OrderEventEnum, OrderStateContext> builder = builderFactory
            .end(FINISHED, CANCELED, REFUNDED)
            .create();

        // 初始化状态
        builder.initTransition()
            .init(UNPAID)
            .on(OrderEventEnum.CREATE)
            .when(checkCreateCondition())
            .perform(doCreate());

        // 未支付 -> 已支付
        builder.externalTransition()
            .from(UNPAID)
            .to(PAID)
            .on(OrderEventEnum.PAY)
            .when(checkPaidCondition())
            .perform(doPay());

        // 已支付 -> 已发货
        builder.externalTransition()
            .from(PAID)
            .to(DELIVERED)
            .on(OrderEventEnum.DELIVER)
            .when(checkDeliverCondition())
            .perform(doDeliver());

        // 已发货 -> 已发货
        builder.internalTransition()
            .within(DELIVERED)
            .on(OrderEventEnum.DELIVER)
            .when(checkDeliverCondition())
            .perform(doDeliver());

        // 已发货 -> 已完成
        builder.externalTransition()
            .from(DELIVERED)
            .to(FINISHED)
            .on(OrderEventEnum.FINISH)
            .when(checkFinishCondition())
            .perform(doFinish());

        // 未支付 -> 已取消
        builder.externalTransition()
            .from(UNPAID)
            .to(CANCELED)
            .on(OrderEventEnum.CANCEL)
            .when(checkCancelCondition())
            .perform(doCancel());

        // 已支付 -> 已退款
        builder.externalTransition()
            .from(PAID)
            .to(REFUNDED)
            .on(OrderEventEnum.REFUND)
            .when(checkRefundCondition())
            .perform(doRefund());

        builder.setFailCallback(new AlertFailCallback<>());
        return builder.build(ORDER_STATEMACHINE_ID);
    }

    private Action<OrderStatusEnum, OrderEventEnum, OrderStateContext> doRefund() {
        return (from, to, event, context) ->
            log.info("[OrderStatemachineConfig#doRefund] state:{}->{},event:{},context:{}", from, to, event, context);
    }

    private Guard<OrderStateContext> checkRefundCondition() {
        return (ctx) -> true;
    }

    private Guard<OrderStateContext> checkCancelCondition() {
        return (ctx) -> true;
    }

    private Action<OrderStatusEnum, OrderEventEnum, OrderStateContext> doCancel() {
        return (from, to, event, context) ->
            log.info("[OrderStatemachineConfig#doCancel] state:{}->{},event:{},context:{}", from, to, event, context);
    }

    private Action<OrderStatusEnum, OrderEventEnum, OrderStateContext> doFinish() {
        return (from, to, event, context) ->
            log.info("[OrderStatemachineConfig#doFinish] state:{}->{},event:{},context:{}", from, to, event, context);
    }

    private Guard<OrderStateContext> checkFinishCondition() {
        return (ctx) -> true;
    }

    private Action<OrderStatusEnum, OrderEventEnum, OrderStateContext> doDeliver() {
        return (from, to, event, context) ->
            log.info("[OrderStatemachineConfig#doDeliver] state:{}->{},event:{},context:{}", from, to, event, context);
    }

    private Guard<OrderStateContext> checkDeliverCondition() {
        return (ctx) -> true;
    }

    private Action<OrderStatusEnum, OrderEventEnum, OrderStateContext> doPay() {
        return (from, to, event, context) ->
            log.info("[OrderStatemachineConfig#doPay] state:{}->{},event:{},context:{}", from, to, event, context);
    }

    private Guard<OrderStateContext> checkPaidCondition() {
        return (ctx) -> true;
    }

    private Guard<OrderStateContext> checkCreateCondition() {
        return (ctx) -> true;
    }

    private Action<OrderStatusEnum, OrderEventEnum, OrderStateContext> doCreate() {
        return (from, to, event, context) ->
            log.info("[OrderStatemachineConfig#doCreate] state:{}->{},event:{},context:{}", from, to, event, context);
    }

}
