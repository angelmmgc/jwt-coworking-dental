package com.example.jwtcoworkingdental.repositories.generico;

import com.example.jwtcoworkingdental.entities.generico.Generico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericoRepository<E extends Generico, ID extends Serializable> extends JpaRepository<E, ID> {
}
