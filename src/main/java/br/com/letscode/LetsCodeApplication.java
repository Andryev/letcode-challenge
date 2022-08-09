package br.com.letscode;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/")
@OpenAPIDefinition(
        info = @Info(
                title = "Let's Code API",
                version = "1.0"
        )
)
public class LetsCodeApplication extends Application {
}
