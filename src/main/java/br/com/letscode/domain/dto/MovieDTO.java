package br.com.letscode.domain.dto;

import br.com.letscode.domain.entity.Movie;
import lombok.*;

import java.util.ArrayList;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieDTO {

    public ArrayList<Movie> items;
    public String errorMessage;
}
