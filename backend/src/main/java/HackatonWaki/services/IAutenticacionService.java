package HackatonWaki.services;

import HackatonWaki.dtos.UserDTO.DatosAutenticacionUsuario;
import HackatonWaki.dtos.TokenDTO.JWTTokenDTO;

public interface IAutenticacionService {

    JWTTokenDTO autenticar(DatosAutenticacionUsuario datosAutenticacionUsuario);

}
