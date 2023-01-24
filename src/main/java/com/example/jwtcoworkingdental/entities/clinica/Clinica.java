package com.example.jwtcoworkingdental.entities.clinica;

import com.example.jwtcoworkingdental.entities.generico.Generico;
import com.example.jwtcoworkingdental.security.entity.Usuario;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clinicas")
public class Clinica extends Generico {


    @Column(name = "nombre")
    private String nombre;
    @Column(name = "email")
    private String email;
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "numGabinetes")
    private int numGabinetes;


    //constructor
    public Clinica() {
    }

    public Clinica(String nombre, String email, String telefono, String direccion, int numGabinetes, Set<Usuario> administra) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.numGabinetes = numGabinetes;
        this.administra = administra;
    }

    //asociaciones
    /**
     * asociación de la tabla clínica con la tabla user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @OnDelete( action = OnDeleteAction.CASCADE )
    @JoinTable(name = "ADMINISTRA",
            joinColumns = {
                    @JoinColumn(name = "CLINICA_ID")


            },

            inverseJoinColumns = {
                    @JoinColumn(name = "USER_ID") })
    private Set<Usuario> administra;


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
}
