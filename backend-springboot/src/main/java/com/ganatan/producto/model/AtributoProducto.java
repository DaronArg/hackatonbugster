package com.ganatan.producto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(AtributoProductoId.class)
public class AtributoProducto {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    @JsonIgnore // Evita recursividad en la serialización
    private Producto producto;

    @Id
    @Column(length = 100)
    private String nombreAtributo;

    @Column(nullable = false)
    private String valorAtributo;
}
