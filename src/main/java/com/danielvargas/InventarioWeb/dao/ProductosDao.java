package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.model.Productos;

import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */
public interface ProductosDao {
    List<Productos> todosLosProductos();

    Productos obtenerPorCodigo(int id);

    void agregarProducto(Productos productos);

    void eliminarProducto(Productos productos);

    void actualizarProducto(Productos productos);
}
