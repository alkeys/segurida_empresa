package com.alex.com10.auth.controllers;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alex.com10.auth.services.CrudService;

public abstract class AbstractCrudController<T, ID, DTO, S extends CrudService<T, ID>> {

    protected final S service;

    protected AbstractCrudController(S service) {
        this.service = service;
    }

    protected abstract DTO toDto(T entity);
    protected abstract T toEntity(DTO dto);

    @GetMapping
    public ResponseEntity<List<DTO>> findAll() {
        List<DTO> dtos = service.findAll().stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> findById(@PathVariable ID id) {
        return service.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DTO> save(@Valid @RequestBody DTO dto) {
        T entity = toEntity(dto);
        T saved = service.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@PathVariable ID id, @Valid @RequestBody DTO dto) {
        T entity = toEntity(dto);
        T updated = service.update(id, entity);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
