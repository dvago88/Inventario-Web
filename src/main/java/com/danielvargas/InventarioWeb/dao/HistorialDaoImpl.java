package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.model.storage.Historial;
import com.danielvargas.InventarioWeb.model.storage.Productos;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistorialDaoImpl implements HistorialDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void crear(Historial historial) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(historial);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void actualizar(Historial historial) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(historial);
        session.getTransaction().commit();
        session.close();
    }

    //    Este metodo está sin testear, no sé si funcione
    @SuppressWarnings("unchecked")
    @Override
    public List<Historial> obtenerProductosPorId(int idProductos) {
//    public Historial obtenerProductosPorId(int idProductos) {
        String sql = "SELECT * FROM historial WHERE idProducto = '" + idProductos + "' ORDER BY localdatetime DESC;";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Historial.class);
        return query.list();
//        return query.getFirstResult();
    }
}
