package br.com.letscode.domain.service;

import br.com.letscode.client.MovieClient;
import br.com.letscode.domain.dto.MovieDTO;
import br.com.letscode.domain.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
@Traced
@Slf4j
public class MovieService {

    @ConfigProperty(name = "br.com.letscode.imdb.api.key")
    private String apiKey;

    @Inject
    @RestClient
    MovieClient movieClient;

    @Inject
    MovieRepository movieRepository;

    public void loadMovie() {
        MovieDTO movieDTO = this.movieClient.get(apiKey);
        movieDTO.getItems().forEach(item -> {
                movieRepository.save(item);
                log.info("Insert Movie...{}", item.getId());
        });
    }
}
