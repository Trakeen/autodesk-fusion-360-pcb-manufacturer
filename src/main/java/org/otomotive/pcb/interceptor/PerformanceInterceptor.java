package org.otomotive.pcb.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

/**
 * Checks allowed roles or scopes.
 *
 * @author David CABILLIC
 */
@Performance
@Priority(103)
@Interceptor
public class PerformanceInterceptor extends AbstractPerformanceInterceptor {

    @Override
    protected Object invocationInternal(final InvocationContext invocationContext) throws Exception {

        return invocationContext.proceed();
    }
}
