package org.otomotive.pcb.interceptor;

import jakarta.interceptor.InvocationContext;
import org.otomotive.pcb.service.AbstractService;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;

abstract class AbstractInterceptor extends AbstractService {

    public abstract Object invocation(final InvocationContext invocationContext) throws Exception;

    protected Map<String, MethodParameter> getMethodParameters(final InvocationContext invocationContext) {

        final Map<String, MethodParameter> map = new LinkedHashMap<>();
        final Object[] values = invocationContext.getParameters();
        final Method method = invocationContext.getMethod();
        int i = 0;

        for (final Parameter parameter : method.getParameters()) {

            final Object value = values[i++];
            final MethodParameter methodParameter = MethodParameter.builder()
                                                                   .name(parameter.getName())
                                                                   .parameter(parameter)
                                                                   .value(value)
                                                                   .build();

            map.put(parameter.getName(), methodParameter);
        }

        return map;
    }
}