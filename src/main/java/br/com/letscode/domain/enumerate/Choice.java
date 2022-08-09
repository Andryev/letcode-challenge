package br.com.letscode.domain.enumerate;

import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
public enum Choice {
    A, B;

    public static Optional<Choice> getEnum(String choice) {
        return stream()
                .filter(s -> choice != null && s.name().equals(choice.toUpperCase()))
                .findFirst();
    }

    public static Stream<Choice> stream() {
        return Stream.of(Choice.values());
    }
}
