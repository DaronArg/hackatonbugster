package com.ganatan.backend_java.shared.services;

import com.ganatan.backend_java.multitenancy.TenantContext;
import com.ganatan.backend_java.shared.exceptions.NotFoundException;
import com.ganatan.backend_java.shared.models.BaseEntity;
import com.ganatan.backend_java.shared.repositories.GenericRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

public abstract class GenericService<T extends BaseEntity<ID>, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    @Autowired
    private EntityManager entityManager;

    public GenericService(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> getAllItems() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("tenantFilter").setParameter("tenantId", TenantContext.getTenantId());
        List<T> items = repository.findAll();
        session.disableFilter("tenantFilter");
        return items;
    }

    public T getItemById(ID id) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("tenantFilter").setParameter("tenantId", TenantContext.getTenantId());
        T item = repository.findById(id).orElseThrow(() -> new NotFoundException("Entity not found with id: " + id));
        session.disableFilter("tenantFilter");
        return item;
    }

    public T createItem(T item) {
        return repository.save(item);
    }

    public T updateItem(ID id, T item) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Entity not found with id: " + id);
        }
        item.setId(id);
        return repository.save(item);
    }

    public void deleteItem(ID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Entity not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
