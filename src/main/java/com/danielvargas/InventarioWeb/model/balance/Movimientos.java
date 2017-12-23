package com.danielvargas.InventarioWeb.model.balance;

import com.danielvargas.InventarioWeb.model.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Movimientos extends BaseEntity {

    private int stock;
    private int cantidadVendido;
    private int cantidadComprado;

    public Movimientos() {
        super();
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
}
