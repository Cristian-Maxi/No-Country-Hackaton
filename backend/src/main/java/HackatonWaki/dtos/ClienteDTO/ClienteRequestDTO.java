package HackatonWaki.dtos.ClienteDTO;

import HackatonWaki.dtos.UserDTO.DatosAutenticacionUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ClienteRequestDTO(
        @NotNull
        String nombre,
        @NotNull
        String apellido,
        @NotNull
        int edad,
        @Valid @NotNull
        DatosAutenticacionUsuario usuario
) {
}
