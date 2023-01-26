package com.example.jwtcoworkingdental.controller.generico;

import com.example.jwtcoworkingdental.entities.generico.Generico;
import com.example.jwtcoworkingdental.service.generico.GenericoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Clase abstracta que implementa los metodos genericos del crud
 * @param <E>
 * @param <S>
 */

public abstract class GenericoControllerImpl  <E extends Generico,S extends GenericoServiceImpl<E,Long>> implements GenericoController<E, Long>{
    @Autowired
    protected S servicio;


    @GetMapping("")
    public ResponseEntity<?> getAll(){


        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findAll());
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente mas tarde.\"}");

        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findById(id));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente mas tarde.\"}");

        }
    }

    //creo que realmente no me hace falta aqui, mejor en el controlador de la clinica
    //tengo que comprobar que con este metodo puedo encotrar
    //cualquier cosa por nombre de forma generica
//    @GetMapping("/findOne/{nombre}")
//    public ResponseEntity<?> findByNombre(@PathVariable String nombre) {
//
//        System.out.println("BUSCANDO CLINICA POR NOMBRE");
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(servicio.findByNombre(nombre));
//        }catch (Exception e){
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al intentar borrar un elemento.  Por favor intente mas tarde.\"}");
//
//        }
//    }


    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody E entity){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.save(entity));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Al insertar los datos.\"}");

        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody E entity){

        if(id == null || entity.getId() == null){//comprobamos que los id son correctos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"El id no es correcto.\"}");

        }else{
            try {

                return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id,entity));
            }catch (Exception e){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Al insertar los datos.\"}");

            }
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){


        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(servicio.delete(id));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error.  Por favor intente mas tarde.\"}");

        }
    }


}
