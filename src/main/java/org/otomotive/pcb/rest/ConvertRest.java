package org.otomotive.pcb.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.jaxrs.RestResponseBuilderImpl;
import org.otomotive.pcb.dto.Manufacturer;
import org.otomotive.pcb.interceptor.PerformanceLog;
import org.otomotive.pcb.interceptor.RestResponseException;
import org.otomotive.pcb.service.ConverterService;

import java.io.IOException;
import java.io.OutputStream;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.otomotive.pcb.Constants.*;

/**
 * Convert REST API.
 */
@RestResponseException
@RequestScoped
//@RolesAllowed({ROLE_ADMIN, ROLE_PRO, ROLE_USER})
//@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@Path("/customer")
@Tag(name = "Customer", description = "Customer data.")
public class ConvertRest {

    @Inject
    ConverterService converterService;

    /**
     * Convert Autodesk Fusion 360 PCB FAO archive to target manufacturer archive.
     *
     * @param manufacturer PCB manufacturer
     * @return PCB manufacturer archive
     */
    @PerformanceLog("manufacturer")
    @POST
    @Path("/convert/{manufacturer}")
    @Consumes(APPLICATION_ZIP)
    @Produces(APPLICATION_ZIP)
    @Operation(summary = "Convert Autodesk Fusion 360 PCB FAO archive to target manufacturer archive.")
    @APIResponses(value = {
            @APIResponse(responseCode = SHS_OK, description = "ZIP file.", content = @Content(mediaType = APPLICATION_ZIP, schema = @Schema(implementation = byte[].class))),
            @APIResponse(responseCode = SHS_UNAUTHORIZED, description = "Bad token.", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Error.class))),
            @APIResponse(responseCode = SHS_FORBIDDEN, description = "Forbidden.", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Error.class)))
    })
    public RestResponse<byte[]> convert(
            final Manufacturer manufacturer,
            final @Parameter(description = "Zip file.") @RequestBody(description = "Zip file") byte[] zip
    ) throws IOException {

        final byte[] bytes = converterService.convert(manufacturer, zip);

        return new RestResponseBuilderImpl<byte[]>()
                .entity(bytes)
                .header("Content-Length", bytes.length)
                .header("Content-Disposition", String.format("attachment; filename=%s.zip", manufacturer))
                .build();
    }
}
