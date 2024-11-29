package com.jlarcher.supermarketapi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.jlarcher.supermarketapi.controllers.SuperMarketController;
import com.jlarcher.supermarketapi.model.Producto;
import com.jlarcher.supermarketapi.services.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(SuperMarketController.class)
public class TestSuperMarketController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addProducto() throws Exception {

        Producto producto = new Producto(1L, "Jabon", 5000, "jabon de tocador", 5);

        when(productoService.crearProducto(any(Producto.class))).thenReturn(producto);


        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Jabon\",\"precio\":5000,\"descripcion\":\"jabon de tocador\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Jabon"))
                .andExpect( jsonPath("$.precio").value(5000))
                .andExpect(jsonPath("$.descripcion").value("jabon de tocador"))
                .andExpect( jsonPath("$.cantidad").value(5));
    }


    @Test
    void testGetAllProductos() throws Exception {
        Producto producto1 = new Producto(1L,"Jabon", 5000, "jabon de tocador", 5);
        Producto producto2 = new Producto(2L,"Shampoo", 5001, "Shampoo para cabello rizado", 5);

        when(productoService.listarProductos()).thenReturn(Arrays.asList(producto1,producto2));


        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Jabon"))
                .andExpect( jsonPath("$[1].nombre").value("Shampoo"))
                .andExpect( jsonPath("$.length()").value(2));
    }


    @Test
    void testetProductoById() throws Exception {

        Producto producto = new Producto(1L,"Queso", 3000, "queso rallado", 2);

        when(productoService.obtenerPorID(producto.getId())).thenReturn(Optional.of(producto));


        mockMvc.perform(get("/api/productos/{id}", producto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Queso"))
                .andExpect( jsonPath("$.precio").value(3000))
                .andExpect( jsonPath("$.descripcion").value("queso rallado"))
                .andExpect( jsonPath("$.cantidad").value(2));
    }


   /* @Test
    void testUpdateProduct() throws Exception{

        //Arrange
        Producto producto = new Producto(1L,"Queso", 3000, "queso rallado", 2);
        Producto productoActualizado = new Producto(1L,"Morcilla", 2000, "Morcilla Paladini", 2);



        when(productoService.actualizarProducto(1L, productoActualizado)).thenReturn(productoActualizado);

        String updatedProductAsString = objectMapper.writeValueAsString(productoActualizado);

        System.out.println("updated product Json: " + updatedProductAsString);
        MvcResult result =mockMvc.perform(put("/api/productos/{id}", producto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductAsString))
                        .andExpect(status().isOk())
                        .andReturn();

        String jsonResult = result.getResponse().getContentAsString();

        System.out.println("Json result: " + jsonResult);
        Producto productAsResult = objectMapper.readValue(jsonResult, Producto.class);

        // Validate the updated product
        Assertions.assertNotNull(productAsResult);
        Assertions.assertEquals(productoActualizado.getNombre(), productAsResult.getNombre());
        verify(productoService).actualizarProducto(producto.getId(), productoActualizado);
    } */

    @Test
    void testGetProductByIdNotFound() throws Exception{
        Long nonExistenID = 99L;

        when(productoService.obtenerPorID(nonExistenID)).thenReturn(Optional.empty());


        mockMvc.perform(get("/api/productos/{id}", nonExistenID))
                .andExpect(status().isNotFound());

    }


    @Test
    void testDeleteProducto() throws Exception{

        Long productId = 1L;


        mockMvc.perform(delete("/api/productos/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productoService).eliminarProducto(productId);
    }



    @Test
    void testAddProductoInvalidInput() throws Exception {

        Producto productoInvalido = new Producto(null, "Producto Invalido", -100, "Descripcion", 2 );

        String invalidProductJson = objectMapper.writeValueAsString(productoInvalido);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidProductJson))
                .andExpect(status().isBadRequest());
    }

}
