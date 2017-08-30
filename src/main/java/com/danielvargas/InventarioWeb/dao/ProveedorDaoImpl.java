package com.danielvargas.InventarioWeb.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProveedorDaoImpl implements ProveedorDao {

    @Autowired
    SessionFactory sessionFactory;
}
