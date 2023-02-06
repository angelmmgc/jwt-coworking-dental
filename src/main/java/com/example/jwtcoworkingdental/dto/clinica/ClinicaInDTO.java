package com.example.jwtcoworkingdental.dto.clinica;


import com.example.jwtcoworkingdental.entities.gabinete.Gabinete;
import com.example.jwtcoworkingdental.security.entity.Usuario;

import java.util.HashSet;
import java.util.Set;

public class ClinicaInDTO {

    private Long id;

    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private int numGabinetes;

    private Set<Usuario> administra = new HashSet<>();

    private Set<Gabinete> gabinetes = new HashSet<>();



    //GETTER AND SETTER


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNumGabinetes() {
        return numGabinetes;
    }

    public void setNumGabinetes(int numGabinetes) {
        this.numGabinetes = numGabinetes;
    }

    public Set<Usuario> getAdministra() {
        return administra;
    }

    public void setAdministra(Set<Usuario> administra) {
        this.administra = administra;
    }

    public Set<Gabinete> getGabinetes() {
        return gabinetes;
    }

    public void setGabinetes(Set<Gabinete> gabinetes) {
        this.gabinetes = gabinetes;
    }
}
