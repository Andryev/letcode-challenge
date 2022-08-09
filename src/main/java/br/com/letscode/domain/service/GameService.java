package br.com.letscode.domain.service;

import br.com.letscode.common.exception.CustomException;
import br.com.letscode.domain.entity.Game;
import br.com.letscode.domain.entity.User;
import br.com.letscode.domain.enumerate.StatusGame;
import br.com.letscode.domain.repository.GameRepository;
import br.com.letscode.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
@Traced
@Slf4j
public class GameService {

    public static final String TO_CLOSE = "There is no game in progress to close.";
    public static final String TO_START = "It is not possible to start a new game, as there is already one in progress for that user.";

    @Inject
    GameRepository gameRepository;

    @Inject
    UserRepository userRepository;


    private Optional<Game> checkIfExistsGame(User user) {
        return this.gameRepository.findByPlayerAndStatus(user, StatusGame.INITIATED);
    }

    public Optional<Game> findById(Long id) {
        return this.gameRepository.findById(id);
    }

    public Game start(SecurityContext securityContext) {
        User user = this.userRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Optional<Game> game = this.checkIfExistsGame(user);
        if (!game.isPresent()) {
            Game entity = Game.builder().player(user).status(StatusGame.INITIATED).mistakes(0).wins(0).build();
            entity = this.gameRepository.save(entity);
            entity = entity.toBuilder().player(User.builder().id(user.getId()).username(user.getUsername()).build()).build();
            return entity;
        } else {
            throw new CustomException(TO_START);
        }
    }

    public Game stop(SecurityContext securityContext) {
        User user = this.userRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Optional<Game> game = this.checkIfExistsGame(user);
        if (game.isPresent()) {
            Game entity = this.gameRepository.save(game.get().toBuilder().status(StatusGame.FINISHED).build());
            entity = entity.toBuilder().player(User.builder().id(user.getId()).username(user.getUsername()).build()).build();
            return entity;
        } else {
            throw new CustomException(TO_CLOSE);
        }
    }

    public List<Game> rankingList() {
        return this.gameRepository.rankingList();
    }

}
