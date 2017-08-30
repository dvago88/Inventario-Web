package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.ProveedorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    @Autowired
    ProveedorDao proveedorDao;
}
