package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.model.Productos;

import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */
public interface ProductosService {
    List<Productos> todosLosProductos();

    Productos obtenerPorCodigo(int id);

    Productos obtenerPorNombre(String nombre);

    void agregarProducto(Productos productos);

    void eliminarProducto(Productos productos);

    void actualizarProducto(Productos productos);

}
