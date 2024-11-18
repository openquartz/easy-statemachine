package com.openquartz.easystatemachine.exmaple.service.impl;

import com.openquartz.easystatemachine.StateMachine;
import com.openquartz.easystatemachine.exmaple.model.BasicUser;
import com.openquartz.easystatemachine.exmaple.model.Order;
import com.openquartz.easystatemachine.exmaple.model.OrderStatusEnum;
import com.openquartz.easystatemachine.exmaple.request.CancelOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.CreateOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.DeliverOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.FinishOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.PayOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.RefundOrderRequest;
import com.openquartz.easystatemachine.exmaple.service.OrderService;
import com.openquartz.easystatemachine.exmaple.statemachine.OrderEventEnum;
import com.openquartz.easystatemachine.exmaple.statemachine.OrderStateContext;
import java.util.Date;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private StateMachine<OrderStatusEnum, OrderEventEnum, OrderStateContext> orderStateMachine;

    @Override
    public String create(BasicUser user, CreateOrderRequest request) {

        // 生成订单号
        String orderNo = "xxx";

        // 业务逻辑处理

        // 触发状态机
        OrderStateContext stateContext = new OrderStateContext();
        stateContext.setOrderNo(orderNo);
        stateContext.setChangeTime(new Date());
        OrderStatusEnum targetStatus = orderStateMachine.fireEvent(OrderEventEnum.CREATE, stateContext);

        // 处理订单业务逻辑

        return orderNo;
    }

    @Override
    public void pay(PayOrderRequest request) {

        // 获取订单状态
        Order order = getOrder(request.getOrderNo());

        // 校验请求

        // 触发状态机
        OrderStateContext stateContext = new OrderStateContext();
        stateContext.setOrderNo(request.getOrderNo());
        stateContext.setChangeTime(new Date());
        OrderStatusEnum targetStatus =
            orderStateMachine.fireEvent(order.getOrderStatus(), OrderEventEnum.PAY, stateContext);
        // 处理业务逻辑

    }

    private Order getOrder(String orderNo) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setOrderStatus(OrderStatusEnum.UNPAID);
        order.setOrderBy("xxx");
        order.setCreateTime(new Date());
        order.setRemark("备注");
        return order;
    }

    @Override
    public void deliver(DeliverOrderRequest request) {

        // 获取订单状态
        Order order = getOrder(request.getOrderNo());

        // 校验请求

        // 触发状态机
        OrderStateContext stateContext = new OrderStateContext();
        stateContext.setOrderNo(request.getOrderNo());
        stateContext.setChangeTime(new Date());
        OrderStatusEnum targetStatus =
            orderStateMachine.fireEvent(order.getOrderStatus(), OrderEventEnum.DELIVER, stateContext);
        // 处理业务逻辑

    }

    @Override
    public void finish(FinishOrderRequest request) {

        // 获取订单状态
        Order order = getOrder(request.getOrderNo());

        // 校验请求

        // 触发状态机
        OrderStateContext stateContext = new OrderStateContext();
        stateContext.setOrderNo(request.getOrderNo());
        stateContext.setChangeTime(new Date());
        OrderStatusEnum targetStatus =
            orderStateMachine.fireEvent(order.getOrderStatus(), OrderEventEnum.FINISH, stateContext);
        // 处理业务逻辑

    }

    @Override
    public void cancel(CancelOrderRequest request) {

        // 获取订单状态
        Order order = getOrder(request.getOrderNo());

        // 校验请求

        // 触发状态机
        OrderStateContext stateContext = new OrderStateContext();
        stateContext.setOrderNo(request.getOrderNo());
        stateContext.setChangeTime(new Date());
        OrderStatusEnum targetStatus =
            orderStateMachine.fireEvent(order.getOrderStatus(), OrderEventEnum.CANCEL, stateContext);
        // 处理业务逻辑

    }

    @Override
    public void refund(RefundOrderRequest request) {

        // 获取订单状态
        Order order = getOrder(request.getOrderNo());

        // 校验请求

        // 触发状态机
        OrderStateContext stateContext = new OrderStateContext();
        stateContext.setOrderNo(request.getOrderNo());
        stateContext.setChangeTime(new Date());
        OrderStatusEnum targetStatus =
            orderStateMachine.fireEvent(order.getOrderStatus(), OrderEventEnum.REFUND, stateContext);
        // 处理业务逻辑

    }
}
