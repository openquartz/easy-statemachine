package com.openquartz.easystatemachine.exmaple.statemachine;

public enum OrderEventEnum {

    /**
     * 创单
     */
    CREATE
    ,

    /**
     * 支付
     */
    PAY,

    /**
     * 发货
     */
    DELIVER,

    /**
     * 完成
     */
    FINISH,

    /**
     * 取消
     */
    CANCEL,

    /**
     * 退款
     */
    REFUND;

}
