package HackatonWaki.mappers;

import HackatonWaki.dtos.UserDTO.DatosAutenticacionUsuario;
import HackatonWaki.models.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-08T21:30:49-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(DatosAutenticacionUsuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( usuario.email() );
        user.setPassword( usuario.password() );

        return user;
    }

    @Override
    public DatosAutenticacionUsuario toDTO(User usuario) {
        if ( usuario == null ) {
            return null;
        }

        String email = null;
        String password = null;

        email = usuario.getEmail();
        password = usuario.getPassword();

        DatosAutenticacionUsuario datosAutenticacionUsuario = new DatosAutenticacionUsuario( email, password );

        return datosAutenticacionUsuario;
    }
}
