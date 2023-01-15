package com.example.jwtcoworkingdental.security.repository;


import com.example.jwtcoworkingdental.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByUserName(String username);

    Optional<Usuario> findUsuarioByEmail(String email);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);


}
