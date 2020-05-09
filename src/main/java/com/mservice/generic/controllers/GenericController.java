package com.mservice.generic.controllers;

import com.mservice.generic.services.IGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GenericController<E, S extends IGenericService<E>> {

    @Autowired
    protected S service;

    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/pagina")
    public ResponseEntity<?> listar(Pageable pageable){
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id){

        Optional<E> dbEntity = service.findById(id);

        if (dbEntity.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(dbEntity.get());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result){

        if(result.hasErrors()){
            return this.validar(result);
        }

        E dbEntity = service.save(entity);

        return ResponseEntity.status(HttpStatus.CREATED).body(dbEntity);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> validar(BindingResult result){

        Map<String, Object> errors = new HashMap<>();

        result.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            });

        return ResponseEntity.badRequest().body(errors);
    }

}













