package com.example.jwtcoworkingdental.controller.gabinete;


import com.example.jwtcoworkingdental.controller.generico.GenericoControllerImpl;
import com.example.jwtcoworkingdental.dto.clinica.ClinicaInDTO;
import com.example.jwtcoworkingdental.dto.gabinete.GabineteInDTO;
import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.entities.gabinete.Gabinete;
import com.example.jwtcoworkingdental.mapper.GabineteInDTOtoGabinete;
import com.example.jwtcoworkingdental.repositories.clinica.ClinicaRepository;
import com.example.jwtcoworkingdental.repositories.gabinete.GabineteRepository;
import com.example.jwtcoworkingdental.security.jwt.JwtProvider;
import com.example.jwtcoworkingdental.security.jwt.JwtTokenFilter;
import com.example.jwtcoworkingdental.service.gabinete.GabineteServiceImpl;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Clase de tipo @RestController, que sirve como punto de entrada a la aplicación
 * desde varios métodos para realizar funciones Crud de la misma.
 * la inserción de gabinetes en una clínica la haríamos desde otra url osea desde
 * una página siguiente por ejemplo.
 */
@RestController
@CrossOrigin(origins = "*")//anotacion que permite acceder desde cualquier tipo de aplicación
@RequestMapping("/gabinetes")
public class GabineteController extends GenericoControllerImpl<Gabinete, GabineteServiceImpl> {

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    GabineteRepository gabineteRepository;

    // TODO BUSCAR GABINETES POR ID DEL GABINETE Y EL ID DE LA CLÍNICA
    // TODO VERIFICAR LOS DATOS DE ENTRADA
    // TODO METODOS ADMIN SAVE,UPDATE,DELETE
    // TODO MÉTODOS USER GETALL, FINBYID, FINDBYESTADO


    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.getAllGabinetes());
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al intentar listar todos los elementos.  Por favor intente mas tarde.\"}");

        }
    }

    //
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        System.out.println("BUSCANDO GABINETE POR ID");
        try {

            if (servicio.getById(id) ==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"No existe el elemento.  Por favor intente mas tarde.\"}");

            }else{
                return ResponseEntity.status(HttpStatus.OK).body(servicio.getById(id));
            }


        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al intentar buscar  un elemento.  Por favor intente mas tarde.\"}");

        }
    }


    @GetMapping("/findByEstado/{estado}")
    public ResponseEntity<?> findByEstado(@PathVariable String estado) {

        System.out.println("BUSCANDO GABINETE POR ID");
        try {

            if (servicio.getAllGabinetesByEstado(estado).isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"No existen gabinetes con ese estado.  Por favor intente mas tarde.\"}");

            }else{
                return ResponseEntity.status(HttpStatus.OK).body(servicio.getAllGabinetesByEstado(estado));
            }



        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al intentar borrar un elemento.  Por favor intente mas tarde.\"}");

        }
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveGabineteByAdmin(@RequestBody GabineteInDTO gabineteInDTO, HttpServletRequest req)throws ServletException, IOException {

        System.out.println("guardando gabinetes");
        //obtenemos el token
        String requestTokenHeader = req.getHeader("Authorization");
        //obtenemos el nombre del usuario
        String nombre_usuario = jwtProvider.getNombreUsuarioFromToken(requestTokenHeader.replace("Bearer",""));
        //comprobamos si es administrador
        if (nombre_usuario.equals("admin")){

            try
            {
                return ResponseEntity.status(HttpStatus.OK).
                        body(servicio.saveGabineteDTO(gabineteInDTO));

            }catch (Exception e){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Al insertar los datos de la clinica.\"}");

            }

        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\":\"No eres admin.\"}");
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateGabineteByAdmin(@RequestBody GabineteInDTO gabineteInDTO, HttpServletRequest req)throws ServletException, IOException {

        //obtenemos el token
        String requestTokenHeader = req.getHeader("Authorization");
        //obtenemos el nombre del usuario
        String nombre_usuario = jwtProvider.getNombreUsuarioFromToken(requestTokenHeader.replace("Bearer",""));
        if (nombre_usuario.equals("admin")){
            try
            {

                if (servicio.updateGabineteDTO(gabineteInDTO) != null) {

                    return ResponseEntity.status(HttpStatus.OK).
                            body(servicio.updateGabineteDTO(gabineteInDTO));
                }

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Al insertar los datos del gabinete.\"}");

            }catch (Exception e){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Al insertar los datos de la clinica.\"}");

            }

        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\":\"No eres admin.\"}");
        }


    }

    //el método borrar un gabinete tiene que restar del número de gabinetes de la clínica
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        System.out.println("intentando borrar un elemento " + id);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.delete(id));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al intentar borrar un elemento.  Por favor intente mas tarde.\"}");

        }

    }



}
