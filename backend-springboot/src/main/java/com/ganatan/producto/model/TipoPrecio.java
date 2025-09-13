package com.ganatan.producto.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TipoPrecio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tenantId;
}
