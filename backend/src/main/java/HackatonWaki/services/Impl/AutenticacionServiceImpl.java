package HackatonWaki.services.Impl;

import HackatonWaki.dtos.UserDTO.DatosAutenticacionUsuario;
import HackatonWaki.dtos.TokenDTO.JWTTokenDTO;
import HackatonWaki.models.User;
import HackatonWaki.services.IAutenticacionService;
import HackatonWaki.services.ITokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionServiceImpl implements IAutenticacionService {

    private final AuthenticationManager authenticationManager;
    private final ITokenService tokenService;

    public AutenticacionServiceImpl(AuthenticationManager authenticationManager, ITokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public JWTTokenDTO autenticar(DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(), datosAutenticacionUsuario.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);
        return new JWTTokenDTO(token);
    }
}