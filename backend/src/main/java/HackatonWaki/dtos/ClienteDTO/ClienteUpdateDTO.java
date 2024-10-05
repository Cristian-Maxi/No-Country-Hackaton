package HackatonWaki.dtos.ClienteDTO;

import jakarta.validation.constraints.NotNull;

public record ClienteUpdateDTO(
        @NotNull
        Long id,
        String nombre,
        String apellido,
        int edad
) {
}
