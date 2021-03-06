package com.danielvargas.InventarioWeb.dao;

import com.danielvargas.InventarioWeb.model.storage.Historial;
import com.danielvargas.InventarioWeb.model.storage.Productos;

import java.time.LocalDateTime;
import java.util.List;

public interface HistorialDao {

    void crear(Historial historial);

    void actualizar(Historial historial);

    List<Historial> obtenerProductosPorId(int idProductos);

    List<Historial> obtenerProductosPorFecha(int fechaEntera);

    List<Historial> obtenerProductoPorFecha(int idProducto, int fechaEntera);
}
