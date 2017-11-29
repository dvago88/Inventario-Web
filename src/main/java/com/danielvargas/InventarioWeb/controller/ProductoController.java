package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.Productos;
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

@Controller
public class ProductoController {

    @Autowired
    ProductosService productosService;

    @Autowired
    ProveedorService proveedorService;

    @RequestMapping("/{productosId}")
    public String producto(@PathVariable int productosId, Model model) {
        Productos pro = productosService.obtenerPorCodigo(productosId);
        int sieteDias = productosService.vendidosPorSemana(pro);
        int treintaDias = productosService.vendidoPorMes(pro);
        int ano = productosService.vendidoPorAno(pro);
        int dia = productosService.vendidosPorDia(pro);
        model.addAttribute("producto", pro);
        model.addAttribute("semana", sieteDias);
        model.addAttribute("mes", treintaDias);
        model.addAttribute("ano", ano);
        model.addAttribute("dia", dia);
        return "productos/producto";
    }

    @RequestMapping(value = "/actualizar/{productosId}", method = RequestMethod.POST)
    public String agregarUno(@PathVariable int productosId, RedirectAttributes redirectAttributes, @RequestParam(value = "action") String action, @RequestParam("masomenos") String masOMenos) {
        if (masOMenos.length() == 0) {
            masOMenos = "1";
        }
        int cantidad = Integer.parseInt(masOMenos);
        Productos pro = productosService.obtenerPorCodigo(productosId);
        //este if devulve si el numero restante del producto sería negativo o no.
        if (!productosService.cantidadProducto(pro, action, cantidad)) {
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("No puede haber menos de 0 productos, si deseas eliminar el producto has click en Borrar", FlashMessage.Status.FAILURE));
        } else {
            if (action.equals("menos")) {
                productosService.numeroDeVentas(pro, cantidad);
            }
            redirectAttributes.addFlashAttribute("flash", new FlashMessage("Producto exitosamente actualizado", FlashMessage.Status.SUCCESS));
            productosService.agregarProducto(pro);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/editar/{productosId}", method = RequestMethod.POST)
    public String editarProducto(@PathVariable int productosId, @Valid Productos productos, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productos", result);
            redirectAttributes.addFlashAttribute("productos", productos);
            return "redirect:/{productosId}";
        }
        Productos prod = productosService.obtenerPorCodigo(productosId);
//        Proveedor prov = prod.getProveedor();

        productosService.revisador(productos, prod);
        productosService.actualizarProducto(prod, false);

        return "redirect:/{productosId}";
    }

    @RequestMapping(value = "/{productosId}", method = RequestMethod.POST)
    public String eliminar(@PathVariable int productosId, RedirectAttributes redirectAttributes) {
        Productos pro = productosService.obtenerPorCodigo(productosId);
        productosService.eliminarProducto(pro);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Producto exitosamente eliminado", FlashMessage.Status.SUCCESS));
        return "redirect:/";
    }
}
