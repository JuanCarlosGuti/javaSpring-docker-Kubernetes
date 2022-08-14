package com.microservicios.cursos.cursos.services;

import com.microservicios.cursos.cursos.models.Usuario;
import com.microservicios.cursos.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoServiceI {
    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso cu);
    void eliminar(Long id);
    void elimnarCursoUsuarioPorId(Long id);
    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
    Optional<Curso> porIdConUsuarios(Long id);





}
