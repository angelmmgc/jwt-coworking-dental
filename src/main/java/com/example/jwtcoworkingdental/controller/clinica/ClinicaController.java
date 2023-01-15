package com.example.jwtcoworkingdental.controller.clinica;

import com.example.jwtcoworkingdental.controller.generico.GenericoControllerImpl;
import com.example.jwtcoworkingdental.dto.clinica.ClinicaInDTO;
import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.mapper.ClinicaInDTOtoClinica;
import com.example.jwtcoworkingdental.security.entity.Usuario;
import com.example.jwtcoworkingdental.security.jwt.JwtProvider;
import com.example.jwtcoworkingdental.security.jwt.JwtTokenFilter;
import com.example.jwtcoworkingdental.security.repository.UsuarioRepository;
import com.example.jwtcoworkingdental.service.clinica.ClinicaServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

/**
 * solo tienen acceso los administradores
 */
@RestController
@CrossOrigin(origins = "*")//anotacion que permite acceder desde cualquier tipo de aplicación
@RequestMapping("/clinicas")
public class ClinicaController extends GenericoControllerImpl<Clinica,ClinicaServiceImpl> {


    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UsuarioRepository usuario;
    //funciones genéricas de obtener todos, buscar por id,borrar
    //si actualizo por método genérico me borra el administrador que ha realizado la actualización,
    //ya que extienden de la clase GenericoControllerImpl
    //PARA BUSQUEDAS ESPECIFICAS DEBO IMPLEMENTARLAS EN UNA INTERFACE


    /**
     *
     * metodo para guardar un clinica por un administrador
     * @param clinicaInDTO
     * @param req
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @PostMapping("/saveClinicaByAdmin")
    public ResponseEntity<?> saveClinicaByAdmin(@RequestBody ClinicaInDTO clinicaInDTO, HttpServletRequest req)throws ServletException, IOException {

        //obtenemos el token
        String requestTokenHeader = req.getHeader("Authorization");
        //obtenemos el nombre del usuario
        String nombre_usuario = jwtProvider.getNombreUsuarioFromToken(requestTokenHeader.replace("Bearer",""));

        try
        {
            return ResponseEntity.status(HttpStatus.OK).
                    body(servicio.saveClinicaDTO(clinicaInDTO,nombre_usuario));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Al insertar los datos de la clinica.\"}");

        }

    }

    /**
     * metodo para actualizar una clinica por un administrador
     * @param clinicaInDTO
     * @param req
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @PutMapping("/updateClinicaByAdmin")
    public ResponseEntity<?> updateClinicaByAdmin(@RequestBody ClinicaInDTO clinicaInDTO, HttpServletRequest req)throws ServletException, IOException {

        //obtenemos el token
        String requestTokenHeader = req.getHeader("Authorization");
        //obtenemos el nombre del usuario
        String nombre_usuario = jwtProvider.getNombreUsuarioFromToken(requestTokenHeader.replace("Bearer",""));

        try
        {
            return ResponseEntity.status(HttpStatus.OK).
                    body(servicio.updateClinicaDTO(nombre_usuario,clinicaInDTO));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Al insertar los datos de la clinica.\"}");

        }

    }


    //BUSQUEDAS DE CLINICAS POR NOMBRE (CON ROLE DE USUARIO)




}
