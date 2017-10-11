package com.danielvargas.InventarioWeb.operation;


import com.danielvargas.InventarioWeb.model.Productos;
import com.danielvargas.InventarioWeb.model.Proveedor;
import com.danielvargas.InventarioWeb.service.ProductosServiceImpl;
import com.danielvargas.InventarioWeb.service.ProveedorServiceImpl;

import java.util.Iterator;
import java.util.LinkedList;


public class ProcesadorDeStrings {
    private String texto;
    private StringBuilder error;
    private ProductosServiceImpl productosService;
    private ProveedorServiceImpl proveedorService;

    public ProcesadorDeStrings(String texto) {
        this.texto = texto;
        error = new StringBuilder();
        productosService = new ProductosServiceImpl();
        proveedorService = new ProveedorServiceImpl();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }


    /**
     * Separa las palabras clave del texto y genera los querys necesarios para ingresar los productos a la BD
     */
    public String procesador() {
        StringBuilder sb = new StringBuilder();
        LinkedList<String> columnas = new LinkedList<>();
        LinkedList<String> datos = new LinkedList<>();
        for (int i = 0; i < texto.length(); i++) {

            if (texto.charAt(i) == '=') {
                columnas.add(sb.toString());
                sb.delete(0, sb.length());
            } else if (texto.charAt(i) == ';') {
                datos.add(sb.toString());
                sb.delete(0, sb.length());
            } else {
                sb.append(texto.charAt(i));
            }
        }
        queryMaker(columnas, datos);
        if (error.length() > 0) {
            return error.toString();
        }
        return "";
    }

    private void queryMaker(LinkedList<String> columnas, LinkedList<String> datos) {
        Iterator<String> iterator1 = columnas.listIterator();
        Iterator<String> iterator2 = datos.listIterator();
        Productos productos = new Productos();
        Proveedor proveedor = new Proveedor();
        int contador = 0;
        while (iterator1.hasNext()) {
            String columna = iterator1.next();
            String dato = iterator2.next();
            switch (columna.toLowerCase()) {
                case "nombre":
                    productos.setNombre(dato);
                    contador++;
                    break;
                case "precio":
                    productos.setPrecio(Integer.parseInt(dato));
                    contador++;
                    break;
                case "precioentrada":
                    productos.setPrecioEntrada(Integer.parseInt(dato));
                    contador++;
                    break;
                case "cantidad":
                    productos.setCantidad(Integer.parseInt(dato));
                    contador++;
                    break;
                case "descripcion":
                    productos.setDescripcion(dato);
                    contador++;
                    break;
                case "proveedor":
                    proveedor.setNombreP(dato);
                    contador++;
                    break;
                case "direccion":
                    proveedor.setDireccion(dato);
                    contador++;
                    break;
                case "telefono":
                    if (dato.equals("")) {
                        dato = "0";
                    }
                    proveedor.setTelefono(Long.parseLong(dato));
                    contador++;
                    break;
                case "descripcionp":
                    proveedor.setDescripcionP(dato);
                    contador++;
                    break;
                default:
                    error.append(columna).append(": ").append(dato).append("/n");
            }
            if (contador == 9) {
                if (proveedorService.obtenerPorNombre(proveedor.getNombreP()) == null) {
                    proveedorService.agregarProveedor(proveedor);
                } else {
                    proveedorService.actualizarProveedor(proveedor);
                }
                productos.setProveedor(proveedor);
                if (productosService.obtenerPorNombre(productos.getNombre()) == null) {
                    productosService.agregarProducto(productos);
                } else {
                    productosService.actualizarProducto(productos, true);
                }
                productos = new Productos();
                proveedor = new Proveedor();
                contador=0;
            }
        }
    }
}
