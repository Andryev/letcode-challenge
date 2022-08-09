package br.com.letscode.client;

import br.com.letscode.domain.dto.MovieDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/Top250Movies")
@RegisterRestClient(configKey = "imdb-api")
public interface MovieClient {

    @GET()
    @Path("/{apiKey}")
    MovieDTO get(@PathParam String apiKey);

}
