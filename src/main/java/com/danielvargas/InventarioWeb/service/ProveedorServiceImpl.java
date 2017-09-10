package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.ProveedorDao;
import com.danielvargas.InventarioWeb.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    ProveedorDao proveedorDao;

    @Override
    public List<Proveedor> todosLosProveedores() {
        return proveedorDao.todosLosProveedores();
    }

    @Override
    public List<Proveedor> obtenerPorNombre(String nombre) {
        return null;
    }

    @Override
    public Proveedor obtenerPorCodigo(int id) {
        return proveedorDao.obtenerPorCodigo(id);
    }

    @Override
    public void agregarProveedor(Proveedor proveedor) {
        proveedorDao.agregarProveedor(proveedor);
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        proveedorDao.eliminarProveedor(proveedor);
    }

    @Override
    public void actualizarProveedor(Proveedor proveedor) {

    }
}
