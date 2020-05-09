package com.mservice.generic.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGenericService<E> {

    Page<E> findAll(Pageable pageable);
    Iterable<E> findAll();
    Optional<E> findById(Long id);
    E save(E entity);
    void deleteById(Long id);
}
