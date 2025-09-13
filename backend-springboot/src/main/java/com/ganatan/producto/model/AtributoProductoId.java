package com.ganatan.producto.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtributoProductoId implements Serializable {
    private Long producto;
    private String nombreAtributo;
}
