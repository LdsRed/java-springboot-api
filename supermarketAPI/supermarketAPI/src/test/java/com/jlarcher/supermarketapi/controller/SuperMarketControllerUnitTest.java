package com.jlarcher.supermarketapi.controller;

import com.jlarcher.supermarketapi.controllers.SuperMarketController;
import com.jlarcher.supermarketapi.exceptions.ProductNotFoundException;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.model.SucessResponse;
import com.jlarcher.supermarketapi.services.ProductoService;
import com.jlarcher.supermarketapi.services.ProductoServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SuperMarketControllerUnitTest {

    @Mock private ProductoService productoService;
    @Mock private ProductoServiceValidation productoServiceValidation;

    @InjectMocks private SuperMarketController superMarketController;
    
    @Test
    void test_Crear_Producto_Valido() {
        Producto producto = new Producto(1L, "Jabon", new BigDecimal(5000), "jabon de tocador", 5);

        doNothing().when(productoServiceValidation).validarProducto(producto);
        when(productoService.crearProducto(any(Producto.class))).thenReturn(producto);

        //Producto result = superMarketController.addProducto(producto).getBody();
        ResponseEntity<?> response = superMarketController.createProducto(producto);

        SucessResponse<Producto> responseBody = (SucessResponse<Producto>) response.getBody();
        Producto resultOfConvertionFromResponseBoyd = (Producto) responseBody.getData();


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(producto, resultOfConvertionFromResponseBoyd);
        assertTrue(resultOfConvertionFromResponseBoyd.getPrecio().compareTo(BigDecimal.ZERO) > 0);
        assertFalse(resultOfConvertionFromResponseBoyd.getPrecio().compareTo(BigDecimal.ZERO) < 0);
        assertEquals(producto.getPrecio(),resultOfConvertionFromResponseBoyd.getPrecio());



        verify(productoServiceValidation).validarProducto(producto);
        verify(productoService).crearProducto(producto);
    }

    @Test
    void test_Crear_ProductoInvalido_IllegalArgumentException_SuperMarketController() {
        Producto producto = new Producto(null, null, new BigDecimal(-5000), null, -1);

        doThrow(new IllegalArgumentException("El precio debe ser mayor a cero")).when(productoServiceValidation).validarProducto(producto);
        //Acc & Assertion
        //ResponseEntity<Producto> response = superMarketController.addProducto(producto);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> superMarketController.createProducto(producto));

        assertEquals("El precio debe ser mayor a cero", exception.getMessage());
    }


     @Test
    void test_addProduct_ServiceThrowsException_ThrowsInternalServerError_SuperMarketController(){
        Producto producto = new Producto(1L, "Jabon", new BigDecimal(500), "Jabon intimo", 2);

        when(productoService.crearProducto(any(Producto.class)))
                .thenThrow(new RuntimeException("Error al crear el producto"));

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> superMarketController.createProducto(producto));


        assertEquals("Error al crear el producto", exception.getMessage());
        verify(productoService).crearProducto(any(Producto.class));


    }


    @Test
    void test_listar_todos_los_productos_superMarketController() {
        Producto producto1 = new Producto(1L, "Pan", new BigDecimal(5000), "pan de molde", 6);
        Producto producto2 = new Producto(2L, "Leche", new BigDecimal(5001), "leche entera", 5);
        Producto producto3 = new Producto(3L, "Dulce de Leche", new BigDecimal(2500), "Dulce de leche tradicional", 2);
        when(productoService.listarProductos()).thenReturn(Arrays.asList(producto1,producto2, producto3));

        ResponseEntity<?> response = superMarketController.getAllProductos();
        SucessResponse<Producto> sucessResponse = (SucessResponse<Producto>) response.getBody();
        List<Producto> responseBody = (List<Producto>) sucessResponse.getData(); // <T>

        assertNotNull(response);
        assertEquals(3, responseBody.size());
        assertEquals(producto1.getNombre(), responseBody.get(0).getNombre());
        assertEquals(producto2.getNombre(), responseBody.get(1).getNombre());
        assertEquals(producto3.getNombre(), responseBody.get(2).getNombre());
        assertEquals(producto1.getDescripcion(), responseBody.get(0).getDescripcion());
        assertEquals(producto2.getDescripcion(), responseBody.get(1).getDescripcion());
        assertEquals(producto3.getDescripcion(), responseBody.get(2).getDescripcion());
        assertEquals(producto1.getPrecio(), responseBody.get(0).getPrecio());
        assertEquals(producto2.getPrecio(), responseBody.get(1).getPrecio());
        assertEquals(producto3.getPrecio(), responseBody.get(2).getPrecio());
        assertEquals(producto1.getCantidad(), responseBody.get(0).getCantidad());
        assertEquals(producto2.getCantidad(), responseBody.get(1).getCantidad());
        assertEquals(producto3.getCantidad(), responseBody.get(2).getCantidad());
    }

    @Test
    void test_obtener_producto_porID_superMarketController() {
        Producto producto1 = new Producto(1L, "Pan", new BigDecimal(5000), "pan de molde", 6);
        when(productoService.obtenerPorID(producto1.getId())).thenReturn(producto1);

        ResponseEntity<SucessResponse<Producto>> response = (ResponseEntity<SucessResponse<Producto>>) superMarketController.getProductoById(producto1.getId());
        SucessResponse<Producto> successResponse = (SucessResponse<Producto>) response.getBody();
        Producto responseBody = successResponse.getData();


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(producto1, responseBody);
        assertEquals(producto1.getNombre(), responseBody.getNombre());
        assertEquals(producto1.getDescripcion(), responseBody.getDescripcion());
        assertEquals(producto1.getPrecio(), responseBody.getPrecio());
        assertEquals(producto1.getCantidad(), responseBody.getCantidad());


        verify(productoService).obtenerPorID(producto1.getId());
    }


    @Test
    void test_ObtenerProductoPorID_NotFound_superMarketController() {

        var id = 888L;
        when(productoService.obtenerPorID(id)).thenThrow(new ProductNotFoundException("Producto no encontrado con el ID provisto"));

        Exception exception = assertThrows(ProductNotFoundException.class, () -> superMarketController.getProductoById(id));
        assertEquals("Producto no encontrado con el ID provisto", exception.getMessage());
        verify(productoService).obtenerPorID(id);
    }

    




}
