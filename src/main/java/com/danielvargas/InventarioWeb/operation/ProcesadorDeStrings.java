package com.danielvargas.InventarioWeb.operation;


import com.danielvargas.InventarioWeb.model.storage.Productos;
import com.danielvargas.InventarioWeb.model.storage.Proveedor;

import java.util.Iterator;
import java.util.LinkedList;

/*TODO: Hacer que desde este metodo se pueda llamar al service
 para no tener que gastar tanta memoria llendo una lista con productos y proveedores.
En el video a continuacion probablemente estén las respuestas para solucionar esto, yo creo que hay que crear
otro sessionFactory que no esté autowired para poder interactuar correctamente desde el dao con el y así poder
llamar al service desde esta clase
 https://teamtreehouse.com/library/hibernate-basics/getting-started-with-hibernate/building-a-hibernate-sessionfactory*/
public class ProcesadorDeStrings {
    private String texto;
    private StringBuilder error;
    private LinkedList<Proveedor> proveedores;
    private LinkedList<Productos> products;

    public ProcesadorDeStrings(String texto) {
        products = new LinkedList<>();
        proveedores = new LinkedList<>();
        error = new StringBuilder();
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LinkedList<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(LinkedList<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public LinkedList<Productos> getProducts() {
        return products;
    }

    public void setProducts(LinkedList<Productos> products) {
        this.products = products;
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

    //TODO: Testear y mejorar este metodo.
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
                products.add(productos);
                proveedores.add(proveedor);
                productos = new Productos();
                proveedor = new Proveedor();
                contador = 0;
            }
        }
    }
}
