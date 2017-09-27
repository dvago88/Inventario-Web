package com.danielvargas.InventarioWeb.model;


import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Daniel on 20/08/2017
 */
@Entity
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int cantidad;

    @NotNull
    @Size(min = 1, max = 35, message = "El nombre debe tener entre {min} y {max} caracteres")
    @Column(unique = true)
    private String nombre;

    /**
     * Seguir acá:
     * la respuesta está acá: https://teamtreehouse.com/library/displaying-validation-messages
     * toca cambiar el mensaje en messages.properties y luego ir al app.config y hacer otros cambios
     */
    @NotNull
    @Range(min = 10, max = 10000000, message = "El valor tiene que estar entre ${min} y $10.000.000")
    private double precio;

    @NotNull
    @Range(min = 10, max = 10000000, message = "El valor tiene que estar entre ${min} y $10.000.000")
    private double precioEntrada;

    @Size(max = 150, message = "Maximo 150 caracteres para la descripción")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    public Productos() {
    }

    //No estoy seguro si este constructor pondrá algún problema en el futuro (lo use para testear más facilmente)
    public Productos(int cantidad, String nombre, double precio, double precioEntrada, String descripcion, Proveedor proveedor) {
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.precio = precio;
        this.precioEntrada = precioEntrada;
        this.descripcion = descripcion;
        this.proveedor = proveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
