package com.example.jwtcoworkingdental.security.controller;


import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.security.dto.JwtDto;
import com.example.jwtcoworkingdental.security.dto.LoginUsuario;
import com.example.jwtcoworkingdental.security.dto.Mensaje;
import com.example.jwtcoworkingdental.security.dto.NuevoUsuario;
import com.example.jwtcoworkingdental.security.entity.Rol;
import com.example.jwtcoworkingdental.security.entity.Usuario;
import com.example.jwtcoworkingdental.security.enums.RolNombre;
import com.example.jwtcoworkingdental.security.jwt.JwtProvider;
import com.example.jwtcoworkingdental.security.mapper.NuevoUsuarioDTOtoUsuario;
import com.example.jwtcoworkingdental.security.service.RolService;
import com.example.jwtcoworkingdental.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

//NO DISPONGO DE LOS METODOS GENERICOS
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private final NuevoUsuarioDTOtoUsuario mapper;

    public AuthController(NuevoUsuarioDTOtoUsuario mapper) {
        this.mapper = mapper;
    }

/*
            ********+FUNCIONES PARA TODOS LOS USUARIOS TANTO ADMIN COMO CLIENTE (REGISTRO Y LOGIN)***********
 */
    /**
     * metodo de registro
     * @param nuevoUsuario parámetro recibido de la consulta
     * @param bindingResult parámetro para comprobar que la entrada de datos es correcta
     * @return
     */
    @PostMapping
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){

        if (bindingResult.hasErrors())
            return new ResponseEntity<>( bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
           // return new ResponseEntity<>(new Mensaje("campos mal puestos o email invalido"), HttpStatus.BAD_REQUEST);


        if (usuarioService.existsByNombreUsuario(nuevoUsuario.getUserName()))
            return new ResponseEntity<>(new Mensaje("ese nombre ya existe"),HttpStatus.BAD_REQUEST);

        if (usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity<>(new Mensaje("ese email ya existe"),HttpStatus.BAD_REQUEST);

      //convertimos el nuevo usuario a usuario
        Usuario usuario = mapper.map(nuevoUsuario);

        Set<Rol> roles = new HashSet<>();


        //añadimos rol admin si el email tiene el dominio admin.edu
        if(nuevoUsuario.getEmail().split("@")[1].equals("admin.edu")){
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        }else {
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
        }


        usuario.setRoles(roles);
        usuarioService.save(usuario);
        System.out.println(usuario.getId());

        return new ResponseEntity<>(new Mensaje("usuario guardado"),HttpStatus.CREATED);
    }


    /**
     * metodo de login
     * @param loginUsuario
     * @param bindingResult
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){

        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUsuario.getUsername(),loginUsuario.getPassword()
                )
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        JwtDto jwtDto = new JwtDto(jwt,userDetails.getUsername(),userDetails.getAuthorities());
        return new ResponseEntity<>(jwtDto,HttpStatus.OK);
    }

    /**
     * función obtiene el usuario por username
     * @param username
     * @return
     */
    @GetMapping("/username")
    public ResponseEntity<?> getOne(@Param("username") String username){


        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByNombreUsuario(username));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente mas tarde.\"}");

        }
    }


    @GetMapping("/email")
    public ResponseEntity<?> getByEmail(@Param("email") String email){

        try{

            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByEmailUsuario(email));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente mas tarde.\"}");
        }

    }



    /*
     ********+ FUNCIONES SOLO ADMIN ***********
     * TENER UN LISTADO DE TODOS LOS USUARIOS
     * ELIMINAR POR ID O NOMBRE
     * TIENE SENTIDO QUE UN ADMIN CREE UN USUARIO USER?
     */




    /**
     * función obtiene todos los usuarios solo para administradores
     * @return
     */
    @GetMapping("/getall")
    public ResponseEntity<?> saludo_admin(){


        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente mas tarde.\"}");

        }
    }



    /**
     * función update el usuario por username para ADMIN Y USER
     * @param nuevoUsuario
     * @return
     */
    @PutMapping("/username")
    public ResponseEntity<?> updateOne(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){

        String username = nuevoUsuario.getUserName();
        try {

            if (usuarioService.existsByNombreUsuario(username)){
                Long id = usuarioService.getByNombreUsuario(username).get().getId();
                System.out.println("correcto");

                Usuario usuario = mapper.updatemap(nuevoUsuario,id);
                usuarioService.save(usuario);

            }
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByNombreUsuario(username));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al actualizar. Por favor intente mas tarde.\"}");

        }
    }

    /*
     ********+ FUNCIONES SOLO USER ***********

     * BUSCAR POR USERNAME O EMAIL
     * UPDATE DE SUS DATOS BUSCANDO POR USERNAME O EMAIL
     * ELIMINAR POR ID O NOMBRE?

     */



}
