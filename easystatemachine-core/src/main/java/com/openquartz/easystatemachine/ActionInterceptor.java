package com.openquartz.easystatemachine;

import com.openquartz.easystatemachine.annotation.Nullable;

/**
 * ActionInterceptor
 *
 * @author svnee
 */
public interface ActionInterceptor {

    /**
     * 拦截器顺序.顺序小的优先执行
     */
    int order();

    /**
     * 前置执行
     *
     * @param source source 源状态
     * @param target target 目标状态
     * @param event event 事件类型
     * @param context context 执行上下文
     */
    void beforeIntercept(@Nullable Object source, Object target, Object event, Object context);

    /**
     * 完成后执行
     *
     * @param source source 源状态
     * @param target target 目标状态
     * @param event event 事件类型
     * @param context context 执行上下文
     */
    void afterComplete(@Nullable Object source, Object target, Object event, Object context, @Nullable Throwable ex);

}
