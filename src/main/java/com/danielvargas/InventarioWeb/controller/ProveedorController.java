package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.Proveedor;
import com.danielvargas.InventarioWeb.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @RequestMapping("/proveedores/{proveedorId}")
    public String proveedor(@PathVariable int proveedorId, Model model) {
        Proveedor prov = proveedorService.obtenerPorCodigo(proveedorId);
        model.addAttribute("proveedor", prov);
        return "proveedor";
    }

    @RequestMapping("/proveedores")
    public String todosLosProveedores() {
        return "proveedores";
    }
}
