package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.model.storage.Productos;
import com.danielvargas.InventarioWeb.model.storage.Proveedor;

import java.util.List;

public interface ProveedorService {
    List<Proveedor> todosLosProveedores();

    Proveedor obtenerPorNombre(String nombre);

    Proveedor obtenerPorCodigo(int id);

    int agregarProveedor(Proveedor proveedor);

    void eliminarProveedor(Proveedor proveedor);

    int actualizarProveedor(Proveedor proveedor);

    List<Productos> todoslosProductos(Proveedor proveedor);
}
