package com.jlarcher.supermarketapi.controller;


import com.jlarcher.supermarketapi.controllers.SuperMarketController;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.services.ProductoService;
import com.jlarcher.supermarketapi.services.ProductoServiceValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SuperMarketServiceUnitTest {

    @Mock
    private ProductoService superMarketService;
    private SuperMarketController superMarketController;
    private ProductoServiceValidation serviceValidation;

    @BeforeEach
    public void setup() {
        superMarketController = new SuperMarketController(superMarketService, serviceValidation);
    }

    @Test
    void testUpdateProduct() throws Exception{
        Producto producto = new Producto(1L,"Queso", 3000, "queso rallado", 2);
        Producto productoActualizado = new Producto(1L,"Morcilla", 2000, "Morcilla Paladini", 3);


        when(superMarketService.actualizarProducto(1L, productoActualizado)).thenReturn(productoActualizado);

        var result = superMarketService.actualizarProducto(1L, productoActualizado);

        assertNotNull(result);
        assertEquals("Morcilla", result.getNombre());
        assertEquals(productoActualizado.getDescripcion(), result.getDescripcion());
        assertEquals(productoActualizado.getPrecio(), result.getPrecio());
        assertEquals(productoActualizado.getCantidad(), result.getCantidad());

    }

}
