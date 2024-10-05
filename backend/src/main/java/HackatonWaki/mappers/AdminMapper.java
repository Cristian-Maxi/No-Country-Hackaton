package HackatonWaki.mappers;

import HackatonWaki.dtos.AdminDTO.AdminRequestDTO;
import HackatonWaki.dtos.AdminDTO.AdminResponseDTO;
import HackatonWaki.dtos.AdminDTO.AdminUpdateDTO;
import HackatonWaki.models.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AdminMapper {

    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    @Mapping(source = "usuario", target = "usuario")
    Admin toEntity(AdminRequestDTO adminRequestDTO);

    @Mapping(source = "id", target = "id")
    AdminResponseDTO toResponseDTO(Admin admin);

    Admin toEntity(AdminUpdateDTO adminUpdateDTO);
}
