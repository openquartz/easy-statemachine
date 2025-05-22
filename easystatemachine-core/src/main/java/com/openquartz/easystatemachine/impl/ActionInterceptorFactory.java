package com.openquartz.easystatemachine.impl;

import com.openquartz.easystatemachine.ActionInterceptor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * ActionInterceptorFactory
 *
 * @author svnee
 */
public class ActionInterceptorFactory {

    private ActionInterceptorFactory() {
    }

    private final static Set<ActionInterceptor> interceptorSet = new LinkedHashSet<>();

    public static void register(ActionInterceptor interceptor) {
        interceptorSet.add(interceptor);
    }

    public static List<ActionInterceptor> getInterceptorSet() {
        return new ArrayList<>(interceptorSet);
    }


}
