package com.example.jwtcoworkingdental.repositories.generico;

import com.example.jwtcoworkingdental.entities.generico.Generico;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface GenericoRepository<E extends Generico, ID extends Serializable> extends JpaRepository<E, ID> {

    Optional<E> findByNombre(String nombre);
}
