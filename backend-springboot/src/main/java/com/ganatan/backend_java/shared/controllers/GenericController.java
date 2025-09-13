package com.ganatan.backend_java.shared.controllers;

import com.ganatan.backend_java.shared.models.BaseEntity;
import com.ganatan.backend_java.shared.services.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public abstract class GenericController<T extends BaseEntity<ID>, ID extends Serializable> {

    private final GenericService<T, ID> service;

    public GenericController(GenericService<T, ID> service) {
        this.service = service;
    }

    @GetMapping
    public List<T> getAll() {
        return service.getAllItems();
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable ID id) {
        return service.getItemById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public T create(@RequestBody T item) {
        return service.createItem(item);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable ID id, @RequestBody T item) {
        return service.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable ID id) {
        service.deleteItem(id);
    }
}
