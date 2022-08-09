package br.com.letscode.common.exception;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private String message;
    private Boolean status;

}
