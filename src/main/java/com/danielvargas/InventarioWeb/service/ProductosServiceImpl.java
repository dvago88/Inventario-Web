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

    @Override
    public List<Productos> obtenerPorNombre(String nombre) {
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

    }

    /**
    * Esta forma de obtener toda la informacion no es la mas eficiente y se debe cambiar despues
    * */
   @Override
    public TreeSet<String> todaLaInformacion() {
        TreeSet<String> todos=new TreeSet<>();
        List<Productos> productos=productosDao.todosLosProductos();
        for(Productos p:productos){
            String i=p.getId()+" ";
            i+=p.getNombre()+" ";
            i+=p.getCantidad()+" ";
            todos.add(i);
        }
        return todos;
    }
}
