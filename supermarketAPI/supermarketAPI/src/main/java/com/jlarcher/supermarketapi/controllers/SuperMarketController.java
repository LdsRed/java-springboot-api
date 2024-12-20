package com.jlarcher.supermarketapi.controllers;


import com.jlarcher.supermarketapi.model.ErrorResponse;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.model.SucessResponse;
import com.jlarcher.supermarketapi.services.ProductoService;

import com.jlarcher.supermarketapi.services.ProductoServiceValidation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<?> createProducto(@Valid @RequestBody Producto producto) {
        productoServiceValidation.validarProducto(producto);
        Producto nuevoProducto = productoService.crearProducto(producto);
        SucessResponse<Producto> response = new SucessResponse<>(nuevoProducto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<?> getAllProductos() {
        List<Producto> productos = productoService.listarProductos();

        if (productos.isEmpty()){
            ErrorResponse errorResponse = new ErrorResponse(List.of("No se encontraron productos"), "No se encontraron productos");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);
        }
        SucessResponse<List<Producto>> response = new SucessResponse<>(productos); // <T>
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.obtenerPorID(id);
        SucessResponse<Producto> response = new SucessResponse<>(producto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        productoServiceValidation.validarProducto(producto);
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        SucessResponse<Producto> response = new SucessResponse<>(productoActualizado);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {
        productoServiceValidation.validarIdProducto(id);
        productoService.eliminarProducto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Producto eliminado con exito");
    }
}
