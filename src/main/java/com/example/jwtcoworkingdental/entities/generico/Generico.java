package com.example.jwtcoworkingdental.entities.generico;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * clase base para tener un "id" generico
 * para ello utilizamos la anotacion  "@MappedSuperclass"
 */
@MappedSuperclass
public class Generico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //CONSTRUCT
    public Generico() {
    }


    public Generico(Long id) {
        this.id = id;
    }

    //GETTER AND SETTER
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
