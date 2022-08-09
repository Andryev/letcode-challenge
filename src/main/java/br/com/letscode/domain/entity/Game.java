package br.com.letscode.domain.entity;

import br.com.letscode.domain.enumerate.StatusGame;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = "id")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = Game.TABLE_NAME)
public class Game {

    public static final String TABLE_NAME = "GAME";
    public static final String SEQUENCE_NAME = "GAME_SEQ";

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = Game.SEQUENCE_NAME, sequenceName = Game.SEQUENCE_NAME, allocationSize = 1)
    @GeneratedValue(generator = Game.SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USER", nullable = false)
    private User player;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StatusGame status;

    @Column(name = "MISTAKES")
    public Integer mistakes;

    @Column(name = "WINS")
    public Integer wins;

    @Transient
    public String username;

    @Transient
    public Long idUser;

    public Game(Long idUser, String username, Long mistakes, Long wins) {
        this.idUser = idUser;
        this.username = username;
        this.mistakes = mistakes.intValue();
        this.wins = wins.intValue();
    }
}
