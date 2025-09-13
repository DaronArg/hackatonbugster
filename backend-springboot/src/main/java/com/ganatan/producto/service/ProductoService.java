package com.ganatan.producto.service;

import com.ganatan.producto.dto.ProductoDTO;
import com.ganatan.producto.exception.ResourceNotFoundException;
import com.ganatan.producto.model.*;
import com.ganatan.producto.repository.*;
import com.ganatan.producto.util.TenantContext;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final RubroRepository rubroRepository;
    private final TipoPrecioRepository tipoPrecioRepository;
    private final UnidadRepository unidadRepository;

    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarProductosPorFiltros(Map<String, String> filtros) {
        Specification<Producto> spec = createSpecification(filtros);
        return productoRepository.findAll(spec).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductoDTO findById(Long id) {
        Producto producto = productoRepository.findByIdAndTenantId(id, TenantContext.getCurrentTenant())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return convertToDto(producto);
    }

    @Transactional
    public ProductoDTO create(ProductoDTO productoDTO) {
        Producto producto = convertToEntity(productoDTO);
        producto.setTenantId(TenantContext.getCurrentTenant());
        Producto savedProducto = productoRepository.save(producto);
        return convertToDto(savedProducto);
    }

    @Transactional
    public ProductoDTO update(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findByIdAndTenantId(id, TenantContext.getCurrentTenant())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        updateEntityFromDto(producto, productoDTO);
        Producto updatedProducto = productoRepository.save(producto);
        return convertToDto(updatedProducto);
    }

    @Transactional
    public void delete(Long id) {
        Producto producto = productoRepository.findByIdAndTenantId(id, TenantContext.getCurrentTenant())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        productoRepository.delete(producto);
    }

    private Specification<Producto> createSpecification(Map<String, String> filtros) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro mandatorio por Tenant
            predicates.add(criteriaBuilder.equal(root.get("tenantId"), TenantContext.getCurrentTenant()));

            filtros.forEach((key, value) -> {
                if (value == null || value.trim().isEmpty()) return;

                switch (key) {
                    case "nombre":
                        predicates.add(criteriaBuilder.like(root.get("nombre"), "%" + value + "%"));
                        break;
                    case "rubroId":
                        predicates.add(criteriaBuilder.equal(root.get("rubro").get("id"), Long.parseLong(value)));
                        break;
                    case "precioMenorA":
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("precio"), value));
                        break;
                    case "precioMayorA":
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("precio"), value));
                        break;
                    default:
                        // Filtro dinámico para atributos personalizados
                        if (key.startsWith("attr_")) {
                            String attrName = key.substring(5);
                            Join<Producto, AtributoProducto> atributoJoin = root.join("atributos");
                            predicates.add(criteriaBuilder.equal(atributoJoin.get("nombreAtributo"), attrName));
                            predicates.add(criteriaBuilder.like(atributoJoin.get("valorAtributo"), "%" + value + "%"));
                        }
                        break;
                }
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private ProductoDTO convertToDto(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        if (producto.getRubro() != null) {
            dto.setRubroId(producto.getRubro().getId());
        }
        if (producto.getTipoPrecio() != null) {
            dto.setTipoPrecioId(producto.getTipoPrecio().getId());
        }
        if (producto.getUnidad() != null) {
            dto.setUnidadId(producto.getUnidad().getId());
        }
        dto.setAtributos(producto.getAtributos().stream()
                .collect(Collectors.toMap(AtributoProducto::getNombreAtributo, AtributoProducto::getValorAtributo)));
        return dto;
    }

    private Producto convertToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        updateEntityFromDto(producto, dto);
        return producto;
    }

    private void updateEntityFromDto(Producto producto, ProductoDTO dto) {
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());

        // Asignar entidades relacionadas
        if (dto.getRubroId() != null) {
            Rubro rubro = rubroRepository.findById(dto.getRubroId())
                    .orElseThrow(() -> new ResourceNotFoundException("Rubro no encontrado"));
            producto.setRubro(rubro);
        }
        if (dto.getTipoPrecioId() != null) {
            TipoPrecio tipoPrecio = tipoPrecioRepository.findById(dto.getTipoPrecioId())
                    .orElseThrow(() -> new ResourceNotFoundException("TipoPrecio no encontrado"));
            producto.setTipoPrecio(tipoPrecio);
        }
        if (dto.getUnidadId() != null) {
            Unidad unidad = unidadRepository.findById(dto.getUnidadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada"));
            producto.setUnidad(unidad);
        }

        // Sincronizar atributos
        producto.getAtributos().clear();
        if (dto.getAtributos() != null) {
            dto.getAtributos().forEach((key, value) -> {
                AtributoProducto atributo = new AtributoProducto();
                atributo.setProducto(producto);
                atributo.setNombreAtributo(key);
                atributo.setValorAtributo(value);
                producto.getAtributos().add(atributo);
            });
        }
    }
}
