package br.com.letscode.domain.repository;

import br.com.letscode.domain.entity.Game;
import br.com.letscode.domain.entity.User;
import br.com.letscode.domain.enumerate.StatusGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByPlayerAndStatus(User player, StatusGame status);

    @Query("select new br.com.letscode.domain.entity.Game(g.player.id, g.player.username, sum(g.mistakes), sum(g.wins)) from Game g group by g.player, g.player.id, g.player.username, g.wins order by g.wins DESC")
    List<Game> rankingList();

}
