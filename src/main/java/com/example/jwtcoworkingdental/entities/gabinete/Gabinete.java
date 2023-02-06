package com.example.jwtcoworkingdental.entities.gabinete;

import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.entities.generico.Generico;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
;

@Entity
@Table(name = "gabinetes")
public class Gabinete extends Generico {


    private String tipo;
    private String estado;


    public Gabinete() {

    }
    public Gabinete(String tipo, String estado, Clinica clinica) {
        this.tipo = tipo;
        this.estado = estado;
        this.clinica = clinica;
    }

    public Gabinete(Long id, String tipo, String estado, Clinica clinica) {
        super(id);
        this.tipo = tipo;
        this.estado = estado;
        this.clinica = clinica;
    }

    //asociaciones

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "clinica_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Clinica clinica;




    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Clinica getClinica() {
        return clinica;
    }

    public void setClinica(Clinica clinica) {
        this.clinica = clinica;
    }


}
