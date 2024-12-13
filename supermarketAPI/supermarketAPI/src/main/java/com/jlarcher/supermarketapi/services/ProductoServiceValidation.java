package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.exceptions.ProductoExistenteException;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class ProductoServiceValidation {

    private ProductoRepository productoRepository;

    public ProductoServiceValidation(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }



    public Producto validarProducto(Producto producto){

        StringBuilder errors = new StringBuilder();

        if(producto.getPrecio().compareTo(BigDecimal.ZERO) < 0){
            errors.append("El precio debe ser mayor a cero.");
        }
        if(producto.getNombre() == null || producto.getNombre().isEmpty()){
            errors.append("El nombre es obligatorio.");
        }
        if (producto.getCantidad() <= 0 ) {
            errors.append("La cantidad debe ser mayor a cero.");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString().trim());
        }
        return producto;
    }

    public void validarIDProductoAeliminar(Long id){

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID es obligatorio y debe ser mayor a cero.");
        }
        productoRepository.findById(id).ifPresentOrElse(
                producto -> log.info("Producto encontrado para eliiminar: {}", producto),
                () -> {
                    throw new IllegalArgumentException("No se ha encontrado el producto con el ID: " + id);
                }
        );
    }

    public void validarIdProducto(Long id){

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID es obligatorio y debe ser mayor a cero.");
        }
        productoRepository.findById(id).ifPresentOrElse(
                producto -> log.info("Producto encontrado: {}", producto),
                () -> {
                    throw new ProductoExistenteException("No se ha encontrado el producto con el ID: " + id);
                }
        );
    }

}
