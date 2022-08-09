package br.com.letscode.domain.repository;

import br.com.letscode.domain.entity.Round;
import br.com.letscode.domain.entity.RoundID;
import br.com.letscode.domain.enumerate.StatusRound;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoundRepository extends CrudRepository<Round, RoundID> {

    List<Round> findAllByStatus(StatusRound statusRound);

}
