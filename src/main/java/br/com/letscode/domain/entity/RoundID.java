package br.com.letscode.domain.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Embeddable
public class RoundID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "ID_USER", nullable = false)
    private User idUser;

    @ManyToOne
    @JoinColumn(name = "ID_MOVIE_A", nullable = false)
    private Movie idMovieA;

    @ManyToOne
    @JoinColumn(name = "ID_MOVIE_B", nullable = false)
    private Movie idMovieB;

}
