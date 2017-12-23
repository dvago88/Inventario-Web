package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.HistorialDao;
import com.danielvargas.InventarioWeb.model.storage.Historial;
import com.danielvargas.InventarioWeb.model.storage.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class HistorialServiceImpl implements HistorialService {

    @Autowired
    private HistorialDao historialDao;

    @Override
    public void crear(Historial historial) {
//        TODO: mirar como hacer para no devolver toda la lista sino solo un producto o null
        List<Historial> antiguos = obtenerProductosPorId(historial.getIdProducto());
        if(antiguos.isEmpty()){
            historialDao.crear(historial);
            return;
        }
        Historial ultimo = antiguos.get(0);
        if (ultimo.compareTo(historial) == 0) {
            historial.setId(ultimo.getId());
            historialDao.actualizar(historial);
        } else {
            historialDao.crear(historial);
        }
    }

    @Override
    public void actualizar(Historial historial) {
        historialDao.actualizar(historial);
    }

    @Override
    public List<Historial> obtenerProductosPorId(int idProductos) {
        return historialDao.obtenerProductosPorId(idProductos);
    }


}
