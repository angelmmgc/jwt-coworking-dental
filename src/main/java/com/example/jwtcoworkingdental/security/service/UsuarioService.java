package com.example.jwtcoworkingdental.security.service;


import com.example.jwtcoworkingdental.security.entity.Usuario;
import com.example.jwtcoworkingdental.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * obtiene un usuario por nombre
     * @param nombreUsuario
     * @return
     */
    public Optional<Usuario> getByNombreUsuario(String nombreUsuario){


        return usuarioRepository.findUsuarioByUserName(nombreUsuario);
    }

    /**
     * comprueba si exsite un usuario por nombre de usuario
     * @param nombreUsuario
     * @return
     */
    public boolean existsByNombreUsuario(String nombreUsuario){

        return usuarioRepository.existsByUserName(nombreUsuario);
    }

    /**
     * comprueba si existe un email
     * @param email
     * @return
     */
    public boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    /**
     * obtiene un usuario por email
     * @param email
     * @return
     */
    public Optional<Usuario> getByEmailUsuario(String email){

        Optional<Usuario> usuario = null;

        if (existsByEmail(email))
            usuario = usuarioRepository.findUsuarioByEmail(email);
        return usuario;

    }


    /**
     * lista todos los usuarios
     * @return
     */
    public List<Usuario> findAll(){

        return usuarioRepository.findAll();
    }

    /**
     * guarda un usuario
     * @param usuario
     * @return
     */
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    /**
     * funcion de borrado
     * @param username
     */
    @Transactional//estos métodos harán transacciones
    public boolean delete(String username) throws Exception {

        System.out.println("delete service");
        try{
            if(usuarioRepository.existsByUserName(username)){
                Long id = usuarioRepository.findUsuarioByUserName(username).get().getId();
                System.out.println(id);
                //usuarioRepository.deleteByUserName(id);
                usuarioRepository.deleteById(id);
                return true;
            }
            throw new Exception();

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
