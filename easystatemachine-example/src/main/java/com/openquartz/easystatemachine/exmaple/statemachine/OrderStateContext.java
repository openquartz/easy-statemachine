package com.openquartz.easystatemachine.exmaple.statemachine;

import java.util.Date;
import lombok.Data;

@Data
public class OrderStateContext {

    /**
     * 单据号
     */
    private String orderNo;

    /**
     * 变化时间
     */
    private Date changeTime;

}
