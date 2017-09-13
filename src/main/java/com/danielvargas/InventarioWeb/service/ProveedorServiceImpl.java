package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.ProveedorDao;
import com.danielvargas.InventarioWeb.model.Proveedor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    ProveedorDao proveedorDao;

    @Override
    public List<Proveedor> todosLosProveedores() {
        return proveedorDao.todosLosProveedores();
    }

    //TODO: Mejorar la eficiencia de este metodo y verificar que sí funcione
    @Override
    public Proveedor obtenerPorNombre(String nombre) {
        List<Proveedor> proveedores = todosLosProveedores();
        Proveedor proveedor;

        int pro = 0;
        for (Proveedor p : proveedores) {
            if (p.getNombreP().equals(nombre)) {
                proveedor = proveedores.get(pro);
                return proveedor;
            }
            pro++;
        }
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
    public Proveedor actualizarProveedor(Proveedor proveedor) {
        Proveedor pro = obtenerPorNombre(proveedor.getNombreP());
        if (proveedor.getDireccion() != null) {
            pro.setDireccion(proveedor.getDireccion());
        }
        if (proveedor.getTelefono() != 0) {
            pro.setTelefono(proveedor.getTelefono());
        }
        if (proveedor.getDescripcionP() != null) {
            pro.setDescripcionP(proveedor.getDescripcionP());
        }
        proveedorDao.actualizarProveedor(pro);

        return pro;
    }
}
