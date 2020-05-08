package com.mservice.generic.services;


import java.util.Optional;

public interface IGenericService<E> {

    Iterable<E> findAll();
    Optional<E> findById(Long id);
    E save(E entity);
    void deleteById(Long id);
}
