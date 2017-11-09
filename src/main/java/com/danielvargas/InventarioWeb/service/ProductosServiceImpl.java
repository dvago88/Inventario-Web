package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.ProductosDao;
import com.danielvargas.InventarioWeb.model.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */
@Service
public class ProductosServiceImpl implements ProductosService {

    @Autowired
    private ProductosDao productosDao;

    @Override
    public List<Productos> todosLosProductos() {
        return productosDao.todosLosProductos();
    }

    @Override
    public Productos obtenerPorCodigo(int id) {
        return productosDao.obtenerPorCodigo(id);
    }

    //Si el producto esta lo devuelve, sino devuelve null
    //TODO: Mejorar la eficiencia de este metodo
    @Override
    public Productos obtenerPorNombre(String nombre) {
        List<Productos> productos = todosLosProductos();
        Productos producto;

        int pro = 0;
        for (Productos p : productos) {
            if (p.getNombre().equals(nombre)) {
                producto = productos.get(pro);
                return producto;
            }
            pro++;
        }
        return null;
    }

    @Override
    public void agregarProducto(Productos productos) {
        productosDao.agregarProducto(productos);
    }

    @Override
    public void eliminarProducto(Productos productos) {
        productosDao.eliminarProducto(productos);
    }

    @Override
    public void actualizarProducto(Productos productos, boolean revisar) {
        if (revisar) {
            Productos prod = obtenerPorNombre(productos.getNombre());
            prod.setCantidad(prod.getCantidad() + productos.getCantidad());

            if (!productos.getDescripcion().equals("")) {
                prod.setDescripcion(productos.getDescripcion());
            }
            if (productos.getPrecio() != prod.getPrecio()) {
                prod.setPrecio(productos.getPrecio());
            }
            if (productos.getPrecioEntrada() != prod.getPrecioEntrada()) {
                prod.setPrecioEntrada(productos.getPrecioEntrada());
            }
            productosDao.actualizarProducto(prod);
        } else {
            productosDao.actualizarProducto(productos);
        }
    }

    @Override
    public void revisador(Productos productos, Productos prod) {
        if (!productos.getNombre().equals(prod.getNombre())) {
            prod.setNombre(productos.getNombre());
        }
        if (productos.getCantidad() != prod.getCantidad()) {
            prod.setCantidad(productos.getCantidad());
        }
        if (productos.getPrecio() != prod.getPrecio()) {
            prod.setPrecio(productos.getPrecio());
        }
        if (productos.getPrecioEntrada() != prod.getPrecioEntrada()) {
            prod.setPrecioEntrada(productos.getPrecioEntrada());
        }
        if (!productos.getDescripcion().equals(prod.getDescripcion())) {
            prod.setDescripcion(productos.getDescripcion());
        }
    }

    private int getDayFromDate(Productos productos) {
        LocalDateTime now = LocalDateTime.now();
        return (int) ChronoUnit.DAYS.between(productos.getDateUploaded(), now);
    }

    //TODO: Agregar exception para cuando el numero es mayor a 32000
    @Override
    public void numeroDeVentas(Productos productos, int cantidad) {
        int diaActual = getDayFromDate(productos);
        //Esto es para que no se actualicen las ventas negativamente, no tiene sentido "desvender".
        if (cantidad < 0) {
            return;
        }
        if (productos.obtenerDiarias(diaActual) == 0) {
            productos.actualizarDiarias(diaActual, cantidad);
        } else {
            int can = productos.obtenerDiarias(diaActual);
            can += cantidad;
            productos.actualizarDiarias(diaActual, can);
        }
        productos.setCantidadVendido(productos.getCantidadVendido() + cantidad);
    }

    @Override
    public boolean cantidadProducto(Productos productos, String masOMenos, int can) {
        int cantidad = productos.getCantidad();
        if (masOMenos.equals("mas")) {
            cantidad += can;
        } else {
            cantidad -= can;
        }
        if (cantidad < 0) {
            return false;
        }
        productos.setCantidad(cantidad);
        return true;
    }

    @Override
    public int vendidosPorDia(Productos productos) {
        int dia = getDayFromDate(productos);
        int d;
        try {
            d = productos.obtenerDiarias(dia);
            return d;
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    @Override
    public int vendidosPorSemana(Productos productos) {
        int diaActual = getDayFromDate(productos);
        return getCantidadArray(productos, diaActual, 7);
    }

    @Override
    public int vendidoPorMes(Productos productos) {
        int diaActual = getDayFromDate(productos);
        return getCantidadArray(productos, diaActual, 30);
    }

    @Override
    public int vendidoPorAno(Productos productos) {
        int diaActual = getDayFromDate(productos);
        return getCantidadArray(productos, diaActual, 365);
    }

    private int getCantidadHashMap(Productos productos, int diaActual, int numeroDias) {
        int contador = 0;
        for (int i = diaActual; i > diaActual - numeroDias; i--) {
            try {
                contador += productos.obtenerDiarias(i);
            } catch (NullPointerException ex) {

            }
        }
        return contador;
    }

    private int getCantidadArray(Productos productos, int diaActual, int numeroDias) {
        int contador = 0;
        for (int i = diaActual; i > diaActual - numeroDias; i--) {
            if (i < 0) {
                return contador;
            }
            contador += productos.obtenerDiarias(i);
        }
        return contador;
    }
}
