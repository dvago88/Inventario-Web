package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.model.storage.Productos;

import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */
public interface ProductosService {
    List<Productos> todosLosProductos();

    Productos obtenerPorCodigo(int id);

    Productos obtenerPorNombre(String nombre);

    int agregarProducto(Productos productos);

    void eliminarProducto(Productos productos);

    int actualizarProducto(Productos productos, boolean revisar);

    void numeroDeVentas(Productos productos, int cantidad);

    boolean cantidadProducto(Productos productos, String masOMenos, int can);

    int vendidosPorXDias(Productos productos, int dias);

    /*int vendidosPorDia(Productos productos);

    int vendidosPorSemana(Productos productos);*/

    int vendidoPorMes(Productos productos);

    int vendidoPorAno(Productos productos);

    void revisador(Productos productos, Productos prod);

}
