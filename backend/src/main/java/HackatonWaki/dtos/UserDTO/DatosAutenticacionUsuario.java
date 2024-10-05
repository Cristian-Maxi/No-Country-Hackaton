package HackatonWaki.dtos.UserDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosAutenticacionUsuario(
        @NotBlank(message = "El email es obligatorio")
        String email,
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password
) {
}
