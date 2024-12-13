package com.jlarcher.supermarketapi.controllers;


import com.jlarcher.supermarketapi.exceptions.ProductNotFoundException;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.services.ProductoService;

import com.jlarcher.supermarketapi.services.ProductoServiceValidation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class SuperMarketController {

    private final ProductoService productoService;
    private final ProductoServiceValidation productoServiceValidation;

    public SuperMarketController(ProductoService productoService, ProductoServiceValidation productoServiceValidation) {
        this.productoService = productoService;
        this.productoServiceValidation = productoServiceValidation;
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody Producto producto) {
        productoServiceValidation.validarProducto(producto);
        Producto nuevoProducto = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }


    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.listarProductos();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Long id) {
        try {
            Producto producto = productoService.obtenerPorID(id);
            return new ResponseEntity<>(producto, HttpStatus.OK); // Retorna el producto con status 200 OK
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND); // Retorna el mensaje de error con status 404 Not Found
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, productoServiceValidation.validarProducto(producto));
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> deleteProducto(@PathVariable Long id) {
        productoServiceValidation.validarIDProductoAeliminar(id);
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
