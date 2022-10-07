package com.wilma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;

/**
 * Provides common CRUD implementation
 *
 * @param <T> The object type implementing this class
 * @param <K> The primary key data type of the calling object
 * @param <R> The JPA repository associated with the calling object
 */
public abstract class CrudOpsImpl<T, K, R extends JpaRepository<T, K>> {

    @Autowired
    R repo;

    public T add(T t) {
        return repo.save(t);
    }

    public Collection<T> addAll(Collection<T> ts) {
        return repo.saveAll(ts);
    }

    public T findById(K id) {
        return repo.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Collection<T> findAll() {
        return repo.findAll();
    }

    public T update(T t) {
        return repo.save(t);
    }

    public HttpStatus deleteById(K id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return HttpStatus.NO_CONTENT;//Deleted
        }
        return HttpStatus.BAD_REQUEST;// Not deleted
    }

}
