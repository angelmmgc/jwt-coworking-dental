package com.example.jwtcoworkingdental.mapper;

import com.example.jwtcoworkingdental.dto.gabinete.GabineteInDTO;
import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.entities.gabinete.Gabinete;
import com.example.jwtcoworkingdental.security.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class GabineteInDTOtoGabinete implements IMapper<GabineteInDTO, Gabinete>{

    public Gabinete map(GabineteInDTO in,Clinica clinica) {


        Gabinete gabinete = new Gabinete();
        Set<Gabinete> gabinetes = new HashSet<>();

        gabinete.setEstado(in.getEstado());
        gabinete.setTipo(in.getTipo());
        gabinetes.add(gabinete);
        clinica.setGabinetes(gabinetes);
        gabinete.setClinica(clinica);

        return gabinete;
    }

    public Gabinete mapUpdate(GabineteInDTO in,Clinica clinica){

        Gabinete gabinete = new Gabinete();
        Set<Gabinete> gabinetes = new HashSet<>();

        gabinete.setId(in.getId());
        gabinete.setEstado(in.getEstado());
        gabinete.setTipo(in.getTipo());
        gabinetes.add(gabinete);
        clinica.setGabinetes(gabinetes);
        gabinete.setClinica(clinica);

        return gabinete;

    }

    @Override
    public Gabinete map(GabineteInDTO in) {
        return null;
    }
}
