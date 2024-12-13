package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductoServiceValidationUnitTest {
    @Mock
    private ProductoRepository productoRepository;

    private ProductoServiceValidation productoServiceValidation;

    @BeforeEach
    void setUp() {
        productoServiceValidation = new ProductoServiceValidation(productoRepository);
    }



    @Test
    void test_ValidarProducto_PrecioInvalido_IllegalArgumentException() {
        Producto productoInvalido = new Producto(1L, "Producto Invalido", new BigDecimal(-100), "Descripcion", 2 );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productoServiceValidation.validarProducto(productoInvalido));

        System.out.println(exception.getMessage()); 
        assertEquals("El precio debe ser mayor a cero.", exception.getMessage());


    }

    @Test
    void test_ValidarProducto_NombreNull_OVacio_IllegalArgumentException() {
        Producto productoInvalido = new Producto(1L, null, new BigDecimal(5000), "Descripcion", 2 );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productoServiceValidation.validarProducto(productoInvalido));

        System.out.println(exception.getMessage());
        assertEquals("El nombre es obligatorio.", exception.getMessage());


    }

    @Test
    void test_ValidarProducto_CantidadInvalida_IllegalArgumentException() {
        Producto productoInvalido = new Producto(1L, "Producto Invalido", new BigDecimal(1000), "Descripcion", -2 );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productoServiceValidation.validarProducto(productoInvalido));

        System.out.println(exception.getMessage());
        assertEquals("La cantidad debe ser mayor a cero.", exception.getMessage());


    }

    @Test
    void test_ValidarProducto_variosCamposInvalidos_IllegalArgumentException() {
        Producto productoInvalido = new Producto(1L, null, new BigDecimal(-100), "Descripcion", -2 );

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> productoServiceValidation.validarProducto(productoInvalido));

        System.out.println(exception.getMessage());
        assertEquals("El precio debe ser mayor a cero.El nombre es obligatorio.La cantidad debe ser mayor a cero.",
                exception.getMessage());


    }

}
