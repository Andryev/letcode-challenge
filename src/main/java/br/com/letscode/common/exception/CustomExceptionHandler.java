package br.com.letscode.common.exception;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomExceptionHandler
        implements ExceptionMapper<CustomException> {

    @Override
    public Response toResponse(CustomException e) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(new ErrorMessage(e.getMessage(), false))
                    .build();
    }
}