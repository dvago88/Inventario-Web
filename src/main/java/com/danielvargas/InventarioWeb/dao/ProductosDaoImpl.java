package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.model.storage.Productos;
import com.danielvargas.InventarioWeb.model.storage.Proveedor;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */

@Repository
@Transactional
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
    public int agregarProducto(Productos productos) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(productos);
        session.getTransaction().commit();
        session.close();
        return productos.getId();
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
    public int actualizarProducto(Productos productos) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(productos);
        session.getTransaction().commit();
        session.close();
        return productos.getId();
    }

    @Override
    public List<Productos> obtenerPorProveedor(int provId) {
        /*Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("SELECT * FROM productos WHERE proveedor_id=" + provId + ";");
//        Query query= session.createQuery("from Productos where proveedor_id = :provId");
//        query.setParameter("provId", provId);
        List<Productos> productos =  query.list();
        session.close();*/
        String sql = "SELECT * FROM productos WHERE proveedor_id=" + provId + ";";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Productos.class);
        return query.list();
    }
}

