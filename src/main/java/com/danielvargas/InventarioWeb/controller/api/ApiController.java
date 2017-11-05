package com.danielvargas.InventarioWeb.controller.api;

import com.danielvargas.InventarioWeb.model.Productos;
import com.danielvargas.InventarioWeb.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    ProductosService productosService;

    @GetMapping("/api/productos")
    public List getProductos() {
        return productosService.todosLosProductos();
    }

    @GetMapping("/api/productos/{id}")
    public Productos getProductoPorId(@PathVariable int id) {
        return productosService.obtenerPorCodigo(id);
    }

}
