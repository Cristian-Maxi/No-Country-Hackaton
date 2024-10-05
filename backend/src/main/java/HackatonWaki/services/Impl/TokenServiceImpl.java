package HackatonWaki.services.Impl;

import HackatonWaki.models.User;
import HackatonWaki.services.ITokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenServiceImpl implements ITokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    @Override
    public String generateToken(User user) {
        try{
            System.out.println("Generando token...");
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("Waki App")
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withClaim("role", user.getRol().name())
                    //.withClaim("nombre", user.getNombre())
                    .withExpiresAt(Date.from(generateExpirationDate()))
                    .sign(algorithm);

        }catch (JWTCreationException e){
            throw new RuntimeException("Error al crear el token");
        }

    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    @Override
    public String getUsernameFromToken(String token) {
        if(token == null){
            throw new RuntimeException("Token nulo");
        }
        DecodedJWT verifier;
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("Waki App")
                    .build()
                    .verify(token);
            verifier.getSubject();
        }catch (JWTCreationException e){
            throw new RuntimeException("Error al verificar el token");
        }
        if (verifier.getSubject() == null){
            throw new RuntimeException("Verificador invalido");
        }
        return verifier.getSubject();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt();
    }

}
