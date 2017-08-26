package com.danielvargas.InventarioWeb.controller;

import com.danielvargas.InventarioWeb.model.Productos;
import com.danielvargas.InventarioWeb.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.TreeSet;

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
    public String formularioAgregar(Model model) {

        if (!model.containsAttribute("productos")) {
            //agregamos el modelo para poder usar el formulario
            model.addAttribute("productos", new Productos());
        }

        model.addAttribute("accion","/agregar");
        model.addAttribute("encabezado","Agregar");
        model.addAttribute("boton","Agregar");

        return "formulario";
    }

    @RequestMapping("/eliminar")
    public String eliminar(Model model) {
        return "formulario";
    }

    @RequestMapping(value = "/agregar", method = RequestMethod.POST)
    public String agregarProducto(@Valid Productos productos, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            //Esto es para optener los erroes de validacion cuando redirijamos
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            //Agregamos categoria si data invalida fue recibida
            //Esto lo tendremos disponible solo por una redireccionada, despues se perderá
            redirectAttributes.addFlashAttribute("category", productos);

            //redirigimos hacia donde estaba antes
            return "redirect:/categories/add";
        }

        productosService.agregarProducto(productos);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Producto exitosamente agregado", FlashMessage.Status.SUCCESS));

        /*
        El redirect hace que spring mande un request del tipo 302 (de redireccionamiento)
        ademas hay que tener cuidado pues despues de los 2 puntos, NO se pone el template sino la pagina a la que
        me quiero redireccionar.
         */
        return "redirect:/agregar";

    }
}


