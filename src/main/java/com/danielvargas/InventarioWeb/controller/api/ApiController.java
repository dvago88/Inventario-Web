package com.danielvargas.InventarioWeb.controller.api;

import com.danielvargas.InventarioWeb.controller.FlashMessage;
import com.danielvargas.InventarioWeb.model.storage.Productos;
import com.danielvargas.InventarioWeb.service.ProductosService;
import com.danielvargas.InventarioWeb.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ApiController {

    @Autowired
    ProductosService productosService;

    @Autowired
    ProveedorService proveedorService;


    @GetMapping("/api/productos")
    public List getProductos() {
        return productosService.todosLosProductos();
    }

  /*  @GetMapping("/api/productos/{id}")
    public Productos getProductoPorId(@PathVariable int id) {
        return productosService.obtenerPorCodigo(id);
    }*/

    @RequestMapping(path = "/api/productos/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Productos> getAvailableArduinos(@PathVariable int id) {

        Productos producto = productosService.obtenerPorCodigo(id);
        if (producto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @RequestMapping(path = "/api/productos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Productos> agregarProducto(@RequestBody Productos producto) {
        producto.setProveedor(proveedorService.obtenerPorCodigo(1));
        productosService.agregarProducto(producto);

        return new ResponseEntity<>(producto, HttpStatus.CREATED);

    }

    @RequestMapping(path = "api/actualizar/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Productos> agregarUno(@PathVariable int id, @RequestParam("howMany") int cantidad, @RequestParam("moreOrLess") String accion) {
        Productos producto = productosService.obtenerPorCodigo(id);

        if (!productosService.cantidadProducto(producto, accion, cantidad)) {

        } else {
            if (accion.equals("menos")) {
                productosService.numeroDeVentas(producto, cantidad);
            }
            productosService.agregarProducto(producto);
        }

        return new ResponseEntity<>(producto, HttpStatus.ACCEPTED);
    }

}
