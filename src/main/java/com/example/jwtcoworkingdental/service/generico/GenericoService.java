package com.example.jwtcoworkingdental.service.generico;

import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.entities.generico.Generico;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Interface con funciones genericas crud
 */
public interface GenericoService <E extends Generico,ID extends Serializable>{
    List<E> findAll() throws Exception;
    E findById(ID id) throws Exception;

    //estos métodos harán transacciones
    E findByNombre(String nombre) throws Exception;//no se si quitarlo

    E save(E entity) throws Exception;
    E update(ID id,E entity) throws Exception;
    boolean delete(ID id) throws Exception;


}
