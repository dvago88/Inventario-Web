package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.model.storage.Historial;
import com.danielvargas.InventarioWeb.model.storage.Productos;

import java.time.LocalDateTime;
import java.util.List;

public interface HistorialService {

    void crear(Historial historial);

    void actualizar(Historial historial);

    List<Historial> obtenerProductosPorId(int idProductos);

    List<Productos> obtenerProductosPorFecha(String fecha);
}
