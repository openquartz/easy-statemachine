package com.openquartz.easystatemachine.exmaple.service;

import com.openquartz.easystatemachine.exmaple.model.BasicUser;
import com.openquartz.easystatemachine.exmaple.request.CancelOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.CreateOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.DeliverOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.FinishOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.PayOrderRequest;
import com.openquartz.easystatemachine.exmaple.request.RefundOrderRequest;

/**
 * OrderService
 * @author svnee
 */
public interface OrderService {

    /**
     * 创建订单
     * @param user user
     * @return orderNo
     */
    String create(BasicUser user, CreateOrderRequest request);

    /**
     * 支付
     * @param request request
     */
    void pay(PayOrderRequest request);

    /**
     * 发货
     * @param request req
     */
    void deliver(DeliverOrderRequest request);

    /**
     * 完成
     * @param request req
     */
    void finish(FinishOrderRequest request);

    /**
     * 取消
     * @param request req
     */
    void cancel(CancelOrderRequest request);

    /**
     * 退款
     * @param request req
     */
    void refund(RefundOrderRequest request);
}
