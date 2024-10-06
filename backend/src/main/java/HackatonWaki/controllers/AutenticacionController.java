package HackatonWaki.controllers;

import HackatonWaki.dtos.UserDTO.DatosAutenticacionUsuario;
import HackatonWaki.dtos.TokenDTO.JWTTokenDTO;
import HackatonWaki.services.IAutenticacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/login")
public class AutenticacionController {

    private final IAutenticacionService autenticacionService;

    @Autowired
    public AutenticacionController(IAutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }

    @PostMapping
    public ResponseEntity<JWTTokenDTO> autenticar(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        JWTTokenDTO jwtTokenDTO = autenticacionService.autenticar(datosAutenticacionUsuario);
        return ResponseEntity.ok(jwtTokenDTO);
    }
}
