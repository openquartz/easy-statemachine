package com.openquartz.easystatemachine.exmaple.model;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    UNPAID(1, "未支付"),
    PAID(2, "已支付"),
    DELIVERED(3, "已发货"),
    FINISHED(4, "已完成"),
    CANCELED(5, "已取消"),
    REFUNDED(6, "已退款"),
    ;

    private final int code;

    private final String desc;

    OrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
