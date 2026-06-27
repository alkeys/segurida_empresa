package com.alex.com10.utils;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    /**
     * Genera un token JWT firmado simétricamente con HMAC-SHA256.
     *
     * @param username El nombre de usuario (subject).
     * @param roles Lista de roles que tiene el usuario (ej. List.of("CLIENTE", "ADMIN")).
     * @return El token JWT serializado.
     */
    public String generateToken(String username, List<String> roles) {
        try {
            JWSSigner signer = new MACSigner(jwtSecret.getBytes());

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .claim("roles", roles) // Guarda los roles en la propiedad "roles"
                    .issueTime(new Date())
                    .expirationTime(new Date(new Date().getTime() + jwtExpirationMs))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el token JWT", e);
        }
    }
}
