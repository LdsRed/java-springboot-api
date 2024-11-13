package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {



    private ProductoRepository productoRepository;


    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public List<Producto> listarProductos(){
        return productoRepository.findAll();
    }
    public Optional<Producto> obtenerPorID(Long id){
        return productoRepository.findById(id);
    }



    public Producto actualizarProducto(Long id, Producto producto) {
        return productoRepository.findById(id)
                .map( producto1 -> {
                    producto1.setNombre(producto.getNombre());
                    producto1.setPrecio(producto.getPrecio());
                    producto1.setDescripcion(producto.getDescripcion());
                    producto1.setCantidad(producto.getCantidad());
                    return productoRepository.save(producto1);
                }).orElseThrow(() -> new RuntimeException("No se encontro el producto"));
    }


    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

}
