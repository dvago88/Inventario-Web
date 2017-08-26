package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.Productos;
import com.danielvargas.InventarioWeb.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */

@Controller
public class ProductosController {

    @Autowired
    ProductosService productosService;

    @RequestMapping("/")
    public String todosLosProductos(Model model) {
        List<Productos> todos = productosService.todosLosProductos();
        model.addAttribute("productos", todos);
        return "principal";
    }

    @RequestMapping("/actualizar")
    public String actualizar(Model model) {
        return "formulario";
    }

    @RequestMapping("/agregar")
    public String agregar(Model model) {
        return "formulario";
    }

    @RequestMapping("/eliminar")
    public String eliminar(Model model) {
        return "formulario";
    }
}
