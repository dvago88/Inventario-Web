package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.ProductosDao;
import com.danielvargas.InventarioWeb.model.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    }

    @Override
    public void eliminarProducto(Productos productos) {

    }

    @Override
    public void actualizarProducto(Productos productos) {

    }
}
