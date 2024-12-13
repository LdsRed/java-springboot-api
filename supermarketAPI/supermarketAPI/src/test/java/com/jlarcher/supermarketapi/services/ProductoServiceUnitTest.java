package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.exceptions.ProductNotFoundException;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceUnitTest {


    @Mock private ProductoRepository productoRepository;
    @InjectMocks private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void test_Crear_Producto_Valido() {

        Producto producto = new Producto(1L, "Jabon", new BigDecimal(5000), "jabon de tocador", 5);

        when(productoRepository.save(producto)).thenReturn(producto);

        Producto result = productoService.crearProducto(producto);

        assertNotNull(result);
        assertInstanceOf(Producto.class, result);
        assertEquals(producto.getNombre(), result.getNombre());
        assertEquals(producto.getPrecio(), result.getPrecio());
        assertEquals(producto.getDescripcion(), result.getDescripcion());
        assertEquals(producto.getCantidad(), result.getCantidad());
        //Verify the interaction with the service
        verify(productoRepository).save(producto);
    }


    /*
    @Test
    void test_Crear_ProductoInvalido_IllegalArgumentException(){
        Producto producto = new Producto(null, null, new BigDecimal(-5000), null, -1);
        //Acc & Assertion
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productoService.crearProducto(producto));

        assertEquals("El precio debe ser mayor a cero", exception.getMessage());

        //verify the interactions
        //verify(productoRepository).save(producto);
        verifyNoMoreInteractions(productoRepository);
    }

    */

    @Test
    void test_addProduct_ServiceThrowsException_ThrowsInternalServerError(){

        //Arrange
        Producto producto = new Producto(1L, "Jabon", new BigDecimal(500), "Jabon intimo", 2);

        when(productoRepository.save(any(Producto.class)))
                .thenThrow(new RuntimeException("Error al crear el producto"));

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> productoService.crearProducto(producto));


        assertEquals("Error al crear el producto", exception.getMessage());
        verify(productoRepository).save(any(Producto.class));
    }

    @Test
    void test_listar_todos_los_productos() {

        Producto producto1 = new Producto(1L, "Pan", new BigDecimal(5000), "pan de molde", 5);
        Producto producto2 = new Producto(2L, "Leche", new BigDecimal(5001), "leche entera", 5);
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

        verify(productoRepository).findAll();
    }

    @Test
    void test_obtener_producto_porID() {

        Producto producto = new Producto(1L, "Queso", new BigDecimal(3000), "queso rallado", 2);
        when(productoRepository.findById(producto.getId())).thenReturn(Optional.of(producto));


        Producto result = productoService.obtenerPorID(producto.getId());

        assertNotNull(result);
        assertEquals(producto.getNombre(), result.getNombre());
        assertEquals(producto.getPrecio(), result.getPrecio());
        assertEquals(producto.getDescripcion(), result.getDescripcion());
        assertEquals(producto.getCantidad(), result.getCantidad());
        verify(productoRepository, times(1)).findById(producto.getId());
    }

    @Test
    void test_ObtenerProductoPorID_NotFound() {

        var id = 888L;

        when(productoRepository.findById(888L)).thenReturn(Optional.empty());


        Exception exception = assertThrows(ProductNotFoundException.class, () -> productoService.obtenerPorID(id));

        assertEquals("Producto no encontrado con ID: " + id , exception.getMessage());
        verify(productoRepository).findById(id);
    }




    @Test
    void testActualizarProducto() {

        //Arrange
        Producto productoExistente = new Producto(1L, "Carpetas", new BigDecimal(5000), "Carpetas de colores", 5);
        Producto productoActualizado = new Producto( 1L, "Carpetas Inc", new BigDecimal(4000), "Carpetas Inc clasificada de colores", 2);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoActualizado);


        //Act
        Producto result = productoService.actualizarProducto(1L, productoActualizado);

        //Assert
        assertNotNull(result);
        assertEquals(productoExistente.getId(), productoActualizado.getId());
        assertNotEquals("Morcilla", result.getNombre());
        assertNotEquals(2000, result.getPrecio().intValue());


        // Verify
        verify(productoRepository).save(productoActualizado);
    }

    @Test
    void test_Eliminar_ProductoExistente() {
        Producto producto = new Producto(1L, "Carpetas Inc", new BigDecimal(4000), "Carpetas Inc clasificada de colores", 2);
        when(productoRepository.existsById(producto.getId())).thenReturn(true);

        productoService.eliminarProducto(producto.getId());
        verify(productoRepository).deleteById(producto.getId());
    }


    @Test
    void test_Elminar_ProductoNoExistente_EntityNotFoundException(){

        Long id = 1L;
        when(productoRepository.existsById(id)).thenReturn(false);

        Exception exception = assertThrows(ProductNotFoundException.class, () -> productoService.eliminarProducto(id));

        assertEquals("El Producto con el ID " + id + " no fue encontrado", exception.getMessage());
        verify(productoRepository).existsById(id);

    }
}