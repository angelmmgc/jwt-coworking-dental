package com.example.jwtcoworkingdental.service.generico;

import com.example.jwtcoworkingdental.entities.generico.Generico;
import com.example.jwtcoworkingdental.repositories.generico.GenericoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class GenericoServiceImpl <E extends Generico,ID extends Serializable> implements GenericoService<E, ID>{



    protected GenericoRepository<E,ID> genericoRepository;

    //CONSTRUCT


    public GenericoServiceImpl(GenericoRepository<E, ID> genericoRepository) {
        this.genericoRepository = genericoRepository;
    }

    @Override
    @Transactional//estos metodos haran transacciones
    public List<E> findAll() throws Exception {

        try{
            List<E> entities = genericoRepository.findAll();
            return entities;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    @Transactional//estos metodos haran transacciones
    public E findById(ID id) throws Exception {
        try{

            Optional<E> entityOptional = genericoRepository.findById(id);
            return entityOptional.get();

        }catch (Exception e){
            throw new Exception(e.getMessage());

        }
    }

    @Override
    @Transactional//estos metodos haran transacciones
    public E save(E entity) throws Exception {
        try{
            entity = genericoRepository.save(entity);
            return entity;

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * metodo generico que actualiza datos, en el caso de los usuarios si no
     * el json no tiene el id la funcion lo que hace es crear un nuevo usuario
     * no actualiza
     * @param id
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    @Transactional//estos metodos haran transacciones
    public E update(ID id,E entity) throws Exception {

        try {

            E entityUpdate = null;
            if (id.equals(entity.getId())) {//comprobamos ids de entrada parametro y json
                Optional<E> entityOptional = genericoRepository.findById(id);

                entityUpdate = entityOptional.get();
                entityUpdate = genericoRepository.save(entity);
                return entityUpdate;
            } else {
                return entity;//devuelvo la entidad que ha entrado en json

            }


        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional//estos métodos harán transacciones
    public boolean delete(ID id) throws Exception {
        try{
            if(genericoRepository.existsById(id)){
                genericoRepository.deleteById(id);
                return true;
            }
            throw new Exception();

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
