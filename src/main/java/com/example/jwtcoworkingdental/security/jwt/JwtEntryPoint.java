package com.example.jwtcoworkingdental.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 *
 * se ejecuta por cada peticion
 * va a comprobar que se valido el token utilizando la clase TokenProvider
 * en caso de que el token sea valido permite el acceso al recurso
 * en caso contraio lanza excepcion
 *
 *rechaza todas la peticiones que no esten autenticadas enviando la respuesta no autorizado
 */

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {

        logger.error("fail en el metodo commence");
        logger.error(authException.getMessage());

        res.sendError(HttpServletResponse.SC_UNAUTHORIZED,"NO AUTORIZADO");
    }
}
