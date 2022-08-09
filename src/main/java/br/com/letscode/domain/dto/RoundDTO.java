package br.com.letscode.domain.dto;

import br.com.letscode.domain.enumerate.Choice;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoundDTO {

    public String movieA;
    public Long resultA;
    public Long resultB;
    public String movieB;
    public String msg;
    public Choice yourChoice;
    public Choice correctChoice;


}
