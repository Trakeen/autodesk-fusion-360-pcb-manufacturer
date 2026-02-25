package org.otomotive.pcb.interceptor;

import io.quarkus.hibernate.validator.runtime.jaxrs.ResteasyReactiveViolationException;
import jakarta.annotation.Priority;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;
import org.otomotive.pcb.error.Error;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Intercept {@link Throwable} and return a {@link RestResponse}.
 *
 * @author David CABILLIC
 */
@RestResponseException
@Priority(101)
@Interceptor
public class RestResponseInterceptor extends AbstractPerformanceInterceptor {

    private static final String COMMA = ", ";

    @Override
    protected Object invocationInternal(final InvocationContext invocationContext) throws Exception {

        try {

            return invocationContext.proceed();
        }
        catch (final WebApplicationException e) {

            final Response response = e.getResponse();
            final Response.Status status = Response.Status.fromStatusCode(response.getStatus());
            final Object entity = response.getEntity();
            String statusMessage = status.getReasonPhrase();

            if (entity instanceof InputStream inputStream) {

                final String payload = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                if (!payload.isBlank()) {

                    statusMessage = payload;
                }
            }

            final Error error = Error.builder()
                                     .statusCode(status.getStatusCode())
                                     .statusMessage(statusMessage)
                                     .timestamp(OffsetDateTime.now())
                                     .build();

            return getRestResponse(invocationContext, status, error, e);
        }
        catch (final ResteasyReactiveViolationException e) {

            final Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
            final String statusMessage = constraintViolations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(COMMA));
            final Error error = Error.builder()
                                     .timestamp(OffsetDateTime.now())
                                     .statusCode(BAD_REQUEST.getStatusCode())
                                     .statusMessage(statusMessage)
                                     .build();
            final Response.Status status = Response.Status.fromStatusCode(error.getStatusCode());

            return getRestResponse(invocationContext, status, error, e);
        }
        catch (final Throwable e) {

            final Error error = Error.builder()
                                     .timestamp(OffsetDateTime.now())
                                     .statusCode(BAD_REQUEST.getStatusCode())
                                     .statusMessage(e.getMessage())
                                     .build();

            return getRestResponse(invocationContext, BAD_REQUEST, error, e);
        }
    }

    private RestResponse<Error> getRestResponse(
            final InvocationContext invocationContext,
            final Response.Status status,
            final Error error,
            final Throwable e
    ) {
        final Method method = invocationContext.getMethod();
        final Logger log = Logger.getLogger(method.getDeclaringClass());

        log.error(e.getMessage(), e);

        return RestResponse.ResponseBuilder
                .create(status, error)
                .build();
    }
}
