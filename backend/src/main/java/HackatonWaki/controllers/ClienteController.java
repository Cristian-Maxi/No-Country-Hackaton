package HackatonWaki.controllers;

import HackatonWaki.dtos.ApiResponseDTO;
import HackatonWaki.dtos.ClienteDTO.ClienteRequestDTO;
import HackatonWaki.dtos.ClienteDTO.ClienteResponseDTO;
import HackatonWaki.dtos.ClienteDTO.ClienteUpdateDTO;
import HackatonWaki.exceptions.AplicationExceptions;
import HackatonWaki.mappers.ClienteMapper;
import HackatonWaki.models.Cliente;
import HackatonWaki.services.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    private final IClienteService clienteService;
    private final ClienteMapper clienteMapper;

    public ClienteController(IClienteService userService, ClienteMapper clienteMapper) {
        this.clienteService = userService;
        this.clienteMapper = clienteMapper;
    }

    @GetMapping("/getAll")
    @Operation(summary = "Obtiene todos los estudiantes")
    public ResponseEntity<ApiResponseDTO<ClienteResponseDTO>> findAll() {
        try {
            List<Cliente> clientes = clienteService.findAll();
            List<ClienteResponseDTO> clientesDTO = clientes.stream()
                    .map(clienteMapper::toResponseDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new ApiResponseDTO<>(true, "Exito", clientesDTO), HttpStatus.CREATED);
        } catch (AplicationExceptions e) {
            throw new AplicationExceptions(" Ha ocurrido un error " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un estudiante en particular")
    public ResponseEntity<ApiResponseDTO<ClienteResponseDTO>> findById(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        if (cliente.isPresent()) {
            ClienteResponseDTO clienteResponseDTO = clienteMapper.toResponseDTO(cliente.get());
            String message = "Estudiante encontrado";
            return new ResponseEntity<>(new ApiResponseDTO<>(true, message, clienteResponseDTO), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, "Cliente no encontrado", null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Se agrega un cliente")
    public ResponseEntity<ApiResponseDTO<ClienteResponseDTO>> save(@RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteRequestDTO);
        clienteService.save(cliente);
        ClienteResponseDTO clienteResponseDTO = clienteMapper.toResponseDTO(cliente);
        String message = "Cliente Registrado";
        return new ResponseEntity<>(new ApiResponseDTO<>(true, message, clienteResponseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Operation(summary = "Se actualiza un cliente en particular")
    public ResponseEntity<ApiResponseDTO<ClienteResponseDTO>> update(@RequestBody @Valid ClienteUpdateDTO clienteUpdateDTO) {
        Optional<Cliente> buscarCliente = clienteService.findById(clienteUpdateDTO.id());
        if (buscarCliente.isPresent()) {
            Cliente cliente = buscarCliente.get();
            cliente.setNombre(clienteUpdateDTO.nombre());
            cliente.setApellido(clienteUpdateDTO.apellido());
            cliente.setEdad(clienteUpdateDTO.edad());
            Long usuarioId = cliente.getUsuario().getId();
            cliente.getUsuario().setId(usuarioId);
            clienteService.update(cliente);
            ClienteResponseDTO clienteResponseDTO = clienteMapper.toResponseDTO(cliente);
            String message = "Cliente actualizado con éxito";
            return new ResponseEntity<>(new ApiResponseDTO<>(true, message, clienteResponseDTO), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, "Cliente no encontrado", null), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Se elimina un cliente en particular")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        clienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Ruta para este pageable GET /getAllPages?page=1&size=10&sort=nombre,desc
    @GetMapping("/getAllPages")
    @Operation(summary = "Obtiene los estudiantes en páginas")
    public ResponseEntity<ApiResponseDTO<Page<ClienteResponseDTO>>> findAllPageables(Pageable pageable) {
        try {
            Page<Cliente> clientes = clienteService.findAllPageable(pageable);
            Page<ClienteResponseDTO> clientesDTO = clientes.map(clienteMapper::toResponseDTO);
            return new ResponseEntity<>(new ApiResponseDTO<Page<ClienteResponseDTO>>(true, "Éxito", clientesDTO), HttpStatus.OK);
        } catch (AplicationExceptions e) {
            throw new AplicationExceptions(" Ha ocurrido un error " + e.getMessage());
        }
    }

    @GetMapping("/getPages")
    @Operation(summary = "Obtiene los clientes en páginas con filtros")
    public ResponseEntity<ApiResponseDTO<Page<ClienteResponseDTO>>> findAllPageable(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Cliente> clientes = clienteService.getFilteredClientes(nombre, apellido, page, size);
            Page<ClienteResponseDTO> clientesDTO = clientes.map(clienteMapper::toResponseDTO);
            return new ResponseEntity<>(new ApiResponseDTO<Page<ClienteResponseDTO>>(true, "Éxito", clientesDTO), HttpStatus.OK);
        } catch (AplicationExceptions e) {
            throw new AplicationExceptions("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
