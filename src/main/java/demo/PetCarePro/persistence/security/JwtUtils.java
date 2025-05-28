package demo.PetCarePro.persistence.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import demo.PetCarePro.persistence.entities.Cliente;
import demo.PetCarePro.persistence.entities.Veterinario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // Tiempo de expiración en milisegundos (1 día)
    private final long jwtExpirationMs = 86400000;

    // Clave secreta base64 segura generada previamente (reemplaza por tu propia clave)
    private final String base64Secret = "CHoQq/8RdkJjTsDW3zB0VZr8MYrIcpv6KDEqGL2uV6z2dX8rS2a5i6u6I1+HZ9xDj9pH+nFQ6n2V2qwrFQwcyDw==";

    private final SecretKey key;

    public JwtUtils() {
        // Decodificamos la clave base64 a bytes y generamos la clave segura para HS512
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    }

    public String generateJwtToken(Veterinario vet) {
        return Jwts.builder()
                .setSubject(vet.getUsername())
                .claim("role", vet.getRole()) // 👉 Incluir el rol como claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String generateJwtTokenFromCliente(Cliente cliente) {
        return Jwts.builder()
                .setSubject(cliente.getUsername())
                .claim("role", cliente.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    
    public SecretKey getKey() {
        return this.key;
    }


}
