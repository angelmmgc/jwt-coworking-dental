package com.example.jwtcoworkingdental.security.repository;


import com.example.jwtcoworkingdental.security.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findUsuarioByUserName(String username);

    Optional<Usuario> findUsuarioByEmail(String email);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);

    /**
     * query para borrar un usuario segun id, solo puede ser admin
     * @param user_id
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM usuarios WHERE id = :user_id", nativeQuery = true)
    void deleteByUserName(@Param("user_id") Long user_id);


}
