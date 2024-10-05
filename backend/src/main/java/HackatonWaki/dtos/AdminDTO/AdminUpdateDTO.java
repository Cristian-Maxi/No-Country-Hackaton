package HackatonWaki.dtos.AdminDTO;

import jakarta.validation.constraints.NotNull;

public record AdminUpdateDTO(
        @NotNull
        Long id,
        String nombre,
        String apellido
) {
}
