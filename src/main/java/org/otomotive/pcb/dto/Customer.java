package org.otomotive.pcb.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Customer.
 */
@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class Customer {

    /**
     * Current UPN.
     */
    private UUID upn;
}
