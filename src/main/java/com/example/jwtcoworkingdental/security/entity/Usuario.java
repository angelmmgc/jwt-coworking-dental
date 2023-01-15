package com.example.jwtcoworkingdental.security.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


/**
 * accede a la base de datos
 */
@Entity
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username")
    private String userName;

    @Column(name = "nombre")
    @NotNull
    private String nombre;

    @NotNull
    @Column(name = "apellidos")
    private String apellidos;
    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "telefono")
    private String telefono;


    @NotNull
    @Column(name = "password")
    private String password;


    //CONSTRUCT

    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String email, String telefono, String password) {

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    public Usuario(String userName, String nombre, String apellidos, String email, String telefono, String password) {
        this.userName = userName;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    public Usuario(String userName, String nombre, String apellidos, String email, String telefono, String password, Set<Rol> roles) {
        this.userName = userName;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.roles = roles;
    }

    public Usuario(Long id, String userName, String nombre, String apellidos, String email, String telefono, String password) {
        this.id = id;
        this.userName = userName;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.password = password;

    }

    //ASOCIACIONES

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<Rol> roles;




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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }


}

