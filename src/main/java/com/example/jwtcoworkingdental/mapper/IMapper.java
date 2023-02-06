package com.example.jwtcoworkingdental.mapper;

import com.example.jwtcoworkingdental.dto.gabinete.GabineteInDTO;
import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.entities.gabinete.Gabinete;

/**
 * Interface con funcin para transformar un objeto de entrada en objeto Entidad
 * @param <I>
 * @param <O>
 */
public interface IMapper <I, O>{
    O map(I in);

}
