package com.microservicios.microServicios.controller;

import com.microservicios.microServicios.entity.Usuario;
import com.microservicios.microServicios.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/index")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;


    @GetMapping
    public List<Usuario> listarUsuarios(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = service.buscarporId(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(service.buscarporId(id).get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?>  creaUsuario(@Valid @RequestBody Usuario usuario, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        if (!usuario.getEmail().isEmpty() && service.existeEmail(usuario.getEmail())){
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("error: ", "ya existe  un usuario con ese correo electronico"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }

        Optional<Usuario> usuario1 = service.buscarporId(id);
        if (usuario1.isPresent()){
            Usuario usuarioDb = usuario1.get();


            if (!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) &&
                    service.porEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("error: ", "ya existe  un usuario con ese correo electronico"));
            }
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPasword(usuario.getPasword());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarPorId(@PathVariable Long id){
        Optional<Usuario> usuario =  service.buscarporId(id);
        if (usuario.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-curso")
    public ResponseEntity<?> listarPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.listarPorIds(ids));
    }


    private ResponseEntity<Map<String, String>> validar(@RequestParam BindingResult result) {

        Map<String, String> erros = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            erros.put(err.getField(),"El campo "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(erros);
    }



}
