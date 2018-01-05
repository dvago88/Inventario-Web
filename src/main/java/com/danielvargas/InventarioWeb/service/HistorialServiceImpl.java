package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.HistorialDao;
import com.danielvargas.InventarioWeb.model.storage.Historial;
import com.danielvargas.InventarioWeb.model.storage.Productos;
import com.danielvargas.InventarioWeb.model.storage.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class HistorialServiceImpl implements HistorialService {

    @Autowired
    private HistorialDao historialDao;

    @Autowired
    private ProveedorService proveedorService;

    @Override
    public void crear(Historial historial) {
//        TODO: mirar como hacer para no devolver toda la lista sino solo un producto o null
        List<Historial> antiguos = obtenerProductosPorId(historial.getIdProducto());
        if (antiguos.isEmpty()) {
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

    @Override
    public List<Productos> obtenerProductosPorFecha(String fecha) {
        List<Historial> historial = historialDao.obtenerProductosPorFecha(convertirFechaStringAFechaEntera(fecha));
//        TODO: mirar que estructura de datos es más eficiente para hacer esto:
        List<Productos> productos = new LinkedList<>();
        for (Historial h : historial) {
            Productos prod = new Productos();
            Proveedor prov = proveedorService.obtenerPorCodigo(h.getIdProveedor());
            prod.setProveedor(prov);
            prod.setId(h.getIdProducto());
            prod.setCantidad(h.getCantidadComprado() - h.getCantidadVendido());
            prod.setNombre(h.getNombreProducto());
            prod.setPrecio(h.getPrecio());
            prod.setPrecioEntrada(h.getPrecioEntrada());
            prod.setCantidadVendido(h.getCantidadVendido());
            prod.setCantidadComprado(h.getCantidadComprado());
            prod.setDateUploaded(h.getLocalDateTime());
            productos.add(prod);
        }
        return productos;
    }

    private int convertirFechaStringAFechaEntera(String fecha) {
        String cache = fecha.substring(6) + fecha.substring(0, 2) + fecha.substring(3, 5);
        return Integer.parseInt(cache);
    }
}
