package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.model.Proveedor;

import java.util.List;

public interface ProveedorService {
    List<Proveedor> todosLosProveedores();

    List<Proveedor> obtenerPorNombre(String nombre);

    Proveedor obtenerPorCodigo(int id);

    void agregarProveedor(Proveedor proveedor);

    void eliminarProveedor(Proveedor proveedor);

    void actualizarProveedor(Proveedor proveedor);
}
