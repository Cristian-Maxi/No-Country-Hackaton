package HackatonWaki.mappers;

import HackatonWaki.dtos.ClienteDTO.ClienteRequestDTO;
import HackatonWaki.dtos.ClienteDTO.ClienteResponseDTO;
import HackatonWaki.dtos.ClienteDTO.ClienteUpdateDTO;
import HackatonWaki.dtos.UserDTO.DatosAutenticacionUsuario;
import HackatonWaki.models.Cliente;
import HackatonWaki.models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-06T06:14:26-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class ClienteMapperImpl implements ClienteMapper {

    @Override
    public Cliente toEntity(ClienteRequestDTO clienteRequestDTO) {
        if ( clienteRequestDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setNombre( clienteRequestDTO.nombre() );
        cliente.setApellido( clienteRequestDTO.apellido() );
        cliente.setEdad( clienteRequestDTO.edad() );
        cliente.setUsuario( datosAutenticacionUsuarioToUser( clienteRequestDTO.usuario() ) );

        return cliente;
    }

    @Override
    public ClienteResponseDTO toResponseDTO(Cliente cliente) {
        if ( cliente == null ) {
            return null;
        }

        Long id = null;
        String nombre = null;
        String apellido = null;
        int edad = 0;

        id = cliente.getId();
        nombre = cliente.getNombre();
        apellido = cliente.getApellido();
        edad = cliente.getEdad();

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO( id, nombre, apellido, edad );

        return clienteResponseDTO;
    }

    @Override
    public Cliente toEntity(ClienteUpdateDTO clienteUpdateDTO) {
        if ( clienteUpdateDTO == null ) {
            return null;
        }

        Cliente cliente = new Cliente();

        cliente.setId( clienteUpdateDTO.id() );
        cliente.setNombre( clienteUpdateDTO.nombre() );
        cliente.setApellido( clienteUpdateDTO.apellido() );
        cliente.setEdad( clienteUpdateDTO.edad() );

        return cliente;
    }

    protected User datosAutenticacionUsuarioToUser(DatosAutenticacionUsuario datosAutenticacionUsuario) {
        if ( datosAutenticacionUsuario == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( datosAutenticacionUsuario.email() );
        user.setPassword( datosAutenticacionUsuario.password() );

        return user;
    }
}
