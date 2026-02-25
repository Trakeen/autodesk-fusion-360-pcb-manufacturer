package org.otomotive.pcb.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import org.jboss.logging.Logger;
import org.jboss.logmanager.MDC;
import org.otomotive.pcb.dto.Customer;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.otomotive.pcb.Constants.N_A;
import static org.otomotive.pcb.Constants.PERF_START;

abstract class AbstractPerformanceInterceptor extends AbstractInterceptor {

    /**
     * Log format.
     */
    private static final String LOG_FORMAT = "ms={0,number,#} customer={1} params={2}";

    @AroundInvoke
    @Override
    public Object invocation(final InvocationContext invocationContext) throws Exception {

        final Method method = invocationContext.getMethod();
        final PerformanceLog conf = method.getAnnotation(PerformanceLog.class);

        if (conf == null) {

            return invocationInternal(invocationContext);
        }

        final long start = System.currentTimeMillis();
        final String[] paramNames = conf.value();
        final Map<String, MethodParameter> methodParameters = getMethodParameters(invocationContext);
        final String name = method.getName();
        final Logger log = Logger.getLogger(method.getDeclaringClass(), name);
        final Map<String, Object> mapParams = new LinkedHashMap<>();
        final Optional<Customer> optCustomer = getCustomerNoError();
        final String customer = optCustomer.map(c -> c.getUpn().toString()).orElse(N_A);

        MDC.putObject(PERF_START, start);

        for (final String paramName : paramNames) {

            final MethodParameter parameter = methodParameters.get(paramName);

            if (parameter == null) {

                log.warnf("Parameter %s not found in method %s", paramName, name);
                continue;
            }

            mapParams.put(paramName, parameter.getValue());
        }

        log.infov(LOG_FORMAT, 0L, customer, mapParams);

        final Object result = invocationInternal(invocationContext);
        final long elapsed = System.currentTimeMillis() - start;

        MDC.putObject("executionTime", elapsed);
        MDC.putObject("params", mapParams);
        log.infov(LOG_FORMAT, elapsed, customer, mapParams);
        MDC.remove("executionTime");
        MDC.remove("params");

        return result;
    }

    protected abstract Object invocationInternal(final InvocationContext invocationContext) throws Exception;
}
