package HackatonWaki.dtos.ClienteDTO;

public record ClienteResponseDTO(
        Long id,
        String nombre,
        String apellido,
        int edad
) {
}
