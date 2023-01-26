package com.example.jwtcoworkingdental.repositories.clinica;

import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.repositories.generico.GenericoRepository;

import java.util.Optional;

public interface ClinicaRepository extends GenericoRepository<Clinica,Long> {
    @Override
    Optional<Clinica> findByNombre(String nombre);





}
