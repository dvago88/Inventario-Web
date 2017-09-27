package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.ProductosDao;
import com.danielvargas.InventarioWeb.model.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Daniel on 20/08/2017
 */
@Service
public class ProductosServiceImpl implements ProductosService {

    @Autowired
    private ProductosDao productosDao;

    @Override
    public List<Productos> todosLosProductos() {
        return productosDao.todosLosProductos();
    }

    @Override
    public Productos obtenerPorCodigo(int id) {
        return productosDao.obtenerPorCodigo(id);
    }

    //Si el producto esta lo devuelve, sino devuelve null
    //TODO: Mejorar la eficiencia de este metodo
    @Override
    public Productos obtenerPorNombre(String nombre) {
        List<Productos> productos = todosLosProductos();
        Productos producto;

        int pro = 0;
        for (Productos p : productos) {
            if (p.getNombre().equals(nombre)) {
                producto = productos.get(pro);
                return producto;
            }
            pro++;
        }
        return null;
    }

    @Override
    public void agregarProducto(Productos productos) {
        productosDao.agregarProducto(productos);
    }

    @Override
    public void eliminarProducto(Productos productos) {
        productosDao.eliminarProducto(productos);
    }

    @Override
    public void actualizarProducto(Productos productos) {
        Productos prod = obtenerPorNombre(productos.getNombre());
        prod.setCantidad(prod.getCantidad() + productos.getCantidad());

        if (!productos.getDescripcion().equals("")) {
            prod.setDescripcion(productos.getDescripcion());
        }
        productosDao.actualizarProducto(prod);
    }

    /**
     * Esta forma de obtener toda la informacion no es la mas eficiente y se debe cambiar despues
     */
    @Override
    public TreeSet<String> todaLaInformacion() {
        TreeSet<String> todos = new TreeSet<>();
        List<Productos> productos = productosDao.todosLosProductos();
        for (Productos p : productos) {
            String i = p.getId() + " ";
            i += p.getNombre() + " ";
            i += p.getCantidad() + " ";
            todos.add(i);
        }
        return todos;
    }
}
