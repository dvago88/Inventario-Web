package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.model.Proveedor;

import java.util.List;

public interface ProveedorDao {
    List<Proveedor> todosLosProveedores();

    Proveedor obtenerPorCodigo(int id);

    void agregarProveedor(Proveedor proveedor);

    void eliminarProveedor(Proveedor proveedor);

    void actualizarProveedor(Proveedor proveedor);

}
