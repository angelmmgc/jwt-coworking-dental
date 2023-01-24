package com.example.jwtcoworkingdental.security.mapper;



import com.example.jwtcoworkingdental.mapper.IMapper;
import com.example.jwtcoworkingdental.security.dto.NuevoUsuario;
import com.example.jwtcoworkingdental.security.entity.Rol;
import com.example.jwtcoworkingdental.security.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class NuevoUsuarioDTOtoUsuario implements IMapper<NuevoUsuario, Usuario> {

    @Override
    public Usuario map(NuevoUsuario in) {

        Usuario usuario = new Usuario();
        usuario.setUserName(in.getUserName());
        usuario.setNombre(in.getNombre());
        usuario.setApellidos(in.getApellidos());
        usuario.setEmail(in.getEmail());
        usuario.setTelefono(in.getTelefono());
        usuario.setPassword(in.getPassword());
        return usuario;
    }


    public Usuario updatemap(NuevoUsuario in, Long id,Set<Rol> rol) {

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setUserName(in.getUserName());
        usuario.setNombre(in.getNombre());
        usuario.setApellidos(in.getApellidos());
        usuario.setEmail(in.getEmail());
        usuario.setTelefono(in.getTelefono());
        usuario.setPassword(in.getPassword());
        usuario.setRoles(rol);
        return usuario;
    }


}
