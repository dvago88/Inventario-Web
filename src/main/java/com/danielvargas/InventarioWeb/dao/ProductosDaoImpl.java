package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.model.Productos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */

@Repository
public class ProductosDaoImpl implements ProductosDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Productos> todosLosProductos() {
        Session session = sessionFactory.openSession();
        List<Productos> productos = session.createCriteria(Productos.class).list();
        session.close();
        return productos;
    }

    @Override
    public Productos obtenerPorCodigo(int id) {
        Session session = sessionFactory.openSession();
        Productos producto = session.get(Productos.class, id);
        session.close();
        return producto;
    }

    @Override
    public void agregarProducto(Productos productos) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(productos);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void eliminarProducto(Productos productos) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(productos);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void actualizarProducto(Productos productos) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(productos);
        session.getTransaction().commit();
        session.close();
    }
}

