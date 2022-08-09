package br.com.letscode.resource;


import br.com.letscode.domain.entity.Game;
import br.com.letscode.domain.service.GameService;
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
import java.util.List;
import java.util.Optional;

@Tag(
        name = "Game",
        description = "Game related operations."
)
@Path("/game")
@Authenticated
public class GameResource {

    @Inject
    GameService gameService;


    @RolesAllowed("USER")
    @GET
    @Path("/{id}")
    @Operation(summary = "Get",
            description = "Get the game identified by the provided id"
    )
    public Response get(@Parameter(description = "The ID of the game to retrieve.", required = true) @PathParam("id") Long id) {
        return Optional
                .ofNullable(this.gameService.findById(id))
                .map(model -> Response.ok(model).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @RolesAllowed("USER")
    @POST
    @Path("/")
    @Operation(summary = "Start",
            description = "Start an game"
    )
    public Game start(@Parameter(description = "The game to start.") @Context SecurityContext securityContext) {
        return this.gameService.start(securityContext);
    }

    @RolesAllowed("USER")
    @PUT
    @Path("/")
    @Operation(summary = "Stop",
            description = "Stop an game"
    )
    public Game update(@Parameter(description = "The game to stop.") @Context SecurityContext securityContext) {
        return this.gameService.stop(securityContext);
    }

    @RolesAllowed("USER")
    @GET
    @Path("/ranking")
    @Operation(summary = "Ranking",
            description = "List an game"
    )
    public List<Game> rankingList() {
        return this.gameService.rankingList();
    }


}
