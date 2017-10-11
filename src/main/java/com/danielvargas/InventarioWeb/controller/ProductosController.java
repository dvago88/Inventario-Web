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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */

@Controller
public class ProductosController {

    //TODO: Evitar field injection creando un costructor
    //TODO: Crear ProductoController y mover algunos metodos para allá
    @Autowired
    ProductosService productosService;

    @Autowired
    ProveedorService proveedorService;


    @RequestMapping("/")
    public String todosLosProductos(Model model) {
        List<Productos> todos = productosService.todosLosProductos();
        model.addAttribute("productos", todos);
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
            productos.setProveedor(proveedor);
        } else {
            Proveedor pro = proveedorService.actualizarProveedor(proveedor);
            productos.setProveedor(pro);
        }

        if (productosService.obtenerPorNombre(productos.getNombre()) == null) {
            productosService.agregarProducto(productos);
        } else {
            productosService.actualizarProducto(productos, true);
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
        if (cantidad < 0) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("No puede haber menos de 0 productos, si deseas eliminar el producto has click en Borrar", FlashMessage.Status.FAILURE));
        } else {
            pro.setCantidad(cantidad);
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Producto exitosamente actualizado", FlashMessage.Status.SUCCESS));
            productosService.agregarProducto(pro);
        }
        return "redirect:/";
    }

    //TODO: Implementar este metodo
    @RequestMapping(value = "/agregarvarios", method = RequestMethod.POST)
    public String agregarVarios(@RequestParam("areaParaIngresarProdutos") String procesador, RedirectAttributes redirectAttributes) {
        ProcesadorDeStrings procesadorDeStrings = new ProcesadorDeStrings(procesador);
        String error = procesadorDeStrings.procesador();

        //esto es moustrosamente malo en cuestion de memoria, pero no se como más hacerlo pues desde la clase
        //ProcesadorDeStrings no me deja llamar al services o al dao
        Iterator<Productos> iterator1 = procesadorDeStrings.getProducts().listIterator();
        Iterator<Proveedor> iterator2 = procesadorDeStrings.getProveedores().listIterator();

        while (iterator2.hasNext()){
            Proveedor prov=iterator2.next();
            Productos prod=iterator1.next();
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

        if (error.length() > 0) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage(error, FlashMessage.Status.FAILURE));
            return "redirect:/agregar";
        }
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Productos agregados exitosamente", FlashMessage.Status.SUCCESS));
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

    @RequestMapping(value = "/editar/{productosId}", method = RequestMethod.POST)
    public String editarProducto(@PathVariable int productosId, RedirectAttributes redirectAttributes, Productos productos, BindingResult result) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productos", result);
            redirectAttributes.addFlashAttribute("productos", productos);
            return "redirect:/editar/{productosId}";
        }
        Productos prod = productosService.obtenerPorCodigo(productosId);
//        Proveedor prov = prod.getProveedor();

        //TODO: Pasar todos estos ifs para un metodo en el service
        if (!productos.getNombre().equals(prod.getNombre())) {
            prod.setNombre(productos.getNombre());
        }
        if (productos.getCantidad() != prod.getCantidad()) {
            prod.setCantidad(productos.getCantidad());
        }
        if (productos.getPrecio() != prod.getPrecio()) {
            prod.setPrecio(productos.getPrecio());
        }
        if (productos.getPrecioEntrada() != prod.getPrecioEntrada()) {
            prod.setPrecioEntrada(productos.getPrecioEntrada());
        }
        if (!productos.getDescripcion().equals(prod.getDescripcion())) {
            prod.setDescripcion(productos.getDescripcion());
        }
        productosService.actualizarProducto(prod, false);

        return "redirect:/{productosId}";
    }
}


