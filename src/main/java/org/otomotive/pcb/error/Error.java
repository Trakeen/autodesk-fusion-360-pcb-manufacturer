package org.otomotive.pcb.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

/**
 * Error for JSON.
 */
@AllArgsConstructor
@Builder
@Getter
@Setter
@RegisterForReflection
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    /**
     * HTTP status code.
     */
    private int statusCode;

    /**
     * HTTP status message.
     */
    private String statusMessage;

    /**
     * Timestamp of the error.
     */
    private OffsetDateTime timestamp;
}
