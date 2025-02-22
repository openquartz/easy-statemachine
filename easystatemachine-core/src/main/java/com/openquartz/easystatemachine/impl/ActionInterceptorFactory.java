package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.ActionInterceptor;
import java.util.ArrayList;
import java.util.List;

/**
 * ActionInterceptorFactory
 *
 * @author svnee
 */
public class ActionInterceptorFactory {

    private ActionInterceptorFactory() {
    }

    private final static List<ActionInterceptor> interceptorList = new ArrayList<>();

    public static void register(ActionInterceptor interceptor) {
        interceptorList.add(interceptor);
    }

    public static List<ActionInterceptor> getInterceptorList() {
        return interceptorList;
    }


}
