package com.example.jwtcoworkingdental.security.jwt;



import com.example.jwtcoworkingdental.security.entity.UsuarioPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * esta clase genera el token y metodos de validacion
 */

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication){

        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();

        return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public String getNombreUsuarioFromToken(String token){

        return  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }


    //obtener el id del token

    public String getIdUsuarioFromToken(String token){

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getAudience();
    }
    public boolean validateToken(String token){

        try{

            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;

        }catch (MalformedJwtException e){
            logger.error("TOKEN MAL FORMADO");
        }
        catch (UnsupportedJwtException e){
            logger.error("TOKEN NO SOPORTADO");
        }
        catch (ExpiredJwtException e){
            logger.error("TOKEN EXPIRADO");
        }catch (IllegalArgumentException e){
            logger.error("TOKEN VACIO");
        }
        catch (SecurityException e){
            logger.error("FALLO EN LA FIRMA");
        }

        return false;
    }


}
