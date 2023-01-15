package com.example.jwtcoworkingdental.service.generico;

import com.example.jwtcoworkingdental.entities.generico.Generico;

import java.io.Serializable;
import java.util.List;

/**
 * Interface con funciones genericas crud
 */
public interface GenericoService <E extends Generico,ID extends Serializable>{
    List<E> findAll() throws Exception;
    E findById(ID id) throws Exception;
    E save(E entity) throws Exception;
    E update(ID id,E entity) throws Exception;
    boolean delete(ID id) throws Exception;
}
