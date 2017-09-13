package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.model.Proveedor;

import java.util.List;

public interface ProveedorService {
    List<Proveedor> todosLosProveedores();

    Proveedor obtenerPorNombre(String nombre);

    Proveedor obtenerPorCodigo(int id);

    void agregarProveedor(Proveedor proveedor);

    void eliminarProveedor(Proveedor proveedor);

    Proveedor actualizarProveedor(Proveedor proveedor);
}
