package org.otomotive.pcb.interceptor;

import jakarta.interceptor.InterceptorBinding;
import org.jboss.resteasy.reactive.RestResponse;

import java.lang.annotation.*;

/**
 * Intercept {@link Throwable} and return a {@link RestResponse}.
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RestResponseException {

}
