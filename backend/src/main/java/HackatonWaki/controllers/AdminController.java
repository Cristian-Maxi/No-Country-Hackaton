package HackatonWaki.controllers;

import HackatonWaki.dtos.AdminDTO.AdminRequestDTO;
import HackatonWaki.dtos.AdminDTO.AdminResponseDTO;
import HackatonWaki.dtos.AdminDTO.AdminUpdateDTO;
import HackatonWaki.dtos.ApiResponseDTO;
import HackatonWaki.exceptions.AplicationExceptions;
import HackatonWaki.mappers.AdminMapper;
import HackatonWaki.models.Admin;
import HackatonWaki.services.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    private final IAdminService adminService;
    private final AdminMapper adminMapper;

    public AdminController(IAdminService adminService, AdminMapper AdminMapper) {
        this.adminService = adminService;
        this.adminMapper = AdminMapper;
    }

    @GetMapping("/getAll")
    @Operation(summary = "Obtiene todos los estudiantes")
    public ResponseEntity<ApiResponseDTO<AdminResponseDTO>> findAll() {
        try {
            List<Admin> admins = adminService.findAll();
            List<AdminResponseDTO> adminsDTO = admins.stream()
                    .map(adminMapper::toResponseDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new ApiResponseDTO<>(true, "Exito", adminsDTO), HttpStatus.CREATED);
        } catch (AplicationExceptions e) {
            throw new AplicationExceptions(" Ha ocurrido un error " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un estudiante en particular")
    public ResponseEntity<ApiResponseDTO<AdminResponseDTO>> findById(@PathVariable("id") Long id) {
        Optional<Admin> admin = adminService.findById(id);
        if (admin.isPresent()) {
            AdminResponseDTO AdminResponseDTO = adminMapper.toResponseDTO(admin.get());
            String message = "Estudiante encontrado";
            return new ResponseEntity<>(new ApiResponseDTO<>(true, message, AdminResponseDTO), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, "Admin no encontrado", null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    @Operation(summary = "Se agrega un Admin")
    public ResponseEntity<ApiResponseDTO<AdminResponseDTO>> save(@RequestBody @Valid AdminRequestDTO adminRequestDTO) {
        Admin admin = adminMapper.toEntity(adminRequestDTO);
        adminService.save(admin);
        AdminResponseDTO adminResponseDTO = adminMapper.toResponseDTO(admin);
        String message = "Admin Registrado";
        return new ResponseEntity<>(new ApiResponseDTO<>(true, message, adminResponseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Operation(summary = "Se actualiza un Admin en particular")
    public ResponseEntity<ApiResponseDTO<AdminResponseDTO>> update(@RequestBody @Valid AdminUpdateDTO adminUpdateDTO) {
        Optional<Admin> buscarAdmin = adminService.findById(adminUpdateDTO.id());
        if (buscarAdmin.isPresent()) {
            Admin admin = buscarAdmin.get();
            admin.setNombre(adminUpdateDTO.nombre());
            admin.setApellido(adminUpdateDTO.apellido());
            Long usuarioId = admin.getUsuario().getId();
            admin.getUsuario().setId(usuarioId);
            adminService.update(admin);
            AdminResponseDTO adminResponseDTO = adminMapper.toResponseDTO(admin);
            String message = "Cliente actualizado con éxito";
            return new ResponseEntity<>(new ApiResponseDTO<>(true, message, adminResponseDTO), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponseDTO<>(false, "Cliente no encontrado", null), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Se elimina un Admin en particular")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

// Si queremos implementar un Borrado Lógico
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Se elimina un Admin en particular")
//    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
//        adminService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}