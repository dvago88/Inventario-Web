package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.storage.Productos;
import com.danielvargas.InventarioWeb.model.storage.Proveedor;
import com.danielvargas.InventarioWeb.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @RequestMapping("/proveedores/{proveedorId}")
    public String proveedor(@PathVariable int proveedorId, Model model) {
        Proveedor prov = proveedorService.obtenerPorCodigo(proveedorId);
        List<Productos> productos = proveedorService.todoslosProductos(prov);
        model.addAttribute("proveedor", prov);
        model.addAttribute("productos", productos);
        return "proveedores/proveedor";
    }

    @RequestMapping("/proveedores")
    public String todosLosProveedores(Model model) {
        List<Proveedor> todos = proveedorService.todosLosProveedores();
        model.addAttribute("proveedor", todos);
        return "proveedores/proveedores";
    }
}
