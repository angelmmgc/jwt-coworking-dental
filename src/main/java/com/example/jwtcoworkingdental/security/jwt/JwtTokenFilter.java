package com.example.jwtcoworkingdental.security.jwt;


import com.example.jwtcoworkingdental.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * se ejecuta por cada peticion,
 * comprueba si hay un token valido
 * para ello utiliza la clase TokenProvider
 * , si no devuelve un 401 no autorizado
 *
 */

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    //va a decir si el usuario esta autenticado y si el token es valido

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

            try {
                //guardamo el token
                String token = getToken(req);
                //comparamos que el token existe y que es valido
                if (token != null && jwtProvider.validateToken(token)) {
                    //obtenemos el usuario a partir del token
                    String nombreUsuario = jwtProvider.getNombreUsuarioFromToken(token);
                    //creamos un UserDetail
                    UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);
                    //Autenticamos al usuario
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //lo pasamos al contexto de autenticacion
                    SecurityContextHolder.getContext().setAuthentication(auth);

                }

            }catch (Exception e){
                logger.error("FAIL EN EL METODO doFilter" + e.getMessage() );
            }

            //llamamos a filter chair
            filterChain.doFilter(req,res);

    }

    private String getToken(HttpServletRequest request){

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer"))
            return header.replace("Bearer","");
        return null;
    }
}
