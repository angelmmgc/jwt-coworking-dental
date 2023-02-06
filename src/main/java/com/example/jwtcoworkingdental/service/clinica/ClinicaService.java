package com.example.jwtcoworkingdental.service.clinica;

import com.example.jwtcoworkingdental.entities.generico.Generico;
import com.example.jwtcoworkingdental.repositories.clinica.ClinicaRepository;
import com.example.jwtcoworkingdental.security.repository.UsuarioRepository;
import com.example.jwtcoworkingdental.service.generico.GenericoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public interface ClinicaService<E extends Generico,ID extends Serializable> {


}
