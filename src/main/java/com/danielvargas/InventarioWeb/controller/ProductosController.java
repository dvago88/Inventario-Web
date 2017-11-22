package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.Productos;
import com.danielvargas.InventarioWeb.model.Proveedor;
import com.danielvargas.InventarioWeb.operation.ProcesadorDeStrings;
import com.danielvargas.InventarioWeb.service.ProductosService;
import com.danielvargas.InventarioWeb.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */

//TODO: Evitar field injection creando un costructor
@Controller
public class ProductosController {

    @Autowired
    ProductosService productosService;

    @Autowired
    ProveedorService proveedorService;

   /* @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }*/

    @RequestMapping("/productos")
    public String todosLosProductos(Model model) {
        List<Productos> todos = productosService.todosLosProductos();
        model.addAttribute("productos", todos);
        model.addAttribute("mas", "+");
        model.addAttribute("menos", "-");
        return "productos/main";
    }

    @RequestMapping("/agregar")
    public String formularioAgregar(Model model) {

        if (!model.containsAttribute("productos") && !model.containsAttribute("proveedor")) {
            //agregamos el modelo para poder usar el formulario
            model.addAttribute("proveedor", new Proveedor());
            model.addAttribute("productos", new Productos());
        }

        model.addAttribute("accion", "/agregar");
        model.addAttribute("encabezado", "Agregar");
        model.addAttribute("boton", "Agregar");

        return "formulario";
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public String agregarProducto(@Valid Productos productos, BindingResult result, @Valid Proveedor proveedor, BindingResult result2, RedirectAttributes redirectAttributes) {
        if (result.hasErrors() || result2.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productos", result);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.proveedor", result2);
            redirectAttributes.addFlashAttribute("productos", productos);
            redirectAttributes.addFlashAttribute("proveedor", proveedor);
            return "redirect:/agregar";
        }

        if (proveedorService.obtenerPorNombre(proveedor.getNombreP()) == null) {
            proveedorService.agregarProveedor(proveedor);
            productos.setProveedor(proveedor);//Esto no lo debe hacer el controller
        } else {
            Proveedor pro = proveedorService.actualizarProveedor(proveedor);
            productos.setProveedor(pro);//Esto no lo debe hacer el controller
        }

        if (productosService.obtenerPorNombre(productos.getNombre()) == null) {
            productosService.agregarProducto(productos);
        } else {
            productosService.actualizarProducto(productos, true);
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Producto exitosamente agregado", FlashMessage.Status.SUCCESS));
        return "redirect:/agregar";
    }

    @RequestMapping(value = "/agregarvarios", method = RequestMethod.POST)
    public String agregarVarios(@RequestParam("areaParaIngresarProdutos") String procesador, RedirectAttributes redirectAttributes) {
        ProcesadorDeStrings procesadorDeStrings = new ProcesadorDeStrings(procesador);
        String error = procesadorDeStrings.procesador();

        //esto es moustrosamente malo en cuestion de memoria, pero no se como más hacerlo pues desde la clase
        //ProcesadorDeStrings no me deja llamar al services o al dao
        Iterator<Productos> iterator1 = procesadorDeStrings.getProducts().listIterator();
        Iterator<Proveedor> iterator2 = procesadorDeStrings.getProveedores().listIterator();
        //TODO: Arreglar: si el proveedor ya está genera error
        while (iterator2.hasNext()) {
            Proveedor prov = iterator2.next();
            Productos prod = iterator1.next();
            if (proveedorService.obtenerPorNombre(prov.getNombreP()) == null) {
                proveedorService.agregarProveedor(prov);
            } else {
                proveedorService.actualizarProveedor(prov);
            }
            prod.setProveedor(prov);
            if (productosService.obtenerPorNombre(prod.getNombre()) == null) {
                productosService.agregarProducto(prod);
            } else {
                productosService.actualizarProducto(prod, true);
            }
        }

        if (error.length() > 0 || procesador.equals("")) {
            //Este mensaje se puede pasar desde el procesaroDeStrings
            if (procesador.equals("")) {
                error = "No había nada escrito";
            }
            redirectAttributes.addFlashAttribute("flash", new FlashMessage(error, FlashMessage.Status.FAILURE));
            return "redirect:/agregar";
        }
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Productos agregados exitosamente", FlashMessage.Status.SUCCESS));
        return "redirect:/agregar";
    }

    @RequestMapping("/eliminar")
    public String paginaEliminar() {
        return "eliminar";
    }
}




