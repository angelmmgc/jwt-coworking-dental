package com.example.jwtcoworkingdental.security.controller;



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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

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
            ********+FUNCIONES PARA NUEVO USUARIO ( register )***********
*/
    /**
     * Método de registro
     * @param nuevoUsuario Parámetro pasado en el cuerpo de la petición.
     * @param bindingResult parámetro para comprobar que la entrada de datos es correcta.
     * @return status ok y mensaje.
     */
    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){

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

            if (rolService.getByRolNombre(RolNombre.ROLE_ADMIN).isPresent()){
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
            }


        }else {

            if (rolService.getByRolNombre(RolNombre.ROLE_USER).isPresent()){
                roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());

            }
        }


        usuario.setRoles(roles);
        usuarioService.save(usuario);
        System.out.println(usuario.getId());

        return new ResponseEntity<>(new Mensaje("usuario guardado"),HttpStatus.CREATED);
    }

    /*
     *********************************************************************************
        FUNCIONES PARA TODOS LOS USUARIOS TANTO ADMIN COMO CLIENTES REGISTRADOS
        (login,getUserByUsername,getUserByEmail,updateUserByUsername,updateUserByEmail)
     *********************************************************************************
     */


    /**
     *
     * Método de login
     * @param loginUsuario . Parámetro pasado en el cuerpo de la petición.
     * @param bindingResult .
     * @return status ok
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
     * Función obtiene el usuario por username
     * @param username . Parámetro pasado por la url.
     * @return Response status OK y usuario en formato json
     *         Response status NOT_FOUND y mensaje en caso de no encontrar al usuario.
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){


        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByNombreUsuario(username));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente mas tarde.\"}");

        }
    }


    /**
     * Función obtiene el usuario por email.
     * @param email . Parámetro pasado por url.
     * @return Response status OK y usuario en formato json
     *         Response status NOT_FOUND y mensaje en caso de no encontrar al usuario.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){

        try{

            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByEmailUsuario(email));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente mas tarde.\"}");
        }

    }

    /**
     * Función update del usuario por username para ADMIN Y USER.
     * @param nuevoUsuario . Parámetro pasado en el cuerpo de la petición.
     * @return Response status ok y el usuario actualizado en caso correcto.
     *         Response status NOT_FOUND y mensaje en caso de no encontrar al usuario.
     */
    @PutMapping("/username")
    public ResponseEntity<?> updateUserByUsername(@Valid @RequestBody NuevoUsuario nuevoUsuario){

        System.out.println("actualizando por nombre");
        String username = nuevoUsuario.getUserName();
        try {

            // if (usuarioService.existsByNombreUsuario(username)){

            if (usuarioService.getByNombreUsuario(username).isPresent()){

                Long id = usuarioService.getByNombreUsuario(username).get().getId();
                Set<Rol> role = usuarioService.getByNombreUsuario(username).get().getRoles();
                System.out.println("correcto" + role);

                Usuario usuario = mapper.updatemap(nuevoUsuario,id,role);
                usuarioService.save(usuario);
            }

            //}
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByNombreUsuario(username));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al actualizar. Por favor intente mas tarde.\"}");

        }
    }

    /**
     * Función update del usuario por email para ADMIN Y USER.
     * @param nuevoUsuario . Parámetro pasado en el cuerpo de la petición.
     * @return Response status ok y el usuario actualizado en caso correcto.
     *         Response status NOT_FOUND y mensaje en caso de no encontrar al usuario.
     */

    @PutMapping("/email")
    public ResponseEntity<?> updateUserByEmail(@Valid @RequestBody NuevoUsuario nuevoUsuario){

        System.out.println("actualizando por email");
        String email = nuevoUsuario.getEmail();
        try {

            if (usuarioService.getByEmailUsuario(email).isPresent()){

                Long id = usuarioService.getByEmailUsuario(email).get().getId();
                Set<Rol> role = usuarioService.getByEmailUsuario(email).get().getRoles();
                System.out.println("correcto" + role);

                Usuario usuario = mapper.updatemap(nuevoUsuario,id,role);
                usuarioService.save(usuario);
            }

            //}
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getByEmailUsuario(email));

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al actualizar. Por favor intente mas tarde.\"}");

        }

    }



    /*
    ********+********************************************************************
               FUNCIONES SOLO ADMIN (getAll, deleteByUsername)
    ******************************************************************************
     */


    /**
     * Función obtiene todos los usuarios solo para administradores.
     * @return Response status OK y listado con todos los usuarios en formato json.
     *         Response status NOT_FOUND y mensaje en caso de no encontrar al usuario.
     */
    @GetMapping("/getall")
    public ResponseEntity<?> getAll(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente mas tarde.\"}");

        }
    }

    /**
     * Función eliminar un usuario por username.
     * @param username .Parámetro pasado por url.
     * @return Response status OK y true en caso de eliminación correcta.
     *         Response status NOT_FOUND y mensaje en caso de no encontrar al usuario.
     *
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteByUsername(@PathVariable String username){

        System.out.println("intentando borrar un elemento " + username);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.delete(username));
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error al intentar borrar un elemento.  Por favor intente mas tarde.\"}");

        }
    }








}
