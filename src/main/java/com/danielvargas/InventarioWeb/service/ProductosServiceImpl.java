package com.danielvargas.InventarioWeb.service;

import com.danielvargas.InventarioWeb.dao.ProductosDao;
import com.danielvargas.InventarioWeb.model.storage.Historial;
import com.danielvargas.InventarioWeb.model.storage.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel on 20/08/2017
 */
@Service
public class ProductosServiceImpl implements ProductosService {

    @Autowired
    private ProductosDao productosDao;

    @Autowired
    private HistorialService historialService;

    @Override
    public List<Productos> todosLosProductos() {
        return productosDao.todosLosProductos();
    }

    @Override
    public Productos obtenerPorCodigo(int id) {
        return productosDao.obtenerPorCodigo(id);
    }

    //Si el producto esta lo devuelve, sino devuelve null
    //TODO: Mejorar la eficiencia de este metodo (si se puede)
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
    public int agregarProducto(Productos productos) {
        productos.setCantidadComprado(productos.getCantidad());
        int prodId = productosDao.agregarProducto(productos);
        Historial historial = new Historial(
                prodId,
                productos.getProveedor().getId(),
                productos.getNombre(),
                productos.getProveedor().getNombreP(),
                productos.getPrecio(),
                productos.getPrecioEntrada(),
                productos.getCantidad(),
                productos.getCantidadVendido(),
                productos.getCantidadComprado()
        );
        historialService.crear(historial);

        return prodId;
    }

    @Override
    public void eliminarProducto(Productos productos) {
        productosDao.eliminarProducto(productos);
    }

    @Override
    public int actualizarProducto(Productos productos, boolean revisar) {
        int prodId;
        if (revisar) {
            Productos prod = obtenerPorNombre(productos.getNombre());
            prod.setCantidad(prod.getCantidad() + productos.getCantidad());
            int comprados = prod.getCantidadComprado() + productos.getCantidad();
            prod.setCantidadComprado(comprados);
            productos.setCantidadComprado(comprados);

            if (!productos.getDescripcion().equals("")) {
                prod.setDescripcion(productos.getDescripcion());
            }
            if (productos.getPrecio() != prod.getPrecio()) {
                prod.setPrecio(productos.getPrecio());
            }
            if (productos.getPrecioEntrada() != prod.getPrecioEntrada()) {
                prod.setPrecioEntrada(productos.getPrecioEntrada());
            }
            if (prod.getProveedor().getId() != productos.getProveedor().getId()) {
                prod.setProveedor(productos.getProveedor());
            }
            prodId = productosDao.actualizarProducto(prod);
        } else {
            prodId = productosDao.actualizarProducto(productos);
        }
        Historial historial = new Historial(
                prodId,
                productos.getProveedor().getId(),
                productos.getNombre(),
                productos.getProveedor().getNombreP(),
                productos.getPrecio(),
                productos.getPrecioEntrada(),
                productos.getCantidad(),
                productos.getCantidadVendido(),
                productos.getCantidadComprado()
        );
        historialService.crear(historial);

        return prodId;
    }

    @Override
    public void revisador(Productos productos, Productos prod) {
        if (!productos.getNombre().equals(prod.getNombre())) {
            prod.setNombre(productos.getNombre());
        }
        if (productos.getCantidad() != prod.getCantidad()) {
            prod.setCantidad(productos.getCantidad());
            int cantComprado = prod.getCantidadComprado();
            cantComprado -= prod.getCantidad() - productos.getCantidad();
            prod.setCantidadComprado(cantComprado);
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
        //Esto es para que no se actualicen las ventas negativamente, no tiene sentido "desvender".
        if (cantidad < 0) {
            return;
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
    public int vendidosPorXDias(Productos productos, int dias) {
        int fechaEntera = getFechaEntera(LocalDateTime.now().minusDays(dias));
        int contador = 0;
        List<Historial> historial = historialService.obtenerProductosPorId(productos.getId());
        for (int i = 0; i < historial.size(); i++) {
            Historial his = historial.get(i);
            if (his.getFechaEntera() >= fechaEntera) {
                if (i + 1 < historial.size()) {
//                    Acumula la diferencia de cada día
                    contador += his.getCantidadVendido() - historial.get(i + 1).getCantidadVendido();
                } else {
                    contador += his.getCantidadVendido();
                }
            } else {
                return contador;
            }
        }
        return contador;
    }

/*    @Override
    public int vendidosPorDia(Productos productos) {
        int fechaEntera = getFechaEntera(LocalDateTime.now());
        List<Historial> historial = historialService.obtenerProductosPorId(productos.getId());
        Historial primero = historial.get(0);
        if (primero.getFechaEntera() == fechaEntera) {
//            la cantidad del día es el último menos el penúltimo (pues siempre se guarda el total)
            return primero.getCantidadVendido() - historial.get(1).getCantidadVendido();
        }
        return 0;
    }


    @Override
    public int vendidosPorSemana(Productos productos) {
        int fechaEntera = getFechaEntera(LocalDateTime.now().minusDays(7));
        int contador = 0;
        List<Historial> historial = historialService.obtenerProductosPorId(productos.getId());
        for (int i = 0; i < historial.size(); i++) {
            Historial his = historial.get(i);
            if (his.getFechaEntera() > fechaEntera) {
                if (i + 1 < historial.size()) {
//                    Acumula la diferencia de cada día
                    contador += his.getCantidadVendido() - historial.get(i + 1).getCantidadVendido();
                } else {
                    contador += his.getCantidadVendido();
                }
            } else {
                return contador;
            }
        }
        return 0;
    }*/

    @Override
    public int vendidoPorMes(Productos productos) {
        LocalDateTime loc = LocalDateTime.now();
        int contador = 0;
        Month mes = loc.getMonth();
        List<Historial> historial = historialService.obtenerProductosPorId(productos.getId());
        for (int i = 0; i < historial.size(); i++) { //Esto es mejor hacer con un lamda
            Historial his = historial.get(i);
            if (his.getLocalDateTime().getMonth().equals(mes)) {
                if (i + 1 < historial.size()) {
                    contador += his.getCantidadVendido() - historial.get(i + 1).getCantidadVendido();
                } else {
                    contador += his.getCantidadVendido();
                }
            } else {
                return contador;
            }
        }
        return contador;
    }

    @Override
    public int vendidoPorAno(Productos productos) {
        LocalDateTime loc = LocalDateTime.now();
        int contador = 0;
        int ano = loc.getYear();
        List<Historial> historial = historialService.obtenerProductosPorId(productos.getId());
        for (int i = 0; i < historial.size(); i++) { //Esto es mejor hacer con un lamda
            Historial his = historial.get(i);
            if (his.getLocalDateTime().getYear() == ano) {
                if (i + 1 < historial.size()) {
                    contador += his.getCantidadVendido() - historial.get(i + 1).getCantidadVendido();
                } else {
                    contador += his.getCantidadVendido();
                }
            } else {
                return contador;
            }
        }
        return contador;
    }

    //    TODO: Refactorizar, este metodo se repite en el constructor del baseEntity
    private int getFechaEntera(LocalDateTime localDateTime) {
        return localDateTime.getYear() * 10000
                + localDateTime.getMonthValue() * 100
                + localDateTime.getDayOfMonth();
    }
}
