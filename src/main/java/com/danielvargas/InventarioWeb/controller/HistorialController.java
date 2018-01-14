package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.storage.Productos;
import com.danielvargas.InventarioWeb.service.HistorialService;
import com.danielvargas.InventarioWeb.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HistorialController {

    @Autowired
    HistorialService historialService;
    @Autowired
    ProductosService productosService;

    @RequestMapping("/historico")
//    @RequestMapping("/historico/{fecha}")
    public String getAllThisDate(@RequestParam("startDate") String fecha, Model model) {
        List<Productos> productos = historialService.obtenerProductosPorFecha(fecha);
        model.addAttribute("productos", productos);
        model.addAttribute("mas", "+");
        model.addAttribute("menos", "-");
        model.addAttribute("fecha", fecha);
        return "historial/historial";
    }
}
