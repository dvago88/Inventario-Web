package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.ProveedorDao;
import com.danielvargas.InventarioWeb.model.storage.Productos;
import com.danielvargas.InventarioWeb.model.storage.Proveedor;
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

    //Si el proveedor esta lo devuelve, sino devuelve null
    //TODO: Mejorar la eficiencia de este metodo
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
    public int agregarProveedor(Proveedor proveedor) {
        return proveedorDao.agregarProveedor(proveedor);
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        proveedorDao.eliminarProveedor(proveedor);
    }

    //Revisa si los campos del proveedor están vacios y de ser así los deja como estaban antes.
    @Override
    public int actualizarProveedor(Proveedor proveedor) {
        Proveedor pro = obtenerPorNombre(proveedor.getNombreP());
        if (!proveedor.getDireccion().equals("")) {
            pro.setDireccion(proveedor.getDireccion());
        }
        if (proveedor.getTelefono() != 0) {
            pro.setTelefono(proveedor.getTelefono());
        }
        if (!proveedor.getDescripcionP().equals("")) {
            pro.setDescripcionP(proveedor.getDescripcionP());
        }
        proveedorDao.actualizarProveedor(pro);

//        return pro;
        return pro.getId();
    }

    @Override
    public List<Productos> todoslosProductos(Proveedor proveedor) {
        return proveedor.getProductos();
    }
}
