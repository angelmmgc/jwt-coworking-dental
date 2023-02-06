package com.example.jwtcoworkingdental.service.gabinete;


import com.example.jwtcoworkingdental.dto.gabinete.GabineteInDTO;
import com.example.jwtcoworkingdental.entities.clinica.Clinica;
import com.example.jwtcoworkingdental.entities.gabinete.Gabinete;
import com.example.jwtcoworkingdental.mapper.GabineteInDTOtoGabinete;
import com.example.jwtcoworkingdental.repositories.clinica.ClinicaRepository;
import com.example.jwtcoworkingdental.repositories.gabinete.GabineteRepository;
import com.example.jwtcoworkingdental.repositories.generico.GenericoRepository;
import com.example.jwtcoworkingdental.service.generico.GenericoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class GabineteServiceImpl extends GenericoServiceImpl<Gabinete, Long> implements GabineteService {


     @Autowired
    private final GabineteInDTOtoGabinete mapper;

    @Autowired
    private final ClinicaRepository clinicaRepository;

    @Autowired
    private final GabineteRepository gabineteRepository;


    //constructor

    public GabineteServiceImpl(GenericoRepository<Gabinete, Long> genericoRepository, GabineteInDTOtoGabinete mapper, ClinicaRepository clinicaRepository, GabineteRepository gabineteRepository) {
        super(genericoRepository);
        this.mapper = mapper;
        this.clinicaRepository = clinicaRepository;
        this.gabineteRepository = gabineteRepository;
    }

//  TODO excepciones a insertar string en el id
// TODO  no dejar insertar numeros en los campos tipo y estado



    /**
     *
     * @return lista de clínicas
     */
    public List<Gabinete> getAllGabinetes(){

        return gabineteRepository.findAll();

    }


    /**
     *
     * @return lista de clínicas
     */
    public List<Gabinete> getAllGabinetesByEstado (String estado){

        return gabineteRepository.findByEstadoListGabinetes(estado);

    }


//TODO COMPROBAR SI EL ID DEL GABINETE EXISTE
    public Gabinete getById(Long id) throws Exception {
        Optional<Gabinete> gabinete = gabineteRepository.findById(id);

        if (gabinete.isPresent()){
            return gabinete.get();
        }
        return null;

    }

    /**
     * @param gabineteInDTO parámetro recibido por json.
     * @return objeto Gabinete o objeto = null en caso de no existir.
     */
    public Gabinete saveGabineteDTO(GabineteInDTO gabineteInDTO) {

       //obtenemos la clinica donde vas a guardar los gabinetes
       Optional<Clinica> clinica = clinicaRepository.findById(gabineteInDTO.getClinica_id());
       //guardamos datos en variables
       Integer g =clinica.get().getNumGabinetes()+1;
       Long id = clinica.get().getId();

       //guardamos en objeto Clinica el Optional del Repository
       Clinica clinica1 = clinica.get();
       //set de los valores id y numGabinetes
       clinica1.setId(id);
       clinica1.setNumGabinetes(g);
       //guardamos los valores
       clinicaRepository.save(clinica1);

       //mapeamos object gabinete
       Gabinete gabinete = mapper.map(gabineteInDTO,clinica1);

       //persistimos en base de datos
       return this.gabineteRepository.save(gabinete);

    }

    /**

     * @param gabineteInDTO parámetro recibido por json.
     * @return objeto Gabinete o objeto = null en caso de no existir.
     */
    public Gabinete updateGabineteDTO(GabineteInDTO gabineteInDTO) {


        //datos recibidos de la petición
        Long id = gabineteInDTO.getId();//id del gabinete
        Long id_clinica = gabineteInDTO.getClinica_id();//id de la clinica


        //obtenemos la clínica por su id de postman
        Optional<Clinica> clinica = clinicaRepository.findById(gabineteInDTO.getClinica_id());

        //guardamos el optional en un objeto
        Clinica clinicaObj = clinica.get();
        //creamos un gabinete
        Gabinete gabinete = null;
        //comprobamos que estamos en la clínica
        if (clinicaObj.getId().equals(id_clinica)) {
            System.out.println("id clinica postman " + id_clinica + " id_clinica peristente " + clinicaObj.getId());
            //guardamos el listado de gabinetes de la clinica
            Set<Gabinete> lista = clinicaObj.getGabinetes();
            //recorremos el listado de gabinetes
            for (Gabinete value : lista) {

                if (id.equals(value.getId())) {//compruebo los id de los gabinetes

                    System.out.println("id gabinete postman " + id);
                    System.out.println("id gabinete persistente " + value.getClinica().getGabinetes().iterator().next().getId());
                    //set id de la clínica
                    clinicaObj.setId(id_clinica);
                    //guardamos en el repositorio
                    clinicaRepository.save(clinicaObj);
                    //mapeamos los datos
                    gabinete = mapper.mapUpdate(gabineteInDTO, clinicaObj);
                    gabinete = gabineteRepository.save(gabinete);
                }

            }
        }

        return gabinete;



    }

    @Override
    public Gabinete findByNombre(String nombre) throws Exception {
        return null;
    }
}
