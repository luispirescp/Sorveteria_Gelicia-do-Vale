package br.com.cursojava.petshop.infra.security;

import br.com.cursojava.petshop.domain.user.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarTone(Usuario usuario){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Sorveteria")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token jwt", exception);
        }
    }
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
