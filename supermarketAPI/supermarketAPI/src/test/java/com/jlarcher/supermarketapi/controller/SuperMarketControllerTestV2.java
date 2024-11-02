package com.jlarcher.supermarketapi.controller;


import com.jlarcher.supermarketapi.controllers.SuperMarketController;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuperMarketControllerTestV2 {

    @Mock
    private ProductoService superMarketService;
    private SuperMarketController superMarketController;

    @BeforeEach
    public void setup() {
        superMarketController = new SuperMarketController(superMarketService);
    }


    @Test
    void addProducto2() throws Exception {


        Producto producto = new Producto(1L, "Jabon", 5000, "jabon de tocador", 5);
        when(superMarketService.crearProducto(producto)).thenReturn(producto);

        var result = superMarketController.addProducto(producto).getBody();
        assertNotNull(result);
        assertEquals("Pepito", result.getNombre());
        assertEquals(producto.getDescripcion(), result.getDescripcion());
        assertEquals(producto.getPrecio(), result.getPrecio());
        assertEquals(producto.getCantidad(), result.getCantidad());
        verify(superMarketController).addProducto(producto);
    }


    @Test
    void testGetAllProductos() throws Exception {
        Producto producto1 = new Producto(1L,"Jabon", 5000, "jabon de tocador", 5);
        Producto producto2 = new Producto(2L,"Shampoo", 5001, "Shampoo para cabello rizado", 5);


    }


    @Test
    void getProductoById() throws Exception {

        Producto producto = new Producto(1L,"Queso", 3000, "queso rallado", 2);


    }


    @Test
    void testUpdateProduct() throws Exception{
        Producto producto = new Producto(1L,"Queso", 3000, "queso rallado", 2);
        Producto productoActualizado = new Producto(1L,"Morcilla", 2000, "Morcilla Paladini", 2);

    }

}
