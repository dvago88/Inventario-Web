package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.model.storage.Proveedor;

import java.util.List;

public interface ProveedorDao {
    List<Proveedor> todosLosProveedores();

    Proveedor obtenerPorCodigo(int id);

    int agregarProveedor(Proveedor proveedor);

    void eliminarProveedor(Proveedor proveedor);

    int actualizarProveedor(Proveedor proveedor);

}
