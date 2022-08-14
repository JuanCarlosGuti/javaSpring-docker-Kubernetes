package com.microservicios.microServicios.service;

import com.microservicios.microServicios.client.CursoClienteRest;
import com.microservicios.microServicios.entity.Usuario;
import com.microservicios.microServicios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioImplement implements IUsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CursoClienteRest clienteRest;


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return repository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> buscarporId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
        clienteRest.eliminarCursoUsuarioPorId(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario>  listarPorIds(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {
        return repository.porEmail(email);
    }

    @Override
    public boolean existeEmail(String email) {
        return repository.existsByEmail(email);
    }
}
