package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.model.storage.Proveedor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProveedorDaoImpl implements ProveedorDao {

    @Autowired
    SessionFactory sessionFactory;


    @Override
    @SuppressWarnings("unchecked")
    public List<Proveedor> todosLosProveedores() {
        Session session = sessionFactory.openSession();
        List<Proveedor> proveedores = session.createCriteria(Proveedor.class).list();
        session.close();
        return proveedores;
    }

    @Override
    public Proveedor obtenerPorCodigo(int id) {
        Session session = sessionFactory.openSession();
        Proveedor proveedor = session.get(Proveedor.class, id);
        session.close();
        return proveedor;
    }

    @Override
    public int agregarProveedor(Proveedor proveedor) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(proveedor);
        session.getTransaction().commit();
        session.close();
        return proveedor.getId();
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(proveedor);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public int actualizarProveedor(Proveedor proveedor) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(proveedor);
        session.getTransaction().commit();
        session.close();
        return proveedor.getId();
    }
}

