package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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


    public Optional<Producto> obtenerPorID(Long id){

        try{
            return productoRepository.findById(id);
        } catch (EntityNotFoundException e) {
            log.error("No se encontro el producto con el ID: " + id, e);
            return Optional.empty();
        }
    }



    public Producto actualizarProducto(Long id, Producto producto) {
        return productoRepository.findById(id)
                .map( producto1 -> {
                    producto1.setNombre(producto.getNombre());
                    producto1.setPrecio(producto.getPrecio());
                    producto1.setDescripcion(producto.getDescripcion());
                    producto1.setCantidad(producto.getCantidad());
                    return productoRepository.save(producto1);
                }).orElseThrow(() -> new EntityNotFoundException("No se encontro el producto"));
    }


    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)){
            log.error("El producto no existe");
            throw new EntityNotFoundException("El Producto con el ID " + id + " no fue encontrado");
        }
        productoRepository.deleteById(id);
    }

}
