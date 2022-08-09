package br.com.letscode.domain.service;

import br.com.letscode.common.exception.CustomException;
import br.com.letscode.domain.dto.RoundDTO;
import br.com.letscode.domain.entity.*;
import br.com.letscode.domain.enumerate.Choice;
import br.com.letscode.domain.enumerate.StatusGame;
import br.com.letscode.domain.enumerate.StatusRound;
import br.com.letscode.domain.repository.GameRepository;
import br.com.letscode.domain.repository.MovieRepository;
import br.com.letscode.domain.repository.RoundRepository;
import br.com.letscode.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
@Transactional
@Traced
@Slf4j
public class RoundService {

    public static final String NO_ACTIVE_GAMES_TO_START_A_ROUND = "There are no active games to start a round.";
    public static final String A_OR_B_ARE_ACCEPTED = "Invalid option, only A or B are accepted";
    public static final String PROGRESS_TO_RECEIVE_RESPONSES = "There is no round in progress to receive responses";
    public static final String IT_RIGHT = "Congratulations, you got it right.";
    public static final String TRY_AGAIN = "Unfortunately you made a mistake, please try again.";
    public static final String GAME_OVER = "You missed, and hit 3 errors, game over.";

    @Inject
    GameRepository gameRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    MovieRepository movieRepository;

    @Inject
    RoundRepository roundRepository;


    private Optional<Game> checkIfExistsGame(User user) {
        return this.gameRepository.findByPlayerAndStatus(user, StatusGame.INITIATED);
    }

    private Movie getRandomElement(List<Movie> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    private Optional<Round> getCurrentRound(User user) {
        List<Round> round = this.roundRepository.findAllByStatus(StatusRound.DRAW);
        return round.stream().filter(model -> model.getId().getIdUser().getId().equals(user.getId())).findFirst();
    }

    public RoundDTO get(SecurityContext securityContext) {
        User user = this.userRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Optional<Game> game = this.checkIfExistsGame(user);

        if (!game.isPresent()) {
            throw new CustomException(NO_ACTIVE_GAMES_TO_START_A_ROUND);
        }

        Iterable<Movie> list = this.movieRepository.findAll();
        RoundID roundID = new RoundID();
        roundID.setIdUser(user);
        RoundDTO roundDTO = new RoundDTO();

        do {
            Movie movieA = this.getRandomElement((List<Movie>) list);
            Movie movieB = this.getRandomElement((List<Movie>) list);
            roundID.setIdMovieA(movieA);
            roundID.setIdMovieB(movieB);
            roundDTO.setMovieA(movieA.getFullTitle());
            roundDTO.setMovieB(movieB.getFullTitle());
        } while (this.roundRepository.findById(roundID).isPresent());

        Round round = Round.builder().id(roundID).status(StatusRound.DRAW).game(game.get()).build();
        this.roundRepository.save(round);

        return roundDTO;
    }

    public RoundDTO send(String choice, SecurityContext securityContext) {
        RoundDTO roundDTO = new RoundDTO();
        Optional<Choice> choiceUser = Choice.getEnum(choice);

        if (!choiceUser.isPresent()) {
            throw new CustomException(A_OR_B_ARE_ACCEPTED);
        }

        User user = this.userRepository.findByUsername(securityContext.getUserPrincipal().getName());
        Optional<Round> currentRound = this.getCurrentRound(user);

        if (!currentRound.isPresent()) {
            throw new CustomException(PROGRESS_TO_RECEIVE_RESPONSES);
        }

        Round round = currentRound.get();
        Long movieA = Math.round(round.getId().getIdMovieA().getImDbRatingCount() * round.getId().getIdMovieA().getImDbRating());
        Long movieB = Math.round(round.getId().getIdMovieB().getImDbRatingCount() * round.getId().getIdMovieB().getImDbRating());
        Choice choiceWin = movieA > movieB ? Choice.A : Choice.B;
        round.toBuilder().choiceWin(choiceWin).choice(choiceUser.get()).build();
        Game game = round.getGame();

        roundDTO.setCorrectChoice(choiceWin);
        roundDTO.setYourChoice(choiceUser.get());
        roundDTO.setMovieA(round.getId().getIdMovieA().getFullTitle());
        roundDTO.setMovieB(round.getId().getIdMovieB().getFullTitle());
        roundDTO.setResultA(movieA);
        roundDTO.setResultB(movieB);

        if (choiceUser.get().equals(choiceWin)) {
            game = game.toBuilder().wins(game.getWins() + 1).build();
            round = round.toBuilder().status(StatusRound.WIN).build();
            roundDTO.setMsg(IT_RIGHT);
        } else {
            game = game.toBuilder().mistakes(game.getMistakes() + 1).build();
            round = round.toBuilder().status(StatusRound.LOST).build();
            roundDTO.setMsg(TRY_AGAIN);
        }

        if (game.getMistakes() >= 3) {
            game = game.toBuilder().status(StatusGame.FINISHED).build();
            roundDTO.setMsg(GAME_OVER);
        }

        this.roundRepository.save(round);
        this.gameRepository.save(game);

        return roundDTO;
    }

}
