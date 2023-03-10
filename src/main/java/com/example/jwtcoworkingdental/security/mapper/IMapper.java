package com.example.jwtcoworkingdental.security.mapper;

/**
 * Interface con funcin para transformar un objeto de entrada en objeto Entidad
 * @param <I>
 * @param <O>
 */
public interface IMapper<I, O>{

    O map(I in);

    O updatemap(I in);


}
