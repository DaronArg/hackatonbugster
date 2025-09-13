package com.ganatan.producto.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Long rubroId;
    private Long tipoPrecioId;
    private Long unidadId;
    private Map<String, String> atributos;
}
