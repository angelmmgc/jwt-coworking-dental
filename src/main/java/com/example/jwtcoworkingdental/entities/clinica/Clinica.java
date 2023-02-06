package com.example.jwtcoworkingdental.entities.clinica;

import com.example.jwtcoworkingdental.entities.gabinete.Gabinete;
import com.example.jwtcoworkingdental.entities.generico.Generico;
import com.example.jwtcoworkingdental.security.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
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
//
//    public Clinica(String nombre, String email, String telefono, String direccion, int numGabinetes, Set<Usuario> administra) {
//        this.nombre = nombre;
//        this.email = email;
//        this.telefono = telefono;
//        this.direccion = direccion;
//        this.numGabinetes = numGabinetes;
//        this.administra = administra;
//    }
//
//    public Clinica(String nombre, String email, String telefono, String direccion, int numGabinetes, Set<Usuario> administra, Set<Gabinete> gabinetes) {
//        this.nombre = nombre;
//        this.email = email;
//        this.telefono = telefono;
//        this.direccion = direccion;
//        this.numGabinetes = numGabinetes;
//        this.administra = administra;
//        this.gabinetes = gabinetes;
//    }
//
//
//    public Clinica(String email, String telefono, String direccion, int numGabinetes, Set<Usuario> administra, Set<Gabinete> gabinetes) {
//        this.email = email;
//        this.telefono = telefono;
//        this.direccion = direccion;
//        this.numGabinetes = numGabinetes;
//        this.administra = administra;
//        this.gabinetes = gabinetes;
//    }
//
//    public Clinica(Long id, String email, String telefono, String direccion, int numGabinetes, Set<Usuario> administra, Set<Gabinete> gabinetes) {
//        super(id);
//        this.email = email;
//        this.telefono = telefono;
//        this.direccion = direccion;
//        this.numGabinetes = numGabinetes;
//        this.administra = administra;
//        this.gabinetes = gabinetes;
//    }
//
//    public Clinica(Long id, String nombre, String email, String telefono, String direccion, int numGabinetes, Set<Usuario> administra, Set<Gabinete> gabinetes) {
//        super(id);
//        this.nombre = nombre;
//        this.email = email;
//        this.telefono = telefono;
//        this.direccion = direccion;
//        this.numGabinetes = numGabinetes;
//        this.administra = administra;
//        this.gabinetes = gabinetes;
//    }

    public Clinica(Long id, int numGabinetes) {
        super(id);
        this.numGabinetes = numGabinetes;
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

    @OneToMany(mappedBy = "clinica",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Gabinete> gabinetes = new HashSet<>();


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

    @Override
    public String toString() {
        return "Clinica{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", numGabinetes=" + numGabinetes +
                ", administra=" + administra +
                ", gabinetes=" + gabinetes +
                '}';
    }
}
