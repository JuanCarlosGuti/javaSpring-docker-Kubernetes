package com.microservicios.microServicios.repository;

import com.microservicios.microServicios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    @Query("select u from Usuario u where u.email=?1")
    Optional<Usuario> porEmail(String email);
    boolean existsByEmail(String Email);
}
