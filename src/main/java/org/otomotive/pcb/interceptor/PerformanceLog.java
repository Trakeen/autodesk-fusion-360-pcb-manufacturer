package org.otomotive.pcb.interceptor;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

/**
 * Logs method performance.
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface PerformanceLog {

    /**
     * Parameters names to log.
     *
     * @return Parameters
     */
    String[] value() default {};
}
