package com.example.jwtcoworkingdental.repositories.gabinete;
import com.example.jwtcoworkingdental.entities.gabinete.Gabinete;
import com.example.jwtcoworkingdental.repositories.generico.GenericoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GabineteRepository extends GenericoRepository<Gabinete,Long> {
    @Query(value = "SELECT * FROM gabinetes WHERE estado=:estado",nativeQuery = true)
    List<Gabinete> findByEstadoListGabinetes(@Param("estado") String estado);



}
