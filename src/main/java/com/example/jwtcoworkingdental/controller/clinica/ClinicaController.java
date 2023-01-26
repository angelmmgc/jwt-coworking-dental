package com.example.jwtcoworkingdental.controller.clinica;

import com.example.jwtcoworkingdental.controller.generico.GenericoControllerImpl;
import com.example.jwtcoworkingdental.dto.clinica.ClinicaInDTO;
import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.mapper.ClinicaInDTOtoClinica;
import com.example.jwtcoworkingdental.repositories.clinica.ClinicaRepository;
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
 * Clase de tipo @RestController, que sirve como punto de entrada a la aplicación
 * desde varios métodos para realizar funciones Crud de la misma.
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

    @Autowired
    ClinicaRepository clinicaRepository;

    @Autowired
    ClinicaServiceImpl clinicaService;

    //metodos crud buscar, crear, eliminar y actualizar
    //funciones genéricas de obtener todos, buscar with id, borrar
    //si actualizo por método genérico me borra el administrador que ha realizado la actualización,
    //ya que extienden de la clase GenericoControllerImpl
    //PARA BUSQUEDAS ESPECIFICAS DEBO IMPLEMENTARLAS EN UNA INTERFACE


    /**
     *
     * Guarda una clínica por un administrador.
     * @param clinicaInDTO . Datos pasados por json.
     * @param req . Parámetro de tipo HttpServletRequest para obtener datos de la cabecera de la petición.
     * @return . Status ok y clínica creada.
     *          . Status NOT FOUND en caso de consulta errónea.
     * @throws ServletException . Excepciones de tipo.
     * @throws IOException .Excepciones de tipo.
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveClinicaByAdmin(@RequestBody ClinicaInDTO clinicaInDTO, HttpServletRequest req)throws ServletException, IOException {

        //obtenemos el token
        String requestTokenHeader = req.getHeader("Authorization");
        //obtenemos el nombre del usuario
        String nombre_usuario = jwtProvider.getNombreUsuarioFromToken(requestTokenHeader.replace("Bearer",""));

        try
        {
            return ResponseEntity.status(HttpStatus.OK).
                    body(servicio.saveClinicaDTO(nombre_usuario,clinicaInDTO));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Al insertar los datos de la clinica.\"}");

        }

    }

    /**
     * Actualizar una clínica por un administrador
     * @param clinicaInDTO . Datos pasados por json.
     * @param req . Parámetro de tipo HttpServletRequest para obtener datos de la cabecera de la petición.
     * @return . Status ok y clínica actualizada.
     *          . Status NOT FOUND en caso de consulta errónea.
     * @throws ServletException . Excepciones de tipo.
     * @throws IOException .Excepciones de tipo.
     */
    @PutMapping("/update")
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

    /**
     * elimina clínica. Solo admin.
     * @param id . Parámetro pasado por url.
     * @return . Status ok y retorna true.
     *         . Status NOT FOUND en caso de consulta errónea.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        System.out.println("intentando borrar un elemento " + id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.delete(id));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al intentar borrar un elemento.  Por favor intente mas tarde.\"}");

        }

    }



    /**
     * listado de todas las clínicas, podrán acceder ADMIN y USER.
     * @return . Status ok y listado en caso de consulta correcta.
     *         . Status NOT FOUND en caso de consulta errónea.
     */
    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.getAllClinicas());
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al intentar borrar un elemento.  Por favor intente mas tarde.\"}");

        }
    }

    /**
     * busca clínica por nombre.
     *
     * @param   nombre .Parámetro pasado por url.
     * @return . Status ok y se muestra clínica en caso de consulta correcta.
     *         . Status NOT FOUND en caso de consulta errónea.
     */
    @GetMapping("/one/{nombre}")
    public ResponseEntity<?> findByNombre(@PathVariable String nombre) {

        System.out.println("BUSCANDO CLINICA POR NOMBRE");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findByNombre(nombre));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al intentar borrar un elemento.  Por favor intente mas tarde.\"}");

        }
    }







}
