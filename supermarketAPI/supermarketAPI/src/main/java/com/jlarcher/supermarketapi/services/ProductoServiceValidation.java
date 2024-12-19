package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.model.Producto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductoServiceValidation {



    public void validarProducto(Producto producto){

        List<String> errors = new ArrayList<>();

        if(producto.getPrecio().compareTo(BigDecimal.ZERO) < 0){
            errors.add("El precio debe ser mayor a cero.");
        }
        if(producto.getNombre() == null || producto.getNombre().isEmpty()){
            errors.add("El nombre es obligatorio.");
        }
        if (producto.getCantidad() <= 0 ) {
            errors.add("La cantidad debe ser mayor a cero.");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }


    public void validarIdProducto(Long id){

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID es obligatorio y debe ser mayor a cero.");
        }
    }

}
