package com.example.jwtcoworkingdental.dto.gabinete;

import com.example.jwtcoworkingdental.entities.clinica.Clinica;

public class GabineteInDTO {

    private Long id;
    private String estado;
    private String tipo;

    private Long clinica_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getClinica_id() {
        return clinica_id;
    }

    public void setClinica_id(Long clinica_id) {
        this.clinica_id = clinica_id;
    }
}
