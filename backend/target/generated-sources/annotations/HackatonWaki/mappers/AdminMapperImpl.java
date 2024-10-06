package HackatonWaki.mappers;

import HackatonWaki.dtos.AdminDTO.AdminRequestDTO;
import HackatonWaki.dtos.AdminDTO.AdminResponseDTO;
import HackatonWaki.dtos.AdminDTO.AdminUpdateDTO;
import HackatonWaki.models.Admin;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-06T05:23:16-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class AdminMapperImpl implements AdminMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Admin toEntity(AdminRequestDTO adminRequestDTO) {
        if ( adminRequestDTO == null ) {
            return null;
        }

        Admin admin = new Admin();

        admin.setUsuario( userMapper.toEntity( adminRequestDTO.usuario() ) );
        admin.setNombre( adminRequestDTO.nombre() );
        admin.setApellido( adminRequestDTO.apellido() );

        return admin;
    }

    @Override
    public AdminResponseDTO toResponseDTO(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        Long id = null;
        String nombre = null;
        String apellido = null;

        id = admin.getId();
        nombre = admin.getNombre();
        apellido = admin.getApellido();

        AdminResponseDTO adminResponseDTO = new AdminResponseDTO( id, nombre, apellido );

        return adminResponseDTO;
    }

    @Override
    public Admin toEntity(AdminUpdateDTO adminUpdateDTO) {
        if ( adminUpdateDTO == null ) {
            return null;
        }

        Admin admin = new Admin();

        admin.setId( adminUpdateDTO.id() );
        admin.setNombre( adminUpdateDTO.nombre() );
        admin.setApellido( adminUpdateDTO.apellido() );

        return admin;
    }
}
