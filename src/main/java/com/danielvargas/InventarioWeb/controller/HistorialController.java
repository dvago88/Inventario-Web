package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.storage.Productos;
import com.danielvargas.InventarioWeb.service.HistorialService;
import com.danielvargas.InventarioWeb.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
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

    @RequestMapping("/historico/{productoId}")
    public String historial(@PathVariable int productoId,
                            @RequestParam("startDate") String inicio,
                            @RequestParam("endDate") String fin, Model model) {
//        TODO: manjear mejor ese null
//        TODO: NO esta funcionando revisar!!1
        try {
            Productos prodInicial;
            Productos prodFinal;
            int vendidos;
            int comprados;
            double costoComprados;
            double gananciaBruta;
            double gananciaNeta;
            System.out.println("fecha: " + fin);

            if (inicio.equals(" ") && fin.equals(" ")) {
                return "redirect:/{productoId}";
            } else if (inicio.equals(" ") && !fin.equals(" ")) {
//                No me gusta esta solución pero funciona....
                inicio = "01/01/2000";
                prodInicial = historialService.obtenerProductoPorFecha(productoId, inicio);
                prodFinal = historialService.obtenerProductoPorFecha(productoId, fin);
//                inicio="";
            } else {
                prodInicial = historialService.obtenerProductoPorFecha(productoId, inicio);
                prodFinal = historialService.obtenerProductoPorFecha(productoId, fin);
            }

            vendidos = prodFinal.getCantidadVendido() - prodInicial.getCantidadVendido();
            comprados = prodFinal.getCantidadComprado() - prodInicial.getCantidadComprado();
            costoComprados = prodFinal.getPrecioEntrada() * vendidos;
            gananciaBruta = vendidos * prodFinal.getPrecio();
            gananciaNeta = gananciaBruta - costoComprados;

            model.addAttribute("producto", prodFinal);
            model.addAttribute("inicio", inicio);
            model.addAttribute("fin", fin);
            model.addAttribute("vendidos", vendidos);
            model.addAttribute("comprados", comprados);
            model.addAttribute("ganancia", gananciaNeta);
            model.addAttribute("ventasPromedio", "interminadas aun");
        } catch (NullPointerException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            return "redirect:/{productoId}";
        }
        return "historial/historico";
    }
}
