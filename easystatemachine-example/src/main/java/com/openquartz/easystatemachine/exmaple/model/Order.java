package com.openquartz.easystatemachine.exmaple.model;

import java.util.Date;
import lombok.Data;

/**
 * 单据
 * @author svnee
 */
@Data
public class Order {

    /**
     * 单号
     */
    private String orderNo;

    /**
     * 单据状态
     */
    private OrderStatusEnum orderStatus;

    /**
     * 下单人
     */
    private String orderBy;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 备注
     */
    private String remark;
}
