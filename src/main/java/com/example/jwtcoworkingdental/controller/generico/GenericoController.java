package com.example.jwtcoworkingdental.controller.generico;


import com.example.jwtcoworkingdental.entities.generico.Generico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * Interface con las funciones genéricas de un crud
 * @param <E>
 * @param <ID>
 */
public interface GenericoController <E extends Generico,ID extends Serializable>{

    ResponseEntity<?> getAll();//la interrogacion es para que pueda extender de cualquier objeto
    ResponseEntity<?> getOne(@PathVariable ID id);
    ResponseEntity<?> save(@RequestBody E entity);
    ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);
    ResponseEntity<?> delete(@PathVariable ID Id);
}
