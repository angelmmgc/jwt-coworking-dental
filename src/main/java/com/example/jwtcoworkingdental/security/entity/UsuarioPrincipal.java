package com.example.jwtcoworkingdental.security.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * implementamos los privilegios de cada usuario
 * es la clase especifica para obtener los datos de usuario y los privilegios
 *
 */
public class UsuarioPrincipal implements UserDetails {


    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public UsuarioPrincipal(String username, String nombre, String apellidos, String email, String telefono, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.authorities = authorities;
    }

    //metodo static añadimos los privilegios
    public static UsuarioPrincipal build(Usuario usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
                        .getRolNombre().name())).collect(Collectors.toList());

        return new UsuarioPrincipal(
                                    usuario.getUserName(),
                                    usuario.getNombre(),
                                    usuario.getApellidos(),
                                    usuario.getEmail(),
                                    usuario.getTelefono(),
                                    usuario.getPassword(),
                                    authorities);
    }


//    public static UsuarioPrincipal build(Usuario1 usuario){
//        List<GrantedAuthority> authorities =
//                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
//                        .getRolNombre().name())).collect(Collectors.toList());
//
//        return new UsuarioPrincipal(usuario.getNombre(),
//                usuario.getApellidos(),
//                usuario.getUserName(),
//                authorities);
//    }



    //USUARIO1






    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }


}
