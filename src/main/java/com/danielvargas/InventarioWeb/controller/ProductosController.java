package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.Productos;
import com.danielvargas.InventarioWeb.model.Proveedor;
import com.danielvargas.InventarioWeb.service.ProductosService;
import com.danielvargas.InventarioWeb.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */

@Controller
public class ProductosController {

    //TODO: Evitar field injection creando un costructor
    @Autowired
    ProductosService productosService;

    @Autowired
    ProveedorService proveedorService;

    @RequestMapping("/")
    public String todosLosProductos(Model model) {
        List<Productos> todos = productosService.todosLosProductos();
        model.addAttribute("productos", todos);
        return "productos/principal";
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
            productos.setProveedor(proveedor);
        } else {
            Proveedor pro = proveedorService.actualizarProveedor(proveedor);
            productos.setProveedor(pro);
        }

        if (productosService.obtenerPorNombre(productos.getNombre()) == null) {
            productosService.agregarProducto(productos);
        } else {
            productosService.actualizarProducto(productos);
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Producto exitosamente agregado", FlashMessage.Status.SUCCESS));
        return "redirect:/agregar";
    }

    @RequestMapping(value = "/actualizarMas/{productosId}", method = RequestMethod.POST)
    public String agregarUno(@PathVariable int productosId, RedirectAttributes redirectAttributes) {
        Productos pro = productosService.obtenerPorCodigo(productosId);
        int cantidad = pro.getCantidad();
        cantidad++;
        pro.setCantidad(cantidad);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Producto exitosamente actualizado", FlashMessage.Status.SUCCESS));
        productosService.agregarProducto(pro);

        return "redirect:/";
    }

    // TODO: Implementar este metodo en uno solo con el de agregar 1
    @RequestMapping(value = "/actualizarMenos/{productosId}", method = RequestMethod.POST)
    public String eliminarUno(@PathVariable int productosId, RedirectAttributes redirectAttributes) {

        Productos pro = productosService.obtenerPorCodigo(productosId);
        int cantidad = pro.getCantidad();
        cantidad--;
        pro.setCantidad(cantidad);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Producto exitosamente actualizado", FlashMessage.Status.SUCCESS));
        productosService.agregarProducto(pro);

        return "redirect:/";
    }

    @RequestMapping(value = "/{productosId}", method = RequestMethod.POST)
    public String eliminar(@PathVariable int productosId, RedirectAttributes redirectAttributes) {
        Productos pro = productosService.obtenerPorCodigo(productosId);
        productosService.eliminarProducto(pro);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Producto exitosamente eliminado", FlashMessage.Status.SUCCESS));
        return "redirect:/";
    }

    @RequestMapping("/{productosId}")
    public String producto(@PathVariable int productosId, Model model) {
        Productos pro = productosService.obtenerPorCodigo(productosId);
        model.addAttribute("producto", pro);

        return "productos/producto";
    }
}


