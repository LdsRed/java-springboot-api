package com.jlarcher.supermarketapi;

import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.repository.ProductoRepository;
import com.jlarcher.supermarketapi.services.ProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    @Test
    void crearProducto() {
        Producto producto = new Producto();
        producto.setNombre("Producto 1");
        producto.setPrecio(10.0);
        producto.setDescripcion("test simple");
        producto.setCantidad(4);

        Mockito.when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.crearProducto(producto);
        assertEquals("Producto 1", resultado.getNombre());
    }
}
