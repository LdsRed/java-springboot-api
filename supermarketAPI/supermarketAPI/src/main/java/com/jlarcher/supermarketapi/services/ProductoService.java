package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.exceptions.ProductNotFoundException;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;


    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto crearProducto(Producto producto) {

        return productoRepository.findById(producto.getId())
                .map(productoExistente -> {
                   productoExistente.setCantidad(productoExistente.getCantidad() + producto.getCantidad());
                   return productoRepository.save(productoExistente);
                })
                .orElseGet(() -> productoRepository.save(producto));
    }

    public List<Producto> listarProductos(){
        return productoRepository.findAll();
    }


    public Producto obtenerPorID(Long id){
            return productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));
    }



    public Producto actualizarProducto(Long id, Producto producto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("No se encontro el producto"));
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setCantidad(producto.getCantidad());
        productoExistente.setDescripcion(producto.getDescripcion());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setId(producto.getId());

        return productoRepository.save(productoExistente);

    }


    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)){
            log.error("El producto no existe");
            throw new ProductNotFoundException("El Producto con el ID " + id + " no fue encontrado");
        }
        log.info("Producto eliminado.");
        productoRepository.deleteById(id);
    }

}
