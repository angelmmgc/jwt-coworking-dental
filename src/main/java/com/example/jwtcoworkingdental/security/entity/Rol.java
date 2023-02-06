package com.example.jwtcoworkingdental.security.entity;



import com.example.jwtcoworkingdental.security.enums.RolNombre;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolNombre rolNombre;

//    @ManyToMany(mappedBy = "roles")
//    private Set<Usuario> usuarios;


    public Rol() {
    }

    public Rol(@NotNull RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RolNombre getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }

//    public Set<Usuario> getUsuarios() {
//        return usuarios;
//    }
//
//    public void setUsuarios(Set<Usuario> usuarios) {
//        this.usuarios = usuarios;
//    }
}
