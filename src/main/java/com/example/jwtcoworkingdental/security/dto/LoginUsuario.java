package com.example.jwtcoworkingdental.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginUsuario {



    @NotBlank
    private String username;

    @NotBlank
    private String password;


    //constructor

    public LoginUsuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //getter and setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
