package HackatonWaki.mappers;

import HackatonWaki.dtos.ClienteDTO.ClienteRequestDTO;
import HackatonWaki.dtos.ClienteDTO.ClienteResponseDTO;
import HackatonWaki.dtos.ClienteDTO.ClienteUpdateDTO;
import HackatonWaki.models.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    Cliente toEntity(ClienteRequestDTO clienteRequestDTO);

    ClienteResponseDTO toResponseDTO(Cliente cliente);

    Cliente toEntity(ClienteUpdateDTO clienteUpdateDTO);
}
