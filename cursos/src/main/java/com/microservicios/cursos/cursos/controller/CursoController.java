package com.microservicios.cursos.cursos.controller;

import com.microservicios.cursos.cursos.models.Usuario;
import com.microservicios.cursos.cursos.models.entity.Curso;
import com.microservicios.cursos.cursos.services.CursoServiceI;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {

    @Autowired
    private CursoServiceI service;

    @GetMapping
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable long id){
        Optional<Curso> o = service.porIdConUsuarios(id);
        if (o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crearCurso(@Valid @RequestBody Curso curso, BindingResult result){
        if(result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curso));
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestBody Curso cur,BindingResult result ){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Curso> o = service.porId(cur.getId());
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cur));
        }
            return ResponseEntity.notFound().build();


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable Long id){
        Optional<Curso> o = service.porId(id);

        if (o.isPresent()){
            service.eliminar(id);
           return ResponseEntity.ok("se ha eliminado el curso con id = "+id);
        }
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado el curso con Id = "+id);
    }
    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario,@PathVariable Long cursoId){
        Optional<Usuario> o ;
                try{
                  o = service.asignarUsuario(usuario, cursoId);
                }
                catch (FeignException e){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).
                            body(Collections.singletonMap("Error", "No existe el usuario " +
                                    "por el id o error en la conexión: "+e.getMessage()));
                }

        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario,@PathVariable Long cursoId){
        Optional<Usuario> o ;
        try{
         o =   service.crearUsuario(usuario, cursoId);
        }
        catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Error", "No se puedo" +
                            "crear al Usuario "+e.getMessage()));
        }

        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario,@PathVariable Long cursoId){
        Optional<Usuario> o;
        try{
         o = service.eliminarUsuario(usuario, cursoId);
        }
        catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Error", "No existe el usuario " +
                            "por el id o error en la conexión: "+e.getMessage()));
        }

        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        service.elimnarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }



    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(),"El campo "+error.getField()+" "+error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
