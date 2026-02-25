package org.otomotive.pcb.service;

import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.otomotive.pcb.dto.Customer;

import java.util.Optional;
import java.util.UUID;

import static org.otomotive.pcb.Constants.F_UPN;

/**
 * Abstract service class that provides common functionality for services.
 */
public class AbstractService {

    @Inject
    JsonWebToken jwt;

    /**
     * Get the customer associated with the current JWT.
     *
     * @return The {@link Customer} associated with the JWT.
     */
    protected Customer getCustomer() {

        if (jwt == null) {

            throw new IllegalStateException("JWT is not injected.");
        }

        return getCustomerNoError()
                .orElseThrow(() -> new IllegalStateException("No customer found for JWT: " + jwt.getName()));
    }

    /**
     * Get the customer associated with the current JWT.
     *
     * @return The {@link Customer} associated with the JWT.
     */
    protected Optional<Customer> getCustomerNoError() {

        if (jwt == null) {

            return Optional.empty();
        }

        final String upnStr = jwt.getClaim(F_UPN);

        if (upnStr == null || upnStr.isBlank()) {

            return Optional.empty();
        }

        final UUID upn = UUID.fromString(upnStr);

        return Optional.empty(); // TODO
    }
}
