package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {


    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void crearProducto() {

        Producto producto1 = new Producto(1L, "Jabon", 5000, "jabon de tocador", 5);

        when(productoRepository.save(producto1)).thenReturn(producto1);

        Producto result = productoService.crearProducto(producto1);

        assertNotNull(result);
        assertEquals(producto1.getNombre(), result.getNombre());
        assertEquals(producto1.getPrecio(), result.getPrecio());
        assertEquals(producto1.getDescripcion(), result.getDescripcion());
        assertEquals(producto1.getCantidad(), result.getCantidad());
        verify(productoRepository).save(producto1);


    }

    @Test
    void listarProductos() {

        Producto producto1 = new Producto(1L, "Pan", 5000, "pan de molde", 5);
        Producto producto2 = new Producto(2L, "Leche", 5001, "leche entera", 5);
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto1,producto2));

        List<Producto> result = productoService.listarProductos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(producto1.getNombre(), result.get(0).getNombre());
        assertEquals(producto2.getNombre(), result.get(1).getNombre());
        assertEquals(producto1.getPrecio(), result.get(0).getPrecio());
        assertEquals(producto2.getPrecio(), result.get(1).getPrecio());
        assertEquals(producto1.getDescripcion(), result.get(0).getDescripcion());
        assertEquals(producto2.getDescripcion(), result.get(1).getDescripcion());
        assertEquals(producto1.getCantidad(), result.get(0).getCantidad());
        assertEquals(producto2.getCantidad(), result.get(1).getCantidad());
    }

    @Test
    void obtenerPorID() {

        Producto producto = new Producto(1L, "Queso", 3000, "queso rallado", 2);
        when(productoRepository.findById(producto.getId())).thenReturn(Optional.of(producto));


        Producto result = productoService.obtenerPorID(producto.getId()).orElseThrow(() -> new RuntimeException("No se encuentra el producto"));

        assertNotNull(result);
        assertEquals(producto.getNombre(), result.getNombre());
        assertEquals(producto.getPrecio(), result.getPrecio());
        assertEquals(producto.getDescripcion(), result.getDescripcion());
        assertEquals(producto.getCantidad(), result.getCantidad());
        verify(productoRepository, times(1)).findById(producto.getId());
    }

    @Test
    void actualizarProducto() {

        Producto producto1 = new Producto(1L, "Carpetas", 5000, "Carpetas de colores", 5);
        Producto productoActualizado = new Producto( 1L, "Carpetas Inc", 4000, "Carpetas Inc clasificada de colores", 2);

        when(productoRepository.save(productoActualizado)).thenReturn(productoActualizado);


        Producto result = productoService.actualizarProducto(1L, productoActualizado);

        assertNotNull(result);
        assertEquals(productoActualizado.getNombre(), result.getNombre());
        assertEquals(productoActualizado.getDescripcion(), result.getDescripcion());
        assertEquals(productoActualizado.getCantidad(), result.getCantidad());
        assertEquals(productoActualizado.getId(), result.getId());
        verify(productoRepository, times(1)).save(productoActualizado);

    }

    @Test
    void eliminarProducto() {
        Producto producto = new Producto(1L, "Carpetas Inc", 4000, "Carpetas Inc clasificada de colores", 2);

        productoService.eliminarProducto(producto.getId());
        verify(productoRepository, times(1)).delete(producto);
    }
}