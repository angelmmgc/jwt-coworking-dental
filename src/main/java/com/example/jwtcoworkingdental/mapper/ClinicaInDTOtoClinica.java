package com.example.jwtcoworkingdental.mapper;

import com.example.jwtcoworkingdental.dto.clinica.ClinicaInDTO;
import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import org.springframework.stereotype.Component;

@Component
public class ClinicaInDTOtoClinica implements IMapper<ClinicaInDTO, Clinica> {


    @Override
    public Clinica map(ClinicaInDTO in) {


        Clinica clinica = new Clinica();

            clinica.setNombre(in.getNombre());
            clinica.setEmail(in.getEmail());
            clinica.setTelefono(in.getTelefono());
            clinica.setDireccion(in.getDireccion());
            clinica.setNumGabinetes(in.getNumGabinetes());
            clinica.setAdministra(in.getAdministra());

        return clinica;

    }

    public Clinica mapUdate(ClinicaInDTO in){

        Clinica clinica = new Clinica();
        clinica.setId(in.getId());
        clinica.setNombre(in.getNombre());
        clinica.setEmail(in.getEmail());
        clinica.setTelefono(in.getTelefono());
        clinica.setDireccion(in.getDireccion());
        clinica.setNumGabinetes(in.getNumGabinetes());
        clinica.setAdministra(in.getAdministra());

        return clinica;
    }
}

