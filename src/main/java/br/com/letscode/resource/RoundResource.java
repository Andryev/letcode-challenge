package br.com.letscode.resource;


import br.com.letscode.domain.service.RoundService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Optional;

@Tag(
        name = "Round",
        description = "Round related operations."
)
@Path("/round")
@Authenticated
public class RoundResource {

    @Inject
    RoundService roundService;


    @RolesAllowed("USER")
    @GET
    @Path("/")
    @Operation(summary = "Get",
            description = "Get the round by the provided game."
    )
    public Response get(@Parameter(description = "The rount to start.") @Context SecurityContext securityContext) {
        return Optional
                .ofNullable(this.roundService.get(securityContext))
                .map(model -> Response.ok(model).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @RolesAllowed("USER")
    @POST
    @Path("/{choice}")
    @Operation(summary = "Choice",
            description = "Send game movie choice, A or B only."
    )
    public Response send(@Parameter(description = "The Choice of the round.", required = true)
                         @PathParam("choice") String choice,
                         @Context SecurityContext securityContext) {
        return Optional
                .ofNullable(this.roundService.send(choice, securityContext))
                .map(model -> Response.ok(model).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }


}
