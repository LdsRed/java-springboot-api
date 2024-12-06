package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductoServiceValidation {

    private ProductoRepository productoRepository;

    public void validarProducto(Producto producto){

        if(producto.getPrecio() <= 0){
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if(producto.getNombre() == null){
            throw  new IllegalArgumentException("El nombre es obligatorio");
        }
        if (producto.getCantidad() <= 0) {
            throw  new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
    }

    public void validarIDProductoAeliminar(Long id){

        if (id == null) {
            throw new IllegalArgumentException("El ID es obligatorio");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID invalido, debe ser mayor a 0");
        }
        if (productoRepository.findById(id).isEmpty()){
            log.info("No se ha encontrado el producto que quiere eliminar");
        }
    }

}
