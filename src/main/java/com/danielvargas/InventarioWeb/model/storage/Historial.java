package com.danielvargas.InventarioWeb.model.storage;

import com.danielvargas.InventarioWeb.model.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Historial extends BaseEntity implements Comparable<Historial> {

    private double precio;
    private double precioEntrada;
    private int stock;
    private int cantidadVendido;
    private int cantidadComprado;

    public Historial(int idProducto,
                     int idProveedor,
                     String nombreProducto,
                     String nombreProveedor,
                     double precio,
                     double precioEntrada,
                     int stock,
                     int cantidadVendido,
                     int cantidadComprado) {
        super(idProducto, idProveedor, nombreProducto, nombreProveedor);
        this.precio = precio;
        this.precioEntrada = precioEntrada;
        this.stock = stock;
        this.cantidadVendido = cantidadVendido;
        this.cantidadComprado = cantidadComprado;
    }

    public Historial() {
        super();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidadVendido() {
        return cantidadVendido;
    }

    public void setCantidadVendido(int cantidadVendido) {
        this.cantidadVendido = cantidadVendido;
    }

    public int getCantidadComprado() {
        return cantidadComprado;
    }

    public void setCantidadComprado(int cantidadComprado) {
        this.cantidadComprado = cantidadComprado;
    }

    @Override
    public int compareTo(Historial o) {
        if (this.getIdProducto() == o.getIdProducto()) {
            return isSameDay(this, o);
        }
        if (this.getLocalDateTime().getDayOfYear() > o.getLocalDateTime().getDayOfYear() &&
                this.getLocalDateTime().getYear() >= o.getLocalDateTime().getYear()) {
            return 1;
        } else {
            return -1;
        }
    }


    private int isSameDay(Historial historial1, Historial historial2) {
        if (historial1.getLocalDateTime().getDayOfYear() == historial2.getLocalDateTime().getDayOfYear() &&
                historial1.getLocalDateTime().getYear() == historial2.getLocalDateTime().getYear()) {
            return 0;
        } else if (historial1.getLocalDateTime().getDayOfYear() > historial2.getLocalDateTime().getDayOfYear() &&
                historial1.getLocalDateTime().getYear() >= historial2.getLocalDateTime().getYear()) {
            return 1;
        } else {
            return -1;
        }
    }
}
