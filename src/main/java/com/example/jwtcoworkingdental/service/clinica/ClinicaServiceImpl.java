package com.example.jwtcoworkingdental.service.clinica;


import com.example.jwtcoworkingdental.dto.clinica.ClinicaInDTO;
import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.mapper.ClinicaInDTOtoClinica;
import com.example.jwtcoworkingdental.repositories.clinica.ClinicaRepository;
import com.example.jwtcoworkingdental.repositories.generico.GenericoRepository;
import com.example.jwtcoworkingdental.security.entity.Usuario;
import com.example.jwtcoworkingdental.security.repository.UsuarioRepository;
import com.example.jwtcoworkingdental.service.generico.GenericoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClinicaServiceImpl extends GenericoServiceImpl<Clinica, Long> implements ClinicaService {


    @Autowired
    private final ClinicaInDTOtoClinica mapper;

    @Autowired
    private final ClinicaRepository clinicaRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;


    public ClinicaServiceImpl(GenericoRepository<Clinica, Long> genericoRepository, ClinicaInDTOtoClinica mapper, ClinicaRepository clinicaRepository, UsuarioRepository usuarioRepository) {
        super(genericoRepository);
        this.mapper = mapper;
        this.clinicaRepository = clinicaRepository;
        this.usuarioRepository = usuarioRepository;
    }


    /**
     * funion guarda un usario si es admin
     * @param clinicaInDTO
     * @param nombre_usuario
     * @return
     * @throws Exception
     */
    public Clinica saveClinicaDTO(String nombre_usuario,ClinicaInDTO clinicaInDTO) throws Exception {

        //obtenemos el usuario admin por usename
        Optional<Usuario>usuario = usuarioRepository.findUsuarioByUserName(nombre_usuario);
        //guardamos id
        Long id = usuario.get().getId();
        //creamos un usuario donde guardamos id
        Usuario user = new Usuario();
        user.setId(id);
        //creamos usuario de tipo set
        Set<Usuario> usuarios = new HashSet<>();
        //añadimos id admin
        usuarios.add(user);
        //añadimos el admin al dto
        clinicaInDTO.setAdministra(usuarios);
        //transformamos los datos
        Clinica clinica = mapper.map(clinicaInDTO);

        //devolvemos la clinica con todos los datos
        return this.clinicaRepository.save(clinica);


    }

    public Clinica updateClinicaDTO(String nombre_usuario,ClinicaInDTO clinicaInDTO) {

        Long idClinicaUpdate = clinicaInDTO.getId();//id pasado de postman

        Optional<Clinica> cLinicaOptional = clinicaRepository.findById(idClinicaUpdate);

        if (idClinicaUpdate.equals(cLinicaOptional.get().getId())) {

            //obtenemos el usuario admin por username
            Optional<Usuario>usuario = usuarioRepository.findUsuarioByUserName(nombre_usuario);
            //guardamos el id
            Long id = usuario.get().getId();
            //creamos un usuario donde gardamos id
            Usuario user = new Usuario();
            user.setId(id);
            //creamos usuario de tipo set
            Set<Usuario> usuarios = new HashSet<>();
            //añadimos id admin
            usuarios.add(user);
            //añadimos el admin al dto
            clinicaInDTO.setAdministra(usuarios);

            Clinica clinica = mapper.mapUdate(clinicaInDTO);

            //devolvemos la clinica con todos los datos
            return this.clinicaRepository.save(clinica);

        }else{
            return null;
        }


    }

    /**
     *
     * @return lista de clínicas
     */
    public List<Clinica> getAllClinicas(){

        return clinicaRepository.findAll();

    }


    @Override
    public Clinica findByNombre(String nombre) throws Exception {

        System.out.println(nombre);
        return clinicaRepository.findByNombre(nombre).get();
    }
}
