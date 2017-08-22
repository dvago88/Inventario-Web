package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Daniel on 20/08/2017
 */

@Controller
public class ProductosController {

    @Autowired
    ProductosService productosService;

    @RequestMapping("/")
    public String todosLosProductos(){

        return "principal";
    }
}
