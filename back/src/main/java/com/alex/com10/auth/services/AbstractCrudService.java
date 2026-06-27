package com.alex.com10.auth.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractCrudService<T, ID, R extends JpaRepository<T, ID>> implements CrudService<T, ID> {

    protected final R repository;

    protected AbstractCrudService(R repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return repository.findById(id);
    }

    @Override
    @Transactional
    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T update(ID id, T entity) {
        if (id == null || entity == null) {
            throw new IllegalArgumentException("ID and Entity cannot be null");
        }
        if (!repository.existsById(id)) {
            throw new jakarta.persistence.EntityNotFoundException("Entity not found with ID: " + id);
        }
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!repository.existsById(id)) {
            throw new jakarta.persistence.EntityNotFoundException("Entity not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
