package com.microservicios.microServicios.service;

import com.microservicios.microServicios.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> listar();
    Optional<Usuario> buscarporId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    List<Usuario> listarPorIds(Iterable<Long> ids);
    Optional<Usuario> porEmail(String email);
    boolean existeEmail(String email);

}
