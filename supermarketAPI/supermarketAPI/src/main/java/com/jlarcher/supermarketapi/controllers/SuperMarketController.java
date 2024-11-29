package com.jlarcher.supermarketapi.controllers;


import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.services.ProductoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class SuperMarketController {

    private final ProductoService productoService;

    public SuperMarketController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<Producto> addProducto(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }


    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.listarProductos();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return productoService.obtenerPorID(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@Positive @PathVariable Long id, @Valid@RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Producto> deleteProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
