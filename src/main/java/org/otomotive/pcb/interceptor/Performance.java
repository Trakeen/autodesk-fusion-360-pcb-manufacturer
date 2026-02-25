package org.otomotive.pcb.interceptor;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

/**
 * Logs method performance for a class.
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Performance {

}
