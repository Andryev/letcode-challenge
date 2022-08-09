package br.com.letscode.domain.entity;

import br.com.letscode.domain.enumerate.Choice;
import br.com.letscode.domain.enumerate.StatusRound;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = "id")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = Round.TABLE_NAME)
public class Round {

    public static final String TABLE_NAME = "ROUND";

    @EmbeddedId
    private RoundID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusRound status;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHOICE")
    private Choice choice;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHOICE_WIN")
    private Choice choiceWin;

    @ManyToOne
    @JoinColumn(name = "GAME_ID", nullable = false)
    private Game game;

}
