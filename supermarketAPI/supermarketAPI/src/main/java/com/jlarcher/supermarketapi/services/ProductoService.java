package com.jlarcher.supermarketapi.services;

import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {


    @Autowired
    private ProductoRepository productoRepository;


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
                    producto.setNombre(producto1.getNombre());
                    producto.setPrecio(producto1.getPrecio());
                    producto.setDescripcion(producto1.getDescripcion());
                    return productoRepository.save(producto);
                }).orElseThrow(() -> new RuntimeException("No se encontro el producto"));
    }


    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

}
