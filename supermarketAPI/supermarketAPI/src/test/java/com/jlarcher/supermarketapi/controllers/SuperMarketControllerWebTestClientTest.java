package com.jlarcher.supermarketapi.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlarcher.supermarketapi.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


@SpringBootTest(webEnvironment = RANDOM_PORT)
class SuperMarketControllerWebTestClientTest {

    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createProducto() throws JsonProcessingException {

        //Given a new product is created
        Producto nuevoProducto = new Producto(0L, "Jabon", new BigDecimal(7000), "Jabon para lavar ropa", 10);

        //When the product is created
        client.post()
                .uri("http://localhost:8080/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(nuevoProducto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.nombre").value(is("Jabon"))
                .jsonPath("$.data.precio").value(is(7000))
                .jsonPath("$.data.descripcion").value(is("Jabon para lavar ropa"))
                .jsonPath("$.data.cantidad").value(is(10));

    }

    @Test
    void getAllProductos() {
        client.get()
                .uri("http://localhost:8080/api/productos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.nombre").isEqualTo("Jabon");
    }

    @Test
    void getProductoById() {
        client.get()
                .uri("http://localhost:8080/api/productos/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.nombre").isEqualTo("Gigabyte")
                .jsonPath("$.data.precio").isEqualTo(5000)
                .jsonPath("$.data.descripcion").isEqualTo("Gigabyte RTX 5050")
                .jsonPath("$.data.cantidad").isEqualTo(5);
    }

    @Test
    void updateProducto() {
    }

    @Test
    void deleteProducto() {

        client.delete()
                .uri("http://localhost:8080/api/productos/1")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody();
    }

     @Test
    void delete_product_notfound() {

        client.delete()
                .uri("http://localhost:8080/api/productos/1")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody();
    }
}