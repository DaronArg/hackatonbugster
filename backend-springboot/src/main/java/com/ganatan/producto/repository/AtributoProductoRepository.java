package com.ganatan.producto.repository;

import com.ganatan.producto.model.AtributoProducto;
import com.ganatan.producto.model.AtributoProductoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtributoProductoRepository extends JpaRepository<AtributoProducto, AtributoProductoId> {}
