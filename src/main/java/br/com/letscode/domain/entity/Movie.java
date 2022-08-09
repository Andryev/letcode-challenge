package br.com.letscode.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = "id")
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = Movie.TABLE_NAME)
public class Movie {

    public static final String TABLE_NAME = "MOVIE";

    @Id
    @Column(name = "ID")
    public String id;

    @Column(name = "RANK")
    public Integer rank;

    @Column(name = "TITLE")
    public String title;

    @Column(name = "FULL_TITLE")
    public String fullTitle;

    @Column(name = "YEAR")
    public Integer year;

    @Column(name = "IMAGE")
    public String image;

    @Column(name = "CREW")
    public String crew;

    @Column(name = "IMDB_RATING")
    public Double imDbRating;

    @Column(name = "IMDB_RATING_COUNT")
    public Long imDbRatingCount;

}
