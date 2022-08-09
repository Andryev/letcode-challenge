package br.com.letscode.resource;


import br.com.letscode.domain.entity.User;
import br.com.letscode.domain.service.UserService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Tag(
        name = "User",
        description = "User related operations."
)
@Path("/user")
@Authenticated
public class UserResource {

    @Inject
    UserService userService;

    @RolesAllowed("USER")
    @GET
    @Path("/{id}")
    @Operation(summary = "Get",
            description = "Get the user identified by the provided id"
    )
    public Response get(@Parameter(description = "The ID of the user to retrieve.", required = true) @PathParam("id") Long id) {
        return Optional
                .ofNullable(this.userService.findById(id))
                .map(model -> Response.ok(model.map(user -> user.toBuilder().password(null).build())).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @PermitAll
    @POST
    @Path("/")
    @Operation(summary = "Save",
            description = "Save an user"
    )
    public User save(@Parameter(description = "The user to save.", required = true) User user) {
        return this.userService.save(user);
    }

    @RolesAllowed("USER")
    @PUT
    @Path("/")
    @Operation(summary = "Update",
            description = "Update an user"
    )
    public User update(@Parameter(description = "The entity to update.", required = true) User user) {
        return this.userService.update(user);
    }

    @RolesAllowed("USER")
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete",
            description = "Delete the user identified by the provided id"
    )
    public Response delete(@Parameter(description = "The ID of the user to delete.", required = true) @PathParam("id") Long id) {
        this.userService.delete(id);
        return Response.ok().build();
    }

}
