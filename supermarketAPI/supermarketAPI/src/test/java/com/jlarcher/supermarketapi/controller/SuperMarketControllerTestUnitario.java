package com.jlarcher.supermarketapi.controller;


import com.jlarcher.supermarketapi.controllers.SuperMarketController;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuperMarketControllerTestUnitario {

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

        when(superMarketService.listarProductos()).thenReturn(Arrays.asList(producto1,producto2));

        var result = superMarketController.getAllProductos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(producto1.getNombre(), result.get(0).getNombre());
        assertEquals(producto1.getDescripcion(), result.get(0).getDescripcion());
        assertEquals(producto1.getCantidad(), result.get(0).getCantidad());
        assertEquals(producto1.getPrecio(), result.get(0).getPrecio());

        assertEquals(producto2.getNombre(), result.get(1).getNombre());
        assertEquals(producto2.getDescripcion(), result.get(1).getDescripcion());
        assertEquals(producto2.getPrecio(), result.get(1).getPrecio());
        assertEquals(producto2.getCantidad(), result.get(1).getCantidad());
    }


    @Test
    void getProductoById() throws Exception {

        Producto producto = new Producto(1L,"Queso", 3000, "queso rallado", 2);
        Producto producto2 = new Producto(3L, "Mayonesa", 5000, "Mayonesa Natura", 5);
        Producto producto3 = new Producto(5L, "Tarta", 1200, "Tarta el Nono", 6);


        when(superMarketService.obtenerPorID(3L)).thenReturn(Optional.of(producto2));
        when(superMarketService.obtenerPorID(1L)).thenReturn(Optional.of(producto));
        when(superMarketService.obtenerPorID(5L)).thenReturn(Optional.of(producto3));

        var result = superMarketController.getProductoById(1L);
        var result2 = superMarketController.getProductoById(3L);
        var result3 = superMarketController.getProductoById(5L);


        assertNotNull(result);
        assertNotNull(result2);
        assertNotNull(result3);


        //Assertions for product
        assertEquals(producto.getNombre(), result.getBody().getNombre());
        assertEquals(producto.getDescripcion(), result.getBody().getDescripcion());
        assertEquals(producto.getId(), result.getBody().getId());

        //Assertions for product2
        assertEquals(producto2.getNombre(), result2.getBody().getNombre());
        assertEquals(producto2.getDescripcion(), result2.getBody().getDescripcion());
        assertEquals(producto2.getId(), result2.getBody().getId());

        //Assertions for product3
        assertEquals(producto3.getNombre(), result3.getBody().getNombre());
        assertEquals(producto3.getDescripcion(), result3.getBody().getDescripcion());
        assertEquals(producto3.getId(), result3.getBody().getId());

    }


    @Test
    void testUpdateProduct() throws Exception{
        Producto producto = new Producto(1L,"Queso", 3000, "queso rallado", 2);
        Producto productoActualizado = new Producto(1L,"Morcilla", 2000, "Morcilla Paladini", 2);

    }

}
