package br.com.letscode.domain.repository;

import br.com.letscode.domain.entity.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, String> {

}
